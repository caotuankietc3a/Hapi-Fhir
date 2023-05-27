package com.hapihl7v2.examples;

import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;
import com.hapihl7v2.examples.test.RestfulPatientResourceProvider;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = {"/fhir/*"}, displayName = "FHIR Server")
public class ExampleRestfulServlet extends RestfulServer {

  private static final long serialVersionUID = 1L;
  @Override
  protected void initialize() throws ServletException {
    List<IResourceProvider> resourceProviders =
        new ArrayList<IResourceProvider>();
    resourceProviders.add(new RestfulPatientResourceProvider());
    setResourceProviders(resourceProviders);
  }
}
