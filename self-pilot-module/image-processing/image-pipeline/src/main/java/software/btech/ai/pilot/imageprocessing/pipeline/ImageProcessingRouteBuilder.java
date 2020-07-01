package software.btech.ai.pilot.imageprocessing.pipeline;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.exec.ExecBinding;
import software.btech.ai.pilot.domain.Configuration;
import software.btech.ai.pilot.domain.ExchangePropertyConstants;
import software.btech.ai.pilot.function.format.AirSimInputEndpointFormat;
import software.btech.ai.pilot.function.format.ImageFileNameFormat;
import software.btech.ai.pilot.function.format.PythonScriptEndpointFormat;
import software.btech.ai.pilot.function.format.TimerInputEndpointFormat;
import software.btech.ai.pilot.imageprocessing.lanedetection.LaneDetectionRouteBuilder;
import software.btech.ai.pilot.processor.PythonScriptOutputProcessor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;


/**
 * Image Processing Route Builder.
 */
@Named
public class ImageProcessingRouteBuilder extends RouteBuilder {

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

  @Inject
  private TimerInputEndpointFormat timerInputEndpointFormat;

  /**
   * Route configuration triggers every configured interval image capture event.
   * If image capture script process ends under expected conditions, body should contain
   * an image which is redirected to post processing routes.
   *
   * @throws Exception
   */
  @Override
  public void configure() throws Exception {

    String imageCaptureInputEndpoint = null;

    if (configuration.getAirsimHost() != null) {
      imageCaptureInputEndpoint = airSimInputEndpointFormat.apply(
        () -> pythonScriptEndpointFormat.apply(
          configuration::getImageCaptureScript,
          () -> imageFileNameFormat.apply(ExchangePropertyConstants.IMAGE_CAPTURE)));
    }

    configureImageCaptureRoute(imageCaptureInputEndpoint);

  }

  private void configureImageCaptureRoute(@NotNull String imageCaptureInputEndpoint) {
    from(timerInputEndpointFormat.apply(getClass().getSimpleName()))
      .throttle(1)
      .setProperty(
        ExchangePropertyConstants.PROCESSING_STEP,
        constant(ExchangePropertyConstants.IMAGE_CAPTURE))
      .to(imageCaptureInputEndpoint)
      .choice()
      .when(header(ExecBinding.EXEC_EXIT_VALUE).isEqualTo(0))
      .process(pythonScriptOutputProcessor)
      .to(LaneDetectionRouteBuilder.LANE_DETECTION_INPUT_ENDPOINT);
    //.to(OBJECT_DETECTION_INPUT_ENDPOINT);
  }

}
