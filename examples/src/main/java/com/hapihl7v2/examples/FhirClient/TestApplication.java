package com.hapihl7v2.examples.FhirClient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Device;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class TestApplication {
  // @Autowired private static IGenericClient fhirClient;
  // @Autowired private static FhirContext ctx;
  static public final ScheduledExecutorService scheduler =
      Executors.newScheduledThreadPool(1);

  static public final FhirContext ctx = FhirClientConfiguration.fhirContext();
  static public final IGenericClient fhirClient =
      FhirClientConfiguration.fhirClient(ctx);

  @Scheduled(fixedRate = 10000)
  public static void run() throws Exception {

    // Runnable runnable = () -> testApp();
    // while (true) {
    // scheduler.schedule(runnable, 1, TimeUnit.SECONDS);
    // testApp();
    // Create a logging interceptor
    LoggingInterceptor loggingInterceptor = new LoggingInterceptor(true);
    // loggingInterceptor.setLogRequestSummary(true);
    // loggingInterceptor.setLogRequestBody(true);

    // Optionally you may configure the interceptor (by default only
    // summary info is logged)
    fhirClient.registerInterceptor(loggingInterceptor);

    MethodOutcome outcome =
        fhirClient.create()
            .resource(testApp1())
            // .conditionalByUrl("Device?isManuallyCreated=0")
            .prettyPrint()
            .encodedJson()
            .execute();

    IIdType id = outcome.getId();
    System.out.println("Got ID: " + id.getValue());

    // Thread.sleep(2500);
    // }
    // FhirContext ctx = FhirContext.forR4();
  }
  public static Device testApp1() {
    int random = (int)(Math.random() * FhirDeviceService.DEVICES.length);
    Device device = FhirDeviceService.createDevice();

    device.setIdBase(generateRandomString(6));
    device.setId(generateRandomString(6));
    device.setDistinctIdentifier(FhirDeviceService.DEVICES[random] + "-" +
                                 generateRandomString(6));
    // Identifier identifier = new Identifier();
    // identifier.setSystem("http://example.org/devices");
    // identifier.setValue(FhirDeviceService.DEVICES[random]);
    // device.addIdentifier(identifier);

    String json =
        ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(device);
    System.out.println(json);
    return device;
  }

  public static String generateRandomString(int length) {
    String characters =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder sb = new StringBuilder(length);
    Random random = new Random();

    for (int i = 0; i < length; i++) {
      int index = random.nextInt(characters.length());
      char randomChar = characters.charAt(index);
      sb.append(randomChar);
    }

    return sb.toString();
  }

  public static void testApp() {
    int random = (int)(Math.random() * FhirService.displayValues.length);
    Observation observation = null;
    switch (random) {
    case 0: {
      observation = FhirService.createCPAPPressureLevelObservation();
      break;
    }
    case 1: {
      observation = FhirService.createCPAPUsageDurationObservation();
      break;
    }
    case 2: {
      observation = FhirService.createMaskPressureLeakageObservation();
      break;
    }
    case 3: {
      observation = FhirService.createRespiratoryRateObservation();
      break;
    }
    case 4: {
      observation = FhirService.createOxygenSaturationObservation();
      break;
    }
    case 5: {
      observation = FhirService.createApneaHypopneaIndexObservation();
      break;
    }
    case 6: {
      observation = FhirService.createMaskFitAssessmentObservation();
      break;
    }
    case 7: {
      observation = FhirService.createEventDetectionObservation();
      break;
    }
    case 8: {
      observation = FhirService.createComplianceDataObservation();
      break;
    }

    default:
      System.err.println("Random was not correct!");
      break;
    }

    // Patient patient = FhirService.createPatient();
    // Device device = FhirService.createDevice();
    printFHIRResource(ctx, observation);

    // MethodOutcome outcome = fhirClient.create()
    //                             .resource(observation)
    //                             .prettyPrint()
    //                             .encodedJson()
    //                             .execute();

    // IIdType id = outcome.getId();
    // System.out.println("Got ID: " + id.getValue());
    // printFHIRResource(ctx, patient);
    // printFHIRResource(ctx, device);
  }
  public static void printFHIRResource(FhirContext ctx,
                                       IBaseResource document) {
    System.out.println(
        "Document Resource: " +
        ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(
            document));
  }
}
