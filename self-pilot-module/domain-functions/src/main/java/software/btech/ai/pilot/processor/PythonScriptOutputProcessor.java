package software.btech.ai.pilot.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.FileUtils;

import javax.inject.Named;
import java.io.File;

import static software.btech.ai.pilot.domain.ExchangePropertyConstants.PROCESSING_STEP;

/**
 * Python Script output processor reads image supplied by python script stdout and publishes in Exchange.
 */
@Named
public class PythonScriptOutputProcessor implements Processor {

  @Override
  public void process(Exchange exchange) throws Exception {
    // obtain filename, std out from script
    String filename = exchange.getIn().getBody(String.class);
    File resultFile = new File(filename.trim());

    // obtain current processing step
    String processingStep = exchange.getProperty(PROCESSING_STEP, String.class);

    // store result in properties for late access
    byte[] result = FileUtils.readFileToByteArray(resultFile);
    exchange.setProperty(processingStep, result);

  }

}
