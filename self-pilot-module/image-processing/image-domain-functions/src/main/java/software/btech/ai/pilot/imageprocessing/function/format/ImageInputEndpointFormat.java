package software.btech.ai.pilot.imageprocessing.function.format;

import software.btech.ai.pilot.imageprocessing.domain.Configuration;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

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
    StringBuilder formatted = new StringBuilder();
    formatted.append(pythonScriptEndpointSupplier.get());
    Stream<String> inputFilename = Stream.of(inputFilenameSupplier.get().split(" "));
    inputFilename.forEach(file ->
      formatted.append(String.format(" %s%s%s", configuration.getImageStorageRoot(), fileSeparator, file)));
    return formatted.toString();
  }

}
