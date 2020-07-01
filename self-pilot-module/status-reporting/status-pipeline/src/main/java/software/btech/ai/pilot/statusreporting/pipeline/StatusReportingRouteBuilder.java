package software.btech.ai.pilot.statusreporting.pipeline;

import org.apache.camel.builder.RouteBuilder;
import software.btech.ai.pilot.function.format.TimerInputEndpointFormat;
import software.btech.ai.pilot.statusreporting.car.CarStatusCaptureRouteBuilder;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Status Reporting Route Builder
 */
@Named
public class StatusReportingRouteBuilder extends RouteBuilder {

  @Inject
  private TimerInputEndpointFormat timerInputEndpointFormat;

  @Override
  public void configure() throws Exception {
    from(timerInputEndpointFormat.apply(getClass().getSimpleName()))
      .to(CarStatusCaptureRouteBuilder.CAR_STATUS_INPUT_ENDPOINT);
  }

}
