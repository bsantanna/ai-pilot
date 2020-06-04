package software.btech.ai.pilot.imageprocessing.function.format;

import software.btech.ai.pilot.imageprocessing.domain.Configuration;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Text formatting function which produces a python executable endpoint target uri
 */
@Named
public class PythonScriptEndpointFormat implements BiFunction<Supplier<String>, Supplier<String>, String> {

  @Inject
  private Configuration configuration;

  @Override
  public String apply(Supplier<String> pythonScriptSupplier, Supplier<String> filenameSupplier) {
    String fileSeparator = System.getProperty("file.separator");
    return String.format(
      "exec:%s%sbin%spython3?args=%s%s%s %s%s%s %s",

      // python bin path with conda env
      configuration.getPythonEnvironmentRoot(),
      fileSeparator,
      fileSeparator,

      // python script path
      configuration.getPythonScriptRoot(),
      fileSeparator,
      pythonScriptSupplier.get(),

      // filename path
      configuration.getImageStorageRoot(),
      fileSeparator,
      filenameSupplier.get(),

      // airsim hostname
      configuration.getAirsimHost()
    );
  }

}
