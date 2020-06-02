package software.btech.ai.pilot.imageprocessing.objectdetection;

import org.apache.camel.builder.RouteBuilder;

import javax.inject.Named;

/**
 * Object Detection Route Builder
 */
@Named
public class ObjectDetectionRouteBuilder extends RouteBuilder {

  public static final String OBJECT_DETECTION_INPUT_ENDPOINT = "seda:object_detection_input_endpoint";

  @Override
  public void configure() throws Exception {
    // TODO
  }

}
