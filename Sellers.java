import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Sellers {
    private String uri;
    final private Logger logger;


    static public class Seller {
        int id;
        String email;
        String city;
        String phone;
        String name;
        public Seller(int id, String email, String city, String phone, String name) {
            this.id = id;
            this.email = email;
            this.city = city;
            this.phone = phone;
            this.name = name;
        }
    }

    public Sellers(String uri, Logger logger) {
        this.uri = uri;
        this.logger = logger;
        dataRead();
    }

    void dataRead(){

    }

}
