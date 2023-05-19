package com.hapihl7v2.examples;

import ca.uhn.fhir.rest.server.interceptor.LoggingInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExamplesApplication {

  public static void main(String[] args) throws Exception {

    // CreateAMessage message = new CreateAMessage();
    // SendAndReceiveAMessage sendAndReceiveAMessage =
    //     new SendAndReceiveAMessage();
    SpringApplication.run(ExamplesApplication.class, args);
  }

  // @Bean
  // public LoggingInterceptor loggingInterceptor() {
  //   return new LoggingInterceptor();
  // }
}
