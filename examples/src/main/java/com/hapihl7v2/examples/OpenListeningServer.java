package com.hapihl7v2.examples;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.ConnectionListener;
import ca.uhn.hl7v2.app.HL7Service;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import ca.uhn.hl7v2.protocol.ReceivingApplicationExceptionHandler;
import ca.uhn.hl7v2.validation.builder.support.NoValidationBuilder;
import java.util.Map;

public class OpenListeningServer {
  public static final int PORT = 8081;
  public static final boolean TLS = false;
  public static void run() throws Exception {

    try (HapiContext context = new DefaultHapiContext()) {
      context.setValidationRuleBuilder(new NoValidationBuilder());
      HL7Service server = context.newServer(PORT, TLS);

      ReceivingApplication<Message> handler = new ExampleReceiverApplication();
      server.registerApplication(handler);

      server.registerConnectionListener(new MyConnectionListener());

      server.setExceptionHandler(new MyExceptionHandler());

      // Start the server listening for messages
      server.startAndWait();

      SendLotsOfMessages.run(PORT, TLS);
      server.stopAndWait();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static class MyConnectionListener implements ConnectionListener {

    public void connectionReceived(Connection theC) {
      System.out.println("New connection received: " +
                         theC.getRemoteAddress().toString());
    }

    public void connectionDiscarded(Connection theC) {
      System.out.println("Lost connection from: " +
                         theC.getRemoteAddress().toString());
    }
  }

  /**
   * Exception handler which is notified any time
   */
  public static class MyExceptionHandler
      implements ReceivingApplicationExceptionHandler {

    public String processException(String theIncomingMessage,
                                   Map<String, Object> theIncomingMetadata,
                                   String theOutgoingMessage, Exception theE) {

      return theOutgoingMessage;
    }
  }
}
