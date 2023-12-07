import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Logger;

public class Sellers {
    private String uri;
    final private Logger logger;

    public Sellers(String uri, Logger logger) {
        this.uri = uri;
        this.logger = logger;
        dataRead();
    }

    void dataRead(){
        //Seller seller;
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonString = "  { \"id\": 2, \"email\": \"user2@example.com\",  \"city\": \"Minsk\",\n" +
                "    \"phone\": \"+375 29 123-45-67\",   \"name\": \"Elena Ivanova\"  }";

        try {
            Seller seller = objectMapper.readValue(jsonString, Seller.class);
            logger.debug(String.valueOf(seller.getId()) + seller.getEmail());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }

}
