import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.net.URI;
import java.net.URL;
import org.apache.logging.log4j.core.LoggerContext;





public class Main {
    final static Logger logger = LogManager.getLogger(Main.class);;

    public static void main(String[] args) {
        logger.info("Start HW#1");
        Sales sales = new Sales("./sales.txt" , logger);
        Sellers sellers = new Sellers(
                "https://gist.githubusercontent.com/isicju/7a14f1953781134f5a5538d8acfd3375/raw/" +
                        "1ce8df6f43f59a377a3b26c213cc1dc57f04a8e0/suppliers.json" ,
                logger);
    }
}



