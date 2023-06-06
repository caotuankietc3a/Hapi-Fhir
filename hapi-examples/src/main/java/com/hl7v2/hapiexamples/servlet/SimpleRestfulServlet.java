package com.hl7v2.hapiexamples.servlet;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.exceptions.InternalErrorException;
import ca.uhn.fhir.rest.server.interceptor.ExceptionHandlingInterceptor;
import ca.uhn.fhir.rest.server.interceptor.LoggingInterceptor;
import ca.uhn.fhir.rest.server.interceptor.ResponseHighlighterInterceptor;
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

    ResponseHighlighterInterceptor interceptorResponseHighlighterInterceptor =
        new ResponseHighlighterInterceptor();
    registerInterceptor(interceptorResponseHighlighterInterceptor);

    // Now register the logging interceptor
    LoggingInterceptor loggingInterceptor = new LoggingInterceptor();
    registerInterceptor(loggingInterceptor);

    // The SLF4j logger "test.accesslog" will receive the logging events
    loggingInterceptor.setLoggerName("test.accesslog");

    // This is the format for each line. A number of substitution variables may
    // be used here. See the JavaDoc for LoggingInterceptor for information on
    // what is available.
    loggingInterceptor.setMessageFormat(
        "Source[${remoteAddr}] Operation[${operationType} ${idOrResourceName}] UA[${requestHeader.user-agent}] Params[${requestParameters}]");

    ExceptionHandlingInterceptor interceptor =
        new ExceptionHandlingInterceptor();
    registerInterceptor(interceptor);

    // Return the stack trace to the client for the following exception types
    interceptor.setReturnStackTracesForExceptionTypes(
        InternalErrorException.class, NullPointerException.class);

    // Register resource providers
    registerProvider(new PatientResourceProvider());
    registerProvider(new ObservationResourceProvider());
    registerProvider(new DeviceResourceProvider());
  }
}
