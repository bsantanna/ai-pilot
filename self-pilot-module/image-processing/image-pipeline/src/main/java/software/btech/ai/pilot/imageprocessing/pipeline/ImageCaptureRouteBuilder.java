package software.btech.ai.pilot.imageprocessing.pipeline;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.exec.ExecBinding;
import software.btech.ai.pilot.imageprocessing.domain.Configuration;
import software.btech.ai.pilot.imageprocessing.function.format.AirSimInputEndpointFormat;
import software.btech.ai.pilot.imageprocessing.function.format.ImageFileNameFormat;
import software.btech.ai.pilot.imageprocessing.function.format.PythonScriptEndpointFormat;
import software.btech.ai.pilot.imageprocessing.processor.PythonScriptOutputProcessor;

import javax.inject.Inject;
import javax.inject.Named;

import static software.btech.ai.pilot.imageprocessing.domain.ExchangePropertyConstants.IMAGE_CAPTURE;
import static software.btech.ai.pilot.imageprocessing.domain.ExchangePropertyConstants.IMAGE_PROCESSING_STEP;
import static software.btech.ai.pilot.imageprocessing.lanedetection.LaneDetectionRouteBuilder.LANE_DETECTION_INPUT_ENDPOINT;
import static software.btech.ai.pilot.imageprocessing.objectdetection.ObjectDetectionRouteBuilder.OBJECT_DETECTION_INPUT_ENDPOINT;

/**
 * Image Capture Route Builder.
 */
@Named
public class ImageCaptureRouteBuilder extends RouteBuilder {

  @Inject
  private Configuration configuration;

  @Inject
  private PythonScriptEndpointFormat pythonScriptEndpointFormat;

  @Inject
  private AirSimInputEndpointFormat airSimInputEndpointFormat;

  @Inject
  private PythonScriptOutputProcessor pythonScriptOutputProcessor;

  @Inject
  private ImageFileNameFormat imageFileNameFormat;

  /**
   * Route configuration triggers every configured interval image capture event.
   * If image capture script process ends under expected conditions, body should contain
   * a png image which is redirected to post processing routes.
   *
   * @throws Exception
   */
  @Override
  public void configure() throws Exception {

    final String timerEndpoint =
      String.format("timer://%s?fixedRate=true&period=%ss", getClass().getSimpleName(),
        configuration.getImageCaptureRepeatInterval());

    final String imageCaptureEndpoint = airSimInputEndpointFormat.apply(
      () -> pythonScriptEndpointFormat.apply(configuration::getImageCaptureScript, () -> imageFileNameFormat.apply(IMAGE_CAPTURE))
    );

    from(timerEndpoint)
      .setProperty(IMAGE_PROCESSING_STEP, constant(IMAGE_CAPTURE))
      .to(imageCaptureEndpoint)
      .choice()
      .when(header(ExecBinding.EXEC_EXIT_VALUE).isEqualTo(0))
      .process(pythonScriptOutputProcessor)
      .to(LANE_DETECTION_INPUT_ENDPOINT)
      .to(OBJECT_DETECTION_INPUT_ENDPOINT);

  }

}
