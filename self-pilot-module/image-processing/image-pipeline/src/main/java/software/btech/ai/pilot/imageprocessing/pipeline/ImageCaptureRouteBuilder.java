package software.btech.ai.pilot.imageprocessing.pipeline;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.exec.ExecBinding;
import software.btech.ai.pilot.imageprocessing.domain.Configuration;
import software.btech.ai.pilot.imageprocessing.function.format.PythonExecEndpointFormat;

import javax.inject.Inject;
import javax.inject.Named;

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
  private PythonExecEndpointFormat pythonExecEndpointFormat;

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
      String.format("timer://%s?delay=%ss", getClass().getSimpleName(),
        configuration.getImageCaptureRepeatInterval());

    from(timerEndpoint)
      .to(pythonExecEndpointFormat.apply(configuration::getImageCaptureScript))
      .choice()
      .when(header(ExecBinding.EXEC_EXIT_VALUE).isEqualTo(0))
      .to(OBJECT_DETECTION_INPUT_ENDPOINT, LANE_DETECTION_INPUT_ENDPOINT);

  }

}
