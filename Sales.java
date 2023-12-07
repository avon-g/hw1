import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;

/** Read given file into List */
public class Sales {
    private String uri;                                                         // may vary later
    final private Logger logger;

    static public class Sale {                                                  // data storage type
        int id;
        String month ;
        double amount;
        public Sale(int id, String month, double amount) {
            this.id = id;
            this.month = month;
            this.amount = amount;
        }
    }
    private ArrayList<Sale> salesArray =  new ArrayList<>();                    // data storage


    public Sales(String uri , Logger logger){
        this.uri = uri;
        this.logger = logger;
        dataRead();
    }


    private boolean isNotInt(String s) {                                        // integer detector
        String digits = s.trim().replaceAll("[^0-9]","");
        String garb = s.trim().replaceAll("[0-9]","");         // any garbage?
        return  (digits.isEmpty() || !garb.isEmpty() );                         // true = not integer
    };


    public void dataRead(){
        logger.info("Sales dataRead start");
        salesArray.clear();

        int lineNumber = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(uri))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                // explode fields by "|"
                String[] fields = line.split("\\|" , 6 );
                if (fields.length != 5) {
                    logger.warn(uri + " : Line  #" + lineNumber + " is skipped");
                    continue;
                }

                // ID - integer
                if (isNotInt(fields[1]) ) {
                    logger.error(uri + " : Line  #" + lineNumber + " bad ID '" + fields[1] + "'. Skipped");
                    continue;
                }
                int id = Integer.parseInt(fields[1].trim());

                // amount - float point
                String dirtyAmount = fields[3].trim().replaceAll("[^0-9.]","");
                String garbAmount = fields[3].trim().replaceAll("[0-9.]","");
                if (dirtyAmount.isEmpty() || !garbAmount.isEmpty() ) {
                    logger.error(uri + " : Line  #" + lineNumber + " bad Amount. '" + fields[3] + "'. Skipped");
                    continue;
                }
                double amount = Double.parseDouble(dirtyAmount);

                // Month - complex of 2 parts
                String[] dates = fields[2].trim().split("-" , 3 );
                if (dates.length != 2) {
                    logger.error(uri + " : Line  #" + lineNumber + " bad Month field. '" + fields[2] +"'. Skipped");
                    continue;
                }
                // year - uint
                if (isNotInt(dates[1]) ) {
                    logger.error(uri + " : Line  #" + lineNumber + " bad Month field. '" + fields[2] +"'. Skipped");
                    continue;
                }
                int year = Integer.parseInt(dates[1].trim());
                // months names
                if (!dates[0].trim().matches("JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC") ) {
                    logger.error(uri + " : Line  #" + lineNumber + " bad Month field. '" + fields[2] +"'. Skipped");
                    continue;
                };
                String monthName = dates[0].trim();
                //logger.debug(lineNumber + ":\t" + id + "\t" + monthName + "-" + year + "\t" + amount);
                salesArray.add(new Sale(id, monthName + "-" + String.valueOf(year), amount ) );
            }
        } catch (IOException e) {
                e.printStackTrace();
        }


    }
}

// eof
