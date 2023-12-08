import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/** Read given file into List */
public class Sales {
    private String uri;                                                         // may vary later
    final private Logger logger;
    private Sellers sellers;

    /** record subclass */
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
    private ArrayList<Sale> salesCount =  new ArrayList<>();                    // same object, string is just unused


    public Sales(String uri , Logger logger , Sellers sellers){
        this.uri = uri;
        this.logger = logger;
        this.sellers = sellers;
        dataRead();
    }


    /** check String int or not */
    private boolean isNotInt(String s) {                                        // integer detector
        String digits = s.replaceAll("[^0-9]","");
        String garb = s.trim().replaceAll("[0-9]","");         // any garbage?
        return  (digits.isEmpty() || !garb.isEmpty() );                         // true = not integer
    };


    /** read and parse data from file */
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
                    logger.error(uri + " : Line  #" + lineNumber + " bad ID field'" + fields[1] + "'. Skipped");
                    continue;
                }
                int id = Integer.parseInt(fields[1].trim());

                // amount - float point
                String dirtyAmount = fields[3].replaceAll("[^0-9.]","");
                String garbAmount = fields[3].trim().replaceAll("[0-9.]","");
                if (dirtyAmount.isEmpty() || !garbAmount.isEmpty() ) {
                    logger.error(uri + " : Line  #" + lineNumber + " bad Amount field. '" + fields[3] + "'. Skipped");
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
        }
        catch (IOException e) {
                e.printStackTrace();
        }

        // todo: sort arrayList by id here. But I am short in time
    } // dataRead()


    /** make counts */
    public void makeCounts(){
        int currentId = -1;                                                     // initial ID
        int oldId = -1;
        double sum = 0;
        salesCount.clear();

        for (Sale sale: salesArray) {                                           // longer, faster for sorted arrayLists
            int id = sale.id;
            double amount = sale.amount;
            if (currentId != id) {
                if (currentId != -1) {                                          // checkout
                    salesCount.add(new Sale(currentId, "", sum));
                    //logger.info(currentId + " " + String.format("%.2f" , sum) );
                }
                sum = 0;
                currentId = id;
            }
            sum += amount;
        }

        Collections.sort(salesCount,
                new Comparator<Sale>() {
                    @Override
                    public int compare(Sale val1, Sale val2) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return val1.amount > val2.amount ? -1 : (val1.amount < val2.amount) ? 1 : 0;
                }
        });

    }


    /** make report and "send by email" */
    public void makeReport() {
        SendMail sendMail = new SendMail();
        for (Sale s : salesCount) {
            Seller seller = sellers.getSellerById(s.id);
            String report;
            if (seller != null) {
                 report = "id: " + s.id + "\n"
                 + "name: " + seller.getName() + "\n"
                 + "city: " + seller.getCity() + "\n"
                 + "phone: " + seller.getPhone() + "\n"
                 + "email: " + seller.getEmail() + "\n"
                 + "Amount: " + s.amount + "\n\n";
            }
            else {
                report = "id: " + s.id + "\n"
                        + "Amount: " + s.amount + "\n"
                        + "No other data \n\n";
            }
            sendMail.send(report);
        }
    }
}

// eof
