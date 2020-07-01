package software.btech.ai.pilot;

import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.DefaultErrorHandlerBuilder;
import org.apache.camel.builder.ErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import software.btech.ai.pilot.imageprocessing.lanedetection.LaneDetectionRouteBuilder;
import software.btech.ai.pilot.imageprocessing.objectdetection.ObjectDetectionRouteBuilder;
import software.btech.ai.pilot.imageprocessing.pipeline.ImageProcessingRouteBuilder;
import software.btech.ai.pilot.statusreporting.car.CarStatusCaptureRouteBuilder;
import software.btech.ai.pilot.statusreporting.pipeline.StatusReportingRouteBuilder;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * Application context which initializes Apache Camel application context
 */
public class CamelContextApplication {

  private final CamelContext camelContext;

  private final ApplicationContext applicationContext;

  protected CamelContextApplication(
    Supplier<ApplicationContext> applicationContextSupplier) throws Exception {

    this(applicationContextSupplier,
      () -> new DefaultErrorHandlerBuilder()
        .maximumRedeliveries(3)
        .retryAttemptedLogLevel(LoggingLevel.WARN)
        .asyncDelayedRedelivery());

  }

  protected CamelContextApplication(
    Supplier<ApplicationContext> applicationContextSupplier,
    Supplier<ErrorHandlerBuilder> errorHandlerBuilderSupplier) throws Exception {

    applicationContext = applicationContextSupplier.get();
    camelContext = new SpringCamelContext(applicationContext);
    camelContext.setErrorHandlerBuilder(errorHandlerBuilderSupplier.get());
    camelContext.setUseMDCLogging(true);
    for (Class<? extends RouteBuilder> route : getRoutes()) {
      camelContext.addRoutes(applicationContext.getBean(route));
    }
    camelContext.start();
  }

  /**
   * @return Application Routes
   */
  protected Iterable<Class<? extends RouteBuilder>> getRoutes() {
    return Arrays.asList(
      CarStatusCaptureRouteBuilder.class,
      ImageProcessingRouteBuilder.class,
      LaneDetectionRouteBuilder.class,
      ObjectDetectionRouteBuilder.class,
      StatusReportingRouteBuilder.class
    );
  }

  /**
   * @return application context
   */
  protected final ApplicationContext getApplicationContext() {
    return applicationContext;
  }

}
