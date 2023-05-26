package com.hapihl7v2.examples;
import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.llp.LLPException;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.util.Hl7InputStreamMessageIterator;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SendLotsOfMessages {
  public static void run(int port, boolean useTls)
      throws FileNotFoundException, HL7Exception, LLPException {
    FileReader reader = new FileReader("src/main/resources/messages_file.txt");

    Hl7InputStreamMessageIterator iter =
        new Hl7InputStreamMessageIterator(reader);

    Connection conn = null;
    try (HapiContext context = new DefaultHapiContext()) {
      while (iter.hasNext()) {
        if (conn == null) {
          conn = context.newClient("localhost", port, useTls);
        }

        Message next = iter.next();
        Message response = conn.getInitiator().sendAndReceive(next);
        System.out.println("Client received response:\n" + response.encode() +
                           "\n");
      }

    } catch (Exception e) {
      System.out.println("Didn't send out this message!");
      e.printStackTrace();
    } finally {
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
  }
}
