import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;





public class Main {
    final static Logger logger = LogManager.getLogger(Main.class);;

    public static void main(String[] args) {
        logger.info("Start HW#1");

        Sales sales = new Sales("./sales.txt" , logger);

    }
}



