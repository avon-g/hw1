//https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.16.0/
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Logger;
import java.net.URL;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sellers {
    private String uri;
    final private Logger logger;

    ArrayList<Seller>  slist= new ArrayList<>();

    public Sellers(String uri, Logger logger) {
        this.uri = uri;
        this.logger = logger;
        dataRead();
    }

    void dataRead(){
        //Seller seller;
        List<Seller> myObjects;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        try {

            // works up to 10 time faster than TypeRefence
            myObjects = Arrays.asList(mapper.readValue(new URL(uri), Seller[].class)); //array

            //Seller[] myObjects = mapper.readValue(new URL(uri), Seller[].class);

            //Seller seller = mapper.readValue(new URL(uri), Seller.class);
            //seller = mapper.readValue(new URL(uri), Seller[].class);
            //Map<int , Object> m = mapper.readValue(new URL(uri), Seller.class);
            //logger.debug(String.valueOf(seller.getId()) + seller.getEmail());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Seller s: myObjects) {
            logger.debug(String.valueOf(s.getId()) + s.getEmail());
        }

        /*
        String jsonString = "  { \"id\": 2, \"email\": \"user2@example.com\",  \"city\": \"Minsk\",\n" +
                "    \"phone\": \"+375 29 123-45-67\",   \"name\": \"Elena Ivanova\"  }";

        try {
            Seller seller = objectMapper.readValue(jsonString, Seller.class);
            logger.debug(String.valueOf(seller.getId()) + seller.getEmail());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
*/

    }

}
