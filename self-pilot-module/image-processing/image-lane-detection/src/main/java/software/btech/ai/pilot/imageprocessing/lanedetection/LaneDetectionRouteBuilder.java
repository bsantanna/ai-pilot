package software.btech.ai.pilot.imageprocessing.lanedetection;

import org.apache.camel.builder.RouteBuilder;

import javax.inject.Named;

/**
 * Lane Detection Route Builder
 */
@Named
public class LaneDetectionRouteBuilder extends RouteBuilder {

  public static final String LANE_DETECTION_INPUT_ENDPOINT = "seda:lane_detection_input_endpoint";

  @Override
  public void configure() throws Exception {
    // TODO
  }

}
