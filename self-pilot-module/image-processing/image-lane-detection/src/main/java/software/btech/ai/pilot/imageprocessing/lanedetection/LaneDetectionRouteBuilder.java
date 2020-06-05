package software.btech.ai.pilot.imageprocessing.lanedetection;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.exec.ExecBinding;
import software.btech.ai.pilot.imageprocessing.domain.Configuration;
import software.btech.ai.pilot.imageprocessing.function.format.ImageInputEndpointFormat;
import software.btech.ai.pilot.imageprocessing.function.format.PythonScriptEndpointFormat;
import software.btech.ai.pilot.imageprocessing.processor.PythonScriptOutputProcessor;

import javax.inject.Inject;
import javax.inject.Named;

import static software.btech.ai.pilot.imageprocessing.domain.ExchangePropertyConstants.*;

/**
 * Lane Detection Route Builder
 */
@Named
public class LaneDetectionRouteBuilder extends RouteBuilder {

  public static final String LANE_DETECTION_INPUT_ENDPOINT = "seda:lane_detection_input_endpoint";

  private static final String LANE_DETECTION_IMAGE_MASK_ENDPOINT = "seda:lane_detection_image_mask_endpoint";

  private static final String LANE_DETECTION_EDGE_DETECTION_ENDPOINT = "seda:lane_detection_edge_detection_endpoint";

  @Inject
  private Configuration configuration;

  @Inject
  private PythonScriptEndpointFormat pythonScriptEndpointFormat;

  @Inject
  private ImageInputEndpointFormat imageInputEndpointFormat;

  @Inject
  private PythonScriptOutputProcessor pythonScriptOutputProcessor;

  @Override
  public void configure() throws Exception {
    configureImageMaskRoute();
    from(LANE_DETECTION_INPUT_ENDPOINT).to(LANE_DETECTION_IMAGE_MASK_ENDPOINT);
  }

  /**
   * Image mask route step
   */
  private void configureImageMaskRoute() {
    String imageInputEndpoint = imageInputEndpointFormat.apply(
      () -> pythonScriptEndpointFormat.apply(configuration::getLaneDetectionImageMaskScript, () -> LANE_DETECTION_IMAGE_MASK + ".png"),
      () -> IMAGE_CAPTURE + ".png"
    );

    from(LANE_DETECTION_IMAGE_MASK_ENDPOINT)
      .setProperty(IMAGE_PROCESSING_STEP, constant(LANE_DETECTION_IMAGE_MASK))
      .to(imageInputEndpoint)
      .choice()
      .when(header(ExecBinding.EXEC_EXIT_VALUE).isEqualTo(0))
      .process(pythonScriptOutputProcessor)
      .to(LANE_DETECTION_EDGE_DETECTION_ENDPOINT);
  }

}
