package software.btech.ai.pilot.imageprocessing.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.FileUtils;

import javax.inject.Named;
import java.io.File;

import static software.btech.ai.pilot.imageprocessing.domain.ExchangePropertyConstants.IMAGE_PROCESSING_STEP;

/**
 * Python Script output processor reads image supplied by python script stdout and publishes in Exchange.
 */
@Named
public class PythonScriptOutputProcessor implements Processor {

  @Override
  public void process(Exchange exchange) throws Exception {
    // obtain filename, std out from script
    String imageFilename = exchange.getIn().getBody(String.class);
    File imageFile = new File(imageFilename.trim());

    // obtain current processing step
    String imageProcessingStep = exchange.getProperty(IMAGE_PROCESSING_STEP, String.class);

    // store image in properties for late access
    byte[] image = FileUtils.readFileToByteArray(imageFile);
    exchange.setProperty(imageProcessingStep, image);

  }

}