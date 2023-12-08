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
import java.util.HashMap;
import java.util.List;

public class Sellers {
    private String uri;
    final private Logger logger;
    HashMap<String , Seller> sellerMap = new HashMap<>();                   // fast search

    public Sellers(String uri, Logger logger) {
        this.uri = uri;
        this.logger = logger;
        dataRead();
    }



    /** read and parse data from url */
    void dataRead(){
        //Seller seller;
        List<Seller> sellerList;                                                // easy import
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        try {
            // "works up to 10 time faster than TypeRefence"
            sellerList = Arrays.asList(mapper.readValue(new URL(uri), Seller[].class)); //array
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        sellerMap.clear();
        // check phones, add data to map
        for (Seller s: sellerList) {
            String clearPhone = s.getPhone().replaceAll("[^0-9]","");

            if (!clearPhone.matches("^7[0-9]{10}|^375[0-9]{9}|^380[0-9]{9}") ) {
                logger.error(uri + " : ID#" + s.getId() + " bad Phone field. '" + s.getPhone() +"'. Marked");
                s.setPhone("[Error] " + s.getPhone());
            };

            sellerMap.put(String.valueOf(s.getId()) , s);
            //logger.debug(String.valueOf(s.getId()) + s.getPhone());
        }
    } // dataRead()


    Seller getSellerById(int id){
        return sellerMap.get(String.valueOf(id));
    }
}

// eof