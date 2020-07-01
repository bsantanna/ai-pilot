package software.btech.ai.pilot.function.format;

import javax.inject.Named;
import java.util.function.UnaryOperator;

/**
 * Text formatting function which appends extension to file name
 */
@Named
public class JsonFileNameFormat implements UnaryOperator<String> {

  private final String extension;

  public JsonFileNameFormat() {
    extension = "json";
  }

  @Override
  public String apply(String s) {
    return String.format("%s.%s", s, extension);
  }
}
