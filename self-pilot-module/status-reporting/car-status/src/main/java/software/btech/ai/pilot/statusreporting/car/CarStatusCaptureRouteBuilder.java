package software.btech.ai.pilot.statusreporting.car;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.exec.ExecBinding;
import software.btech.ai.pilot.domain.Configuration;
import software.btech.ai.pilot.domain.ExchangePropertyConstants;
import software.btech.ai.pilot.function.format.AirSimInputEndpointFormat;
import software.btech.ai.pilot.function.format.JsonFileNameFormat;
import software.btech.ai.pilot.function.format.PythonScriptEndpointFormat;
import software.btech.ai.pilot.processor.PythonScriptOutputProcessor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

@Named
public class CarStatusCaptureRouteBuilder extends RouteBuilder {

  public static final String CAR_STATUS_INPUT_ENDPOINT = "seda:car_status_input_endpoint";

  private static final String CAR_STATUS_ENDPOINT = "seda:car_status";

  @Inject
  private Configuration configuration;

  @Inject
  private AirSimInputEndpointFormat airSimInputEndpointFormat;

  @Inject
  private PythonScriptEndpointFormat pythonScriptEndpointFormat;

  @Inject
  private PythonScriptOutputProcessor pythonScriptOutputProcessor;

  @Inject
  private JsonFileNameFormat jsonFileNameFormat;

  @Override
  public void configure() throws Exception {
    String carStatusCaptureInputEndpoint = null;

    if (configuration.getAirsimHost() != null) {
      carStatusCaptureInputEndpoint = airSimInputEndpointFormat.apply(
        () -> pythonScriptEndpointFormat.apply(
          configuration::getCarStatusCaptureScript,
          () -> jsonFileNameFormat.apply(ExchangePropertyConstants.CAR_STATUS_CAPTURE)));
    }

    configureCarStatusCaptureRoute(carStatusCaptureInputEndpoint);
  }

  private void configureCarStatusCaptureRoute(@NotNull String carStatusCaptureInputEndpoint) {
    from(CAR_STATUS_INPUT_ENDPOINT)
      .setProperty(
        ExchangePropertyConstants.PROCESSING_STEP,
        constant(ExchangePropertyConstants.CAR_STATUS_CAPTURE))
      .to(carStatusCaptureInputEndpoint)
      .choice()
      .when(header(ExecBinding.EXEC_EXIT_VALUE).isEqualTo(0))
      .process(pythonScriptOutputProcessor)
      .to(CAR_STATUS_ENDPOINT);
  }

}
