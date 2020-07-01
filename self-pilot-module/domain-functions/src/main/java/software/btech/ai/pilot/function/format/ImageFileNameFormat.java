package software.btech.ai.pilot.function.format;

import javax.inject.Named;
import java.util.function.UnaryOperator;

/**
 * Text formatting function which appends extension to file name
 */
@Named
public class ImageFileNameFormat implements UnaryOperator<String> {

  private final String extension;

  public ImageFileNameFormat() {
    extension = "jpg";
  }

  @Override
  public String apply(String s) {
    return String.format("%s.%s", s, extension);
  }
}
