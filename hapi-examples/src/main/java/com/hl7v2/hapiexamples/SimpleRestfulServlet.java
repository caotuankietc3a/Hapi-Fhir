package com.hl7v2.hapiexamples;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.RestfulServer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet("/*")
public class SimpleRestfulServlet extends RestfulServer {
  @Override
  protected void initialize() throws ServletException {
    // Create a context for the appropriate version
    setFhirContext(FhirContext.forR4());

    // Register resource providers
    registerProvider(new PatientResourceProvider());
  }
}
