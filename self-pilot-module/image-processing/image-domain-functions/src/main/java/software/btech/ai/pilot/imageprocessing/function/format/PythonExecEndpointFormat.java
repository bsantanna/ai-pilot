package software.btech.ai.pilot.imageprocessing.function.format;

import software.btech.ai.pilot.imageprocessing.domain.Configuration;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Text formatting function which produces a python executable endpoint target
 */
@Named
public class PythonExecEndpointFormat implements Function<Supplier<String>, String> {

  @Inject
  private Configuration configuration;

  @Override
  public String apply(Supplier<String> pythonScriptSupplier) {
    return String.format("exec:python3?args=%s%s%s",
      configuration.getPythonScriptRoot(),
      System.getProperty("file.separator"),
      pythonScriptSupplier.get());
  }

}
