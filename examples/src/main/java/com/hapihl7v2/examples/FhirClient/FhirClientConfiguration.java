package com.hapihl7v2.examples.FhirClient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
public class FhirClientConfiguration {
  static public final String URL = "http://localhost:8080/fhir";

  // @Bean
  public static FhirContext fhirContext() { return FhirContext.forR4(); }

  // @Bean
  public static IGenericClient fhirClient(FhirContext fhirContext) {
    return fhirContext.newRestfulGenericClient(URL);
  }
}
