package software.btech.ai.pilot.imageprocessing.domain;

/**
 * Configuration POJO for Image Processing Pipeline
 */
public class Configuration {

  private String airsimHost;

  private String pythonEnvironmentRoot;

  private String pythonScriptRoot;

  private Integer imageCaptureRepeatInterval;

  private String imageCaptureScript;

  private String imageStorageRoot;

  private Boolean debugMode;

  public String getPythonScriptRoot() {
    return pythonScriptRoot;
  }

  public void setPythonScriptRoot(String pythonScriptRoot) {
    this.pythonScriptRoot = pythonScriptRoot;
  }

  public Integer getImageCaptureRepeatInterval() {
    return imageCaptureRepeatInterval;
  }

  public void setImageCaptureRepeatInterval(Integer imageCaptureRepeatInterval) {
    this.imageCaptureRepeatInterval = imageCaptureRepeatInterval;
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
}
