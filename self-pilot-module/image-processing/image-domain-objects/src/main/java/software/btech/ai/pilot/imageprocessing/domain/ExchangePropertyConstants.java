package software.btech.ai.pilot.imageprocessing.domain;

/**
 * Constants used for storing properties in transient storage.s
 */
public final class ExchangePropertyConstants {

  private ExchangePropertyConstants() {
    // utility class
  }

  public static final String IMAGE_PROCESSING_STEP = "image_processing_step";

  public static final String IMAGE_CAPTURE = "image_capture";

  public static final String LANE_DETECTION_IMAGE_MASK = "lane_detection_image_mask";

  public static final String LANE_DETECTION_IMAGE_WARP_PROJECTION = "lane_detection_image_warp_projection";

  public static final String LANE_DETECTION_IMAGE_PERSPECTIVE = "lane_detection_image_perspective";

  public static final String LANE_DETECTION_IMAGE_EDGE = "lane_detection_image_edge";

  public static final String LANE_DETECTION_IMAGE_CURVE_FIT = "lane_detection_image_curve_fit";

}
