package software.btech.ai.pilot.imageprocessing.pipeline.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import software.btech.ai.pilot.imageprocessing.domain.Configuration;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ImageOutputProcessor implements Processor {

  @Inject
  private Configuration configuration;

  @Override
  public void process(Exchange exchange) throws Exception {
    Byte imageOutput[] = exchange.getIn().getBody(Byte[].class);
    // TODO: store byte array in exchange property
    if (configuration.isDebugEnabled()) {
      // TODO: trigger file storage event
    }
  }

}
