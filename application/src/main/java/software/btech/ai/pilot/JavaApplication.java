package software.btech.ai.pilot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.function.Supplier;

public class JavaApplication extends CamelContextApplication {

  private static Logger LOG = LoggerFactory.getLogger(JavaApplication.class);

  protected JavaApplication(Supplier<ApplicationContext> applicationContextSupplier) throws Exception {
    super(applicationContextSupplier);
  }

  public static void main(String... args) throws Exception {
    new JavaApplication(() -> new ClassPathXmlApplicationContext("classpath:applicationContext.xml"));

    while (true) {
      Thread.sleep(5 * 60 * 1000L);
      LOG.info(
        String.format("%s: heartbeat", JavaApplication.class.getSimpleName())
      );
    }
  }

}
