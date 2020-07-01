package software.btech.ai.pilot.function.format;

import software.btech.ai.pilot.domain.Configuration;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Text formatting function which appends configured AirSim host to endpoint url
 */
@Named
public class AirSimInputEndpointFormat implements Function<Supplier<String>, String> {

  @Inject
  private Configuration configuration;

  @Override
  public String apply(Supplier<String> pythonScriptEndpointSupplier) {
    return String.format("%s %s",
      pythonScriptEndpointSupplier.get(),
      configuration.getAirsimHost());
  }

}
