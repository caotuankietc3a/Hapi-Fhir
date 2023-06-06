package com.hl7v2.hapiexamples.provider;

import static net.logstash.logback.argument.StructuredArguments.entries;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.annotation.ConditionalUrlParam;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import com.hl7v2.hapiexamples.dao.AlarmsSettingsDAO;
import com.hl7v2.hapiexamples.dao.MeasureDAO;
import com.hl7v2.hapiexamples.dao.StatusDAO;
import com.hl7v2.hapiexamples.dao.VentilationSettingsDAO;
import com.hl7v2.hapiexamples.model.AlarmsSettings;
// import org.springframework.stereotype.Component;
import com.hl7v2.hapiexamples.model.Measure;
import com.hl7v2.hapiexamples.model.Status;
import com.hl7v2.hapiexamples.model.VentilationSettings;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.fluentd.logger.FluentLogger;
import org.fluentd.logger.FluentLoggerFactory;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Device;
import org.hl7.fhir.r4.model.Device.DevicePropertyComponent;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.Property;
import org.hl7.fhir.r4.model.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// @Component
public class DeviceResourceProvider implements IResourceProvider {
  private static final FhirContext fhirContext = FhirContext.forR4();
  private static final String FLUENTD_HOST = "localhost";
  private static final int FLUENTD_PORT = 24224;
  private static final String FLUENTD_TAG = "service.log";

  private static final Logger logger =
      LoggerFactory.getLogger(DeviceResourceProvider.class);

  private static final FluentLogger fluent_logger =
      FluentLogger.getLogger(FLUENTD_TAG, FLUENTD_HOST, FLUENTD_PORT);

  private Map<Integer, Device> devices = new HashMap<Integer, Device>();

  public DeviceResourceProvider() {
    // TODO document why this constructor is empty
  }

  @Override
  public Class<? extends IBaseResource> getResourceType() {
    return Device.class;
  }

  @Create
  public MethodOutcome create(@ResourceParam final Device device,
                              @ConditionalUrlParam String theConditionalUrl) {
    // if (theConditionalUrl != null) {
    //   System.out.println(theConditionalUrl.split("&").toString());
    //   createDeviceBasedOnConditionalUrl(device);
    // } else {
    //   createDeviceNormally(device);
    // }

    createDeviceNormally(device);

    Integer id = devices.size() + 1;
    devices.put(id, device);

    MethodOutcome retVal = new MethodOutcome();
    retVal.setId(new IdType("Device", id.toString(), "1"));
    retVal.setId(new IdType("Device", id.toString(), "1"));

    OperationOutcome outcome = new OperationOutcome();
    outcome.addIssue().setDiagnostics("One minor issue detected");
    retVal.setOperationOutcome(outcome);

    return retVal;
  }

  private void createDeviceBasedOnConditionalUrl(final Device device) {
    logger.info("LOG TEST DEVICE");
    fhirContext.newJsonParser().setPrettyPrint(true).encodeResourceToString(
        device);
  }

  private void createDeviceNormally(final Device device) {

    fhirContext.newJsonParser().setPrettyPrint(true).encodeResourceToString(
        device);

    for (DevicePropertyComponent prop : device.getProperty()) {
      String type = prop.getType().getCoding().get(0).getCode();
      // recordLogs(device.getDistinctIdentifier(), type,

      if (type.equals("measures")) {
        saveMeasure(generateMeasure(prop.getValueQuantity()));
      } else if (type.equals("status")) {
        saveStatus(generateStatus(prop.getValueQuantity()));
      } else if (type.equals("ventilation settings")) {
        saveVentilationSettings(
            generateventilationSettings(prop.getValueQuantity()));
      } else if (type.equals("alarms settings")) {
        saveAlarmsSettings(generateAlarmsSettings(prop.getValueQuantity()));
      }
    }
  }

  private void saveAlarmsSettings(AlarmsSettings generateAlarmsSettings) {
    try (ClassPathXmlApplicationContext context =
             new ClassPathXmlApplicationContext("spring.xml")) {
      AlarmsSettingsDAO alarmsSettingsDAO =
          context.getBean(AlarmsSettingsDAO.class);
      alarmsSettingsDAO.save(generateAlarmsSettings);
      logger.debug(generateAlarmsSettings.toString());
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  private AlarmsSettings generateAlarmsSettings(List<Quantity> valueQuantity) {
    AlarmsSettings alarmsSettings = new AlarmsSettings();
    for (Quantity var : valueQuantity) {
      double value = var.getValue().doubleValue();
      String code = var.getCode();
      if (code.equals("Ppeak")) {
        alarmsSettings.setPpeak(value);
      } else if (code.equals("VTiMin")) {
        alarmsSettings.setVtiMin(value);
      } else if (code.equals("VTiMax")) {
        alarmsSettings.setVtiMax(value);
      } else if (code.equals("VTeMin")) {
        alarmsSettings.setVteMin(value);
      } else if (code.equals("VTeMax")) {
        alarmsSettings.setVteMax(value);
      } else if (code.equals("MViMin")) {
        alarmsSettings.setMviMin(value);
      } else if (code.equals("MViMax")) {
        alarmsSettings.setMviMax(value);
      } else if (code.equals("MVeMin")) {
        alarmsSettings.setMveMin(value);
      } else if (code.equals("MVeMax")) {
        alarmsSettings.setMveMax(value);
      } else if (code.equals("RR min")) {
        alarmsSettings.setRrMin(value);
      } else if (code.equals("RR max")) {
        alarmsSettings.setRrMax(value);
      } else if (code.equals("Fio2Min")) {
        alarmsSettings.setFio2Min(value);
      } else if (code.equals("Fio2Max")) {
        alarmsSettings.setFio2Max(value);
      } else if (code.equals("Etco2Min")) {
        alarmsSettings.setEtco2Min(value);
      } else if (code.equals("Etco2Max")) {
        alarmsSettings.setEtco2Max(value);
      } else if (code.equals("Fico2Max")) {
        alarmsSettings.setFico2Max(value);
      } else if (code.equals("Pmin")) {
        alarmsSettings.setPmin(value);
      } else if (code.equals("PplatMax")) {
        alarmsSettings.setPplatMax(value);
      } else if (code.equals("FreqCTMin")) {
        alarmsSettings.setFreqCTMin(value);
      } else if (code.equals("FreqCTMax")) {
        alarmsSettings.setFreqCTMax(value);
      }
    }
    alarmsSettings.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    System.out.println(alarmsSettings.toString());
    return alarmsSettings;
  }

  private void
  saveVentilationSettings(VentilationSettings generateventilationSettings) {
    try (ClassPathXmlApplicationContext context =
             new ClassPathXmlApplicationContext("spring.xml")) {
      VentilationSettingsDAO ventilationSettingsDAO =
          context.getBean(VentilationSettingsDAO.class);
      ventilationSettingsDAO.save(generateventilationSettings);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  private VentilationSettings
  generateventilationSettings(List<Quantity> valueQuantity) {
    VentilationSettings ventilationSettings = new VentilationSettings();
    for (Quantity var : valueQuantity) {
      double value = var.getValue().doubleValue();
      String code = var.getCode();
      if (code.equals("VT")) {
        ventilationSettings.setVt(value);
      } else if (code.equals("RR")) {
        ventilationSettings.setRr(value);
      } else if (code.equals("RR mini")) {
        ventilationSettings.setRrMini(value);
      } else if (code.equals("RR VSIMV")) {
        ventilationSettings.setRrVsimv(value);
      } else if (code.equals("PI")) {
        ventilationSettings.setPi(value);
      } else if (code.equals("PS")) {
        ventilationSettings.setPs(value);
      } else if (code.equals("PEEP")) {
        ventilationSettings.setPeep(value);
      } else if (code.equals("I:E")) {
        ventilationSettings.setIE(value);
      } else if (code.equals("TI/Ttot")) {
        ventilationSettings.setTiTtot(value);
      } else if (code.equals("Timax")) {
        ventilationSettings.setTimax(value);
      } else if (code.equals("TrigI")) {
        ventilationSettings.setTrigI(value);
      } else if (code.equals("TrigE")) {
        ventilationSettings.setTrigE(value);
      } else if (code.equals("pressure slope")) {
        ventilationSettings.setPressureSlope(value);
      } else if (code.equals("PI sigh")) {
        ventilationSettings.setPiSigh(value);
      } else if (code.equals("Sigh period")) {
        ventilationSettings.setSighPeriod(value);
      } else if (code.equals("Vt target")) {
        ventilationSettings.setVtTarget(value);
      } else if (code.equals("FiO2")) {
        ventilationSettings.setFiO2(value);
      } else if (code.equals("Tplat")) {
        ventilationSettings.setTplat(value);
      } else if (code.equals("Vtapnea")) {
        ventilationSettings.setVtapnea(value);
      } else if (code.equals("f apnea")) {
        ventilationSettings.setFApnea(value);
      } else if (code.equals("T apnea")) {
        ventilationSettings.setTApnea(value);
      } else if (code.equals("Tins")) {
        ventilationSettings.setTins(value);
      } else if (code.equals("Pimax")) {
        ventilationSettings.setPimax(value);
      } else if (code.equals("f ent")) {
        ventilationSettings.setFEnt(value);
      } else if (code.equals("Tplat(sec)")) {
        ventilationSettings.setTplat(value);
      } else if (code.equals("PS PVACI")) {
        ventilationSettings.setPsPvaci(value);
      } else if (code.equals("FiO2 CPV")) {
        ventilationSettings.setFiO2Cpv(value);
      } else if (code.equals("RR CPV")) {
        ventilationSettings.setRrCpv(value);
      } else if (code.equals("PI CPV")) {
        ventilationSettings.setPiCpv(value);
      } else if (code.equals("Thigh CPV")) {
        ventilationSettings.setThighCpv(value);
      } else if (code.equals("Heigh")) {
        ventilationSettings.setHeigh(value);
      } else if (code.equals("Gender")) {
        ventilationSettings.setGender(value);
      } else if (code.equals("Coeff ml/kg")) {
        ventilationSettings.setCoeff(value);
      } else if (code.equals("O2 low pressure")) {
        ventilationSettings.setO2LowPressure(value);
      } else if (code.equals("Peak flow")) {
        ventilationSettings.setPeakFlow(value);
      }
    }
    ventilationSettings.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    logger.info(ventilationSettings.toString());
    return ventilationSettings;
  }

  private void saveStatus(Status generateStatus) {
    try (ClassPathXmlApplicationContext context =
             new ClassPathXmlApplicationContext("spring.xml")) {
      StatusDAO statusDAO = context.getBean(StatusDAO.class);
      statusDAO.save(generateStatus);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  private Status generateStatus(List<Quantity> valueQuantity) {
    Status status = new Status();
    for (Quantity var : valueQuantity) {
      int value = var.getValue().intValue();
      String code = var.getCode();
      if (code.equals("Ventilator State")) {
        status.setVentilatorState(value);
      } else if (code.equals("Patient Type")) {
        status.setPatientType(value);
      } else if (code.equals("Ventilator Mode")) {
        status.setVentilatorMode(value);
      } else if (code.equals("NIV")) {
        status.setNiv(value);
      } else if (code.equals("100% O2")) {
        status.setO2_100(value);
      } else if (code.equals("Expi. Flow Sensor")) {
        status.setExpiFlowSensor(value);
      } else if (code.equals("CO2 Sensor")) {
        status.setCo2Sensor(value);
      } else if (code.equals("Expi. Flow Sensor")) {
        status.setExpiFlowSensor(value);
      } else if (code.equals("Expi Pause")) {
        status.setExpiPause(value);
      } else if (code.equals("Inspi Pause")) {
        status.setInspiPause(value);
      }
    }
    status.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    logger.info(status.toString());
    return status;
  }

  private void recordLogs(String id, String type, List<Quantity> lQuantities) {
    Map<String, Object> logKeyValue2 = new HashMap<>();
    String[] identifier = id.split("-");
    logKeyValue2.put("device_name", identifier[0]);
    logKeyValue2.put("device_id", identifier[1]);
    logKeyValue2.put("type", type);
    for (Quantity var : lQuantities) {
      double value = var.getValue().doubleValue();
      String code = var.getCode();
      String unit = var.getUnit();
      logKeyValue2.put(code + "__value", value);
      logKeyValue2.put(code + "__unit", unit);
    }

    logger.info("LOG " + type.toUpperCase() + " DEVICE", entries(logKeyValue2));

    fluent_logger.log("@NORMAL", logKeyValue2);
  }

  private void saveMeasure(Measure measure) {
    try (ClassPathXmlApplicationContext context =
             new ClassPathXmlApplicationContext("spring.xml")) {
      MeasureDAO measureDAO = context.getBean(MeasureDAO.class);
      measureDAO.save(measure);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  private Measure generateMeasure(List<Quantity> lQuantities) {
    Measure measure = new Measure();
    for (Quantity var : lQuantities) {
      double value = var.getValue().doubleValue();
      String code =
          var.getCode().replaceAll(" ", "_").replaceAll("/", "_").toLowerCase();
      logger.info(code);
      if (code.equals("ppeak")) {
        measure.setPpeak(value);
      } else if (code.equals("vte")) {
        measure.setVte(value);
      } else if (code.equals("rr")) {
        measure.setRr(value);
      } else if (code.equals("mve")) {
        measure.setMve(value);
      } else if (code.equals("fio2")) {
        measure.setFiO2(value);
      } else if (code.equals("pmean")) {
        measure.setPmean(value);
      } else if (code.equals("pplat")) {
        measure.setPplat(value);
      } else if (code.equals("peep")) {
        measure.setPeep(value);
      } else if (code.equals("vti")) {
        measure.setVti(value);
      } else if (code.equals("mvi")) {
        measure.setMvi(value);
      } else if (code.equals("ti_ttot")) {
        measure.setTiTtot(value);
      } else if (code.equals("finco2")) {
        measure.setFinCO2(value);
      } else if (code.equals("etco2")) {
        measure.setEtCO2(value);
      }
    }
    measure.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    logger.info(measure.toString());
    return measure;
  }
}
