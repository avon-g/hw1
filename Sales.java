import org.apache.logging.log4j.Logger;

import java.io.*;

public class Sales {
    private String uri;                                                         // may vary later
    final private Logger logger;

    public Sales(String uri , Logger logger){
        this.uri = uri;
        this.logger = logger;
        dataRead();
    }

    void dataRead(){
        logger.info("Sales dataRead start");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(uri));
        // reader.readLine()

        int lineNumber = -1;
        try (BufferedReader reader = new BufferedReader(new FileReader(uri))) {
            String line = "";

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] fields = line.split("\\|" , 6 );

                if (fields.length != 5) {
                    logger.warn("Line  #" + lineNumber + " is skipped");
                    continue;
                }

                String dirty_id = fields[1].replaceAll("[^0-9]","");
                if (dirty_id.isEmpty()) {
                    logger.warn("Line  #" + lineNumber + " bad ID. Skipped");
                    continue;
                }

                int id = Integer.parseInt(dirty_id);
                logger.debug(id + fields[2] + fields[3]);

/*
                String ip = params[0];
                String user = params[1];
                Date date = readDate(params[2]);
                Event event = readEvent(params[3]);
                int eventAdditionalParameter = -1;
                if (event.equals(Event.SOLVE_TASK) || event.equals(Event.DONE_TASK)) {
                    eventAdditionalParameter = readAdditionalParameter(params[3]);
                }
                Status status = readStatus(params[4]);

                LogEntity logEntity = new LogEntity(ip, user, date, event, eventAdditionalParameter, status);
                logEntities.add(logEntity);

 */
            }
        } catch (IOException e) {
                e.printStackTrace();
        }


    }
}

// eof
