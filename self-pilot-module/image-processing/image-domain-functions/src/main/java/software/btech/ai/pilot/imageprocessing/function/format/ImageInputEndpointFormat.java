package software.btech.ai.pilot.imageprocessing.function.format;

import software.btech.ai.pilot.imageprocessing.domain.Configuration;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Text formatting function which appends input file path to endpoint url
 */
@Named
public class ImageInputEndpointFormat implements BiFunction<Supplier<String>, Supplier<String>, String> {

  @Inject
  private Configuration configuration;

  @Override
  public String apply(Supplier<String> pythonScriptEndpointSupplier, Supplier<String> inputFilenameSupplier) {
    String fileSeparator = System.getProperty("file.separator");
    return String.format("%s %s%s%s",
      pythonScriptEndpointSupplier.get(),
      configuration.getImageStorageRoot(),
      fileSeparator,
      inputFilenameSupplier.get());
  }

}
