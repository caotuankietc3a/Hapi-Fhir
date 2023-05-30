package com.hl7v2.hapiexamples.servlet;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.RestfulServer;
import com.hl7v2.hapiexamples.provider.DeviceResourceProvider;
import com.hl7v2.hapiexamples.provider.ObservationResourceProvider;
import com.hl7v2.hapiexamples.provider.PatientResourceProvider;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet("/fhir/*")
public class SimpleRestfulServlet extends RestfulServer {
  @Override
  protected void initialize() throws ServletException {
    // Create a context for the appropriate version
    setFhirContext(FhirContext.forR4());

    // Register resource providers
    registerProvider(new PatientResourceProvider());
    registerProvider(new ObservationResourceProvider());
    registerProvider(new DeviceResourceProvider());
  }
}
