import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.*;

// fake sendmail
public class SendMail {
    void send(String text) {
        try {
            PrintWriter out = new PrintWriter(
                    new FileOutputStream( new File("./sendmail.txt"),
                   true )
            );
            out.println(text);
            out.close();
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
