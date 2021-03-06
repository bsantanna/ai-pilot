package software.btech.ai.pilot.domain;

/**
 * Configuration POJO for Image Processing Pipeline
 */
public class Configuration {

  private String airsimHost;

  private String pythonEnvironmentRoot;

  private String pythonScriptRoot;

  private Integer evaluationRepeatInterval;

  private String imageCaptureScript;

  private String imageStorageRoot;

  private String laneDetectionImageWarpProjectionScript;

  private String laneDetectionImagePerspectiveScript;

  private String laneDetectionImageEdgeScript;

  private String laneDetectionImageCurveFitScript;

  private String carStatusCaptureScript;

  private Boolean debugMode;

  public String getPythonScriptRoot() {
    return pythonScriptRoot;
  }

  public void setPythonScriptRoot(String pythonScriptRoot) {
    this.pythonScriptRoot = pythonScriptRoot;
  }

  public Integer getEvaluationRepeatInterval() {
    return evaluationRepeatInterval;
  }

  public void setEvaluationRepeatInterval(Integer evaluationRepeatInterval) {
    this.evaluationRepeatInterval = evaluationRepeatInterval;
  }

  public String getImageCaptureScript() {
    return imageCaptureScript;
  }

  public void setImageCaptureScript(String imageCaptureScript) {
    this.imageCaptureScript = imageCaptureScript;
  }

  public Boolean isDebugEnabled() {
    return getDebugMode();
  }

  public Boolean getDebugMode() {
    return debugMode;
  }

  public void setDebugMode(Boolean debugMode) {
    this.debugMode = debugMode;
  }

  public String getPythonEnvironmentRoot() {
    return pythonEnvironmentRoot;
  }

  public void setPythonEnvironmentRoot(String pythonEnvironmentRoot) {
    this.pythonEnvironmentRoot = pythonEnvironmentRoot;
  }

  public String getImageStorageRoot() {
    return imageStorageRoot;
  }

  public void setImageStorageRoot(String imageStorageRoot) {
    this.imageStorageRoot = imageStorageRoot;
  }

  public String getAirsimHost() {
    return airsimHost;
  }

  public void setAirsimHost(String airsimHost) {
    this.airsimHost = airsimHost;
  }

  public String getLaneDetectionImageWarpProjectionScript() {
    return laneDetectionImageWarpProjectionScript;
  }

  public void setLaneDetectionImageWarpProjectionScript(String laneDetectionImageWarpProjectionScript) {
    this.laneDetectionImageWarpProjectionScript = laneDetectionImageWarpProjectionScript;
  }

  public String getLaneDetectionImagePerspectiveScript() {
    return laneDetectionImagePerspectiveScript;
  }

  public void setLaneDetectionImagePerspectiveScript(String laneDetectionImagePerspectiveScript) {
    this.laneDetectionImagePerspectiveScript = laneDetectionImagePerspectiveScript;
  }

  public String getLaneDetectionImageEdgeScript() {
    return laneDetectionImageEdgeScript;
  }

  public void setLaneDetectionImageEdgeScript(String laneDetectionImageEdgeScript) {
    this.laneDetectionImageEdgeScript = laneDetectionImageEdgeScript;
  }

  public String getLaneDetectionImageCurveFitScript() {
    return laneDetectionImageCurveFitScript;
  }

  public void setLaneDetectionImageCurveFitScript(String laneDetectionImageCurveFitScript) {
    this.laneDetectionImageCurveFitScript = laneDetectionImageCurveFitScript;
  }

  public String getCarStatusCaptureScript() {
    return carStatusCaptureScript;
  }

  public void setCarStatusCaptureScript(String carStatusCaptureScript) {
    this.carStatusCaptureScript = carStatusCaptureScript;
  }
}
