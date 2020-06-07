package software.btech.ai.pilot.imageprocessing.lanedetection;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.exec.ExecBinding;
import software.btech.ai.pilot.imageprocessing.domain.Configuration;
import software.btech.ai.pilot.imageprocessing.function.format.ImageInputEndpointFormat;
import software.btech.ai.pilot.imageprocessing.function.format.PythonScriptEndpointFormat;
import software.btech.ai.pilot.imageprocessing.processor.PythonScriptOutputProcessor;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.function.Supplier;

import static software.btech.ai.pilot.imageprocessing.domain.ExchangePropertyConstants.*;

/**
 * Lane Detection Route Builder
 */
@Named
public class LaneDetectionRouteBuilder extends RouteBuilder {

  public static final String LANE_DETECTION_INPUT_ENDPOINT = "seda:lane_detection_input_endpoint";

  private static final String LANE_DETECTION_IMAGE_MASK_ENDPOINT = "seda:lane_detection_image_mask_endpoint";

  private static final String LANE_DETECTION_IMAGE_PERSPECTIVE_ENDPOINT = "seda:lane_detection_image_perspective_endpoint";

  private static final String LANE_DETECTION_IMAGE_COLOR_FILTER_ENDPOINT = "seda:lane_detection_image_color_filter_endpoint";

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
    configureImagePerspectiveRoute();
    from(LANE_DETECTION_INPUT_ENDPOINT)
      .to(LANE_DETECTION_IMAGE_MASK_ENDPOINT);
  }

  /**
   * Step for configuring a image processing route
   *
   * @param stepSupplier              step being executed for property storage purposes
   * @param inputEndpointUriSupplier  input endpoint uri
   * @param inputImageSupplier        input image name
   * @param outputEndpointUriSupplier output endpoint uri
   * @param outputImageSupplier       output image name
   */
  private void configureImageProcessingRoute(
    Supplier<String> stepSupplier,
    Supplier<String> imageProcessingScriptSupplier,
    Supplier<String> inputEndpointUriSupplier,
    Supplier<String> inputImageSupplier,
    Supplier<String> outputEndpointUriSupplier,
    Supplier<String> outputImageSupplier) {

    String imageInputEndpoint = imageInputEndpointFormat.apply(
      () -> pythonScriptEndpointFormat.apply(imageProcessingScriptSupplier, outputImageSupplier),
      inputImageSupplier
    );

    from(inputEndpointUriSupplier.get())
      .setProperty(IMAGE_PROCESSING_STEP, constant(stepSupplier.get()))
      .to(imageInputEndpoint)
      .choice()
      .when(header(ExecBinding.EXEC_EXIT_VALUE).isEqualTo(0))
      .process(pythonScriptOutputProcessor)
      .to(outputEndpointUriSupplier.get());

  }

  /**
   * Image mask route step
   */
  private void configureImageMaskRoute() {
    configureImageProcessingRoute(
      () -> LANE_DETECTION_IMAGE_MASK,
      configuration::getLaneDetectionImageMaskScript,
      () -> LANE_DETECTION_IMAGE_MASK_ENDPOINT,
      () -> IMAGE_CAPTURE + ".png",
      () -> LANE_DETECTION_IMAGE_MASK + ".png",
      () -> LANE_DETECTION_IMAGE_PERSPECTIVE_ENDPOINT
    );
  }

  /**
   * Image perspective route step
   */
  private void configureImagePerspectiveRoute() {
    configureImageProcessingRoute(
      () -> LANE_DETECTION_IMAGE_PERSPECTIVE,
      configuration::getLaneDetectionImagePerspectiveScript,
      () -> LANE_DETECTION_IMAGE_PERSPECTIVE_ENDPOINT,
      () -> LANE_DETECTION_IMAGE_MASK + ".png",
      () -> LANE_DETECTION_IMAGE_PERSPECTIVE + ".png",
      () -> LANE_DETECTION_IMAGE_COLOR_FILTER_ENDPOINT
    );
  }

}
