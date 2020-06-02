package software.btech.ai.pilot.imageprocessing.domain;

/**
 * Configuration POJO for Image Processing Pipeline
 */
public class Configuration {

  private String pythonScriptRoot;

  private Integer imageCaptureRepeatInterval;

  private String imageCaptureScript;

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
}
