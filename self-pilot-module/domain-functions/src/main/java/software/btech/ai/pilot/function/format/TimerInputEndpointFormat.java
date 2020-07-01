package software.btech.ai.pilot.function.format;

import software.btech.ai.pilot.domain.Configuration;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.function.UnaryOperator;

@Named
public class TimerInputEndpointFormat implements UnaryOperator<String> {

  @Inject
  private Configuration configuration;

  @Override
  public String apply(String logName) {
    return String.format("timer://%s?fixedRate=true&period=%ss",
      logName, configuration.getEvaluationRepeatInterval());
  }

}
