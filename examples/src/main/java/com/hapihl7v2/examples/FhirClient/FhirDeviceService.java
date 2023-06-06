package com.hapihl7v2.examples.FhirClient;

import ca.uhn.fhir.context.FhirContext;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.r4.model.codesystems.DeviceStatus;

public class FhirDeviceService {
  public static final String[] DEVICES =
      new String[] {"(A)VCV", "(A)PCV",     "CPAP",   "PSV",  "SIMV",
                    "PSIMV",  "Duo-Levels", "Ps-PRO", "PRVC", "Oxygen therapy"};

  private static final Map<String, String> MEASURES = new HashMap<>();
  private static final Map<String, String> VENTILATIONSETTINGS =
      new HashMap<>();
  private static final Map<String, String> ALARMSSETTINGS = new HashMap<>();

  private static final Map<String, Map<Integer, String>> STATUS =
      new HashMap<>();

  static {
    MEASURES.put("Ppeak", "cmH2O");
    MEASURES.put("VTe", "mL");
    MEASURES.put("RR", "c/min");
    MEASURES.put("MVe", "L/min");
    MEASURES.put("FiO2", "%");
    MEASURES.put("Pmean", "cmH2O");
    MEASURES.put("Pplat", "cmH2O");
    MEASURES.put("PEEP", "cmH2O");
    MEASURES.put("VTi", "mL");
    MEASURES.put("MVi", "L/min");
    MEASURES.put("TI/Ttot", "%");
    MEASURES.put("FinCO2", "%");
    MEASURES.put("etCO2", "%");

    Map<Integer, String> map = new HashMap<>();
    map.put(1, "Auto Tests");
    map.put(2, "Maintenance");
    map.put(3, "Ventilation");
    STATUS.put("Ventilator State", map);

    Map<Integer, String> map1 = new HashMap<>();
    map1.put(0, "Adult");
    map1.put(1, "Child");
    map1.put(2, "Infant");
    STATUS.put("Patient Type", map1);

    Map<Integer, String> map2 = new HashMap<>();
    for (int i = 0; i < DEVICES.length; i++) {
      map2.put(i, DEVICES[i]);
    }
    STATUS.put("Ventilator Mode", map2);

    Map<Integer, String> map3 = new HashMap<>();
    map3.put(0, "OFF");
    map3.put(1, "ON");
    STATUS.put("NIV", map3);
    STATUS.put("100% O2", map3);
    STATUS.put("Expi. Flow Sensor", map3);
    STATUS.put("CO2 Sensor", map3);
    STATUS.put("Expi. Flow Sensor", map3);
    STATUS.put("Expi Pause", map3);
    STATUS.put("Inspi Pause", map3);

    VENTILATIONSETTINGS.put("VT", "mL");
    VENTILATIONSETTINGS.put("RR", "c/min");
    VENTILATIONSETTINGS.put("RR mini", "c/min");
    VENTILATIONSETTINGS.put("RR VSIMV", "c/min");
    VENTILATIONSETTINGS.put("PI", "cmH2O");
    VENTILATIONSETTINGS.put("PS", "cmH2O");
    VENTILATIONSETTINGS.put("PEEP", "cmH2O");
    VENTILATIONSETTINGS.put("I:E", "1/0.1");
    VENTILATIONSETTINGS.put("TI/Ttot", "%");
    VENTILATIONSETTINGS.put("Timax", "ms");
    VENTILATIONSETTINGS.put("TrigI", "L/min");
    VENTILATIONSETTINGS.put("TrigE", "%");
    VENTILATIONSETTINGS.put("pressure slope", "cmH2O/sec");
    VENTILATIONSETTINGS.put("PI sigh", "% of PI");
    VENTILATIONSETTINGS.put("Sigh period", "cycle");
    VENTILATIONSETTINGS.put("Vt target", "mL");
    VENTILATIONSETTINGS.put("FiO2", "%");
    // VENTILATIONSETTINGS.put("Flow shape",
    //                         "0: constant flow 1: decelerated flow");
    VENTILATIONSETTINGS.put("Tplat", "%");
    VENTILATIONSETTINGS.put("Vtapnea", "mL");
    VENTILATIONSETTINGS.put("f apnea", "cpm");
    VENTILATIONSETTINGS.put("T apnea", "sec");
    VENTILATIONSETTINGS.put("Tins", "ms");
    VENTILATIONSETTINGS.put("Pimax", "cmH2O");
    VENTILATIONSETTINGS.put("f ent", "c/min");
    VENTILATIONSETTINGS.put("Tplat(sec)", "ms");
    VENTILATIONSETTINGS.put("PS PVACI", "cmH2O");
    VENTILATIONSETTINGS.put("FiO2 CPV", "%");
    VENTILATIONSETTINGS.put("RR CPV", "c/min");
    VENTILATIONSETTINGS.put("PI CPV", "cmH2O");
    VENTILATIONSETTINGS.put("Thigh CPV", "ms");
    VENTILATIONSETTINGS.put("Heigh", "mm");
    VENTILATIONSETTINGS.put("Gender", "0: Female 1: Male");
    VENTILATIONSETTINGS.put("Coeff ml/kg", "ml/Kg");
    VENTILATIONSETTINGS.put("O2 low pressure", "ON/OFF");
    VENTILATIONSETTINGS.put("Peak flow", "L/min");

    ALARMSSETTINGS.put("Ppeak", "cmH2O");
    ALARMSSETTINGS.put("VTiMin", "mL");
    ALARMSSETTINGS.put("VTiMax", "mL");
    ALARMSSETTINGS.put("VTeMin", "mL");
    ALARMSSETTINGS.put("VTeMax", "mL");
    ALARMSSETTINGS.put("MViMin", "L/min");
    ALARMSSETTINGS.put("MViMax", "L/min");
    ALARMSSETTINGS.put("MVeMin", "L/min");
    ALARMSSETTINGS.put("MVeMax", "L/min");
    ALARMSSETTINGS.put("RR min", "c/min");
    ALARMSSETTINGS.put("RR max", "c/min");
    ALARMSSETTINGS.put("Fio2Min", "%");
    ALARMSSETTINGS.put("Fio2Max", "%");
    ALARMSSETTINGS.put("Etco2Min", "mmHg");
    ALARMSSETTINGS.put("Etco2Max", "mmHg");
    ALARMSSETTINGS.put("Fico2Max", "mmHg");
    ALARMSSETTINGS.put("Pmin", "cmH2O");
    ALARMSSETTINGS.put("PplatMax", "cmH2O");
    ALARMSSETTINGS.put("FreqCTMin", "c/min");
    ALARMSSETTINGS.put("FreqCTMax", "c/min");
  }

  // private static final String[] STATUS = new String[] {
  //     "Ventilator State", "Patient Type",      "Ventilation Mode", "NIV",
  //     "100% O2",          "Expi. Flow Sensor", "CO2 Sensor",       "Expi
  //     Pause", "Inspi Pause",
  // };

  public static Device createDevice() {

    // Create FHIR Context
    // FhirContext fhirContext = FhirContext.forR4();

    // Create Device Resource
    Device device = new Device();

    // Set Device ID
    // device.setId("ventilator-device");

    // Set Device Identifier
    // Identifier identifier = new Identifier();
    // identifier.setSystem("http://example.org/devices");
    // identifier.setValue("123456");
    // device.addIdentifier(identifier);

    // Set Device Status
    // device.setStatus(Device.DeviceStatus.ACTIVE);

    // Set Device Type
    // CodeableConcept deviceType = new CodeableConcept();
    // Coding deviceTypeCoding = new Coding();
    // deviceTypeCoding.setSystem("http://snomed.info/sct");
    // deviceTypeCoding.setCode("30351005");
    // deviceTypeCoding.setDisplay("Ventilator");
    // deviceType.addCoding(deviceTypeCoding);
    // device.setType(deviceType);

    // Set Device Manufacturer
    // device.setManufacturer("ABC Corporation");

    // Set Device Model
    // device.setModel("VNT-2000");

    // Set Device Version
    // device.setVersion("2.0");

    // Set Patient Reference
    // Reference patientReference = new Reference("Patient/example-patient");
    // device.setPatient(patientReference);
    List<Quantity> quantities = new ArrayList<Quantity>();

    for (Map.Entry<String, String> entry : MEASURES.entrySet()) {
      double randomValue =
          Math.random() * (entry.getValue().equals("%") ? 100 : 20);
      DecimalFormat decimalFormat = new DecimalFormat("##.##");
      String formattedValue = decimalFormat.format(randomValue);

      quantities.add(new Quantity()
                         .setValue(Double.parseDouble(formattedValue))
                         .setUnit(entry.getValue())
                         .setCode(entry.getKey()));
    }
    device.addProperty()
        .setType(new CodeableConcept().addCoding(
            new Coding("http://hl7.org/fhir/CodeSystem/device-property-type",
                       "measures", null)))
        .setValueQuantity(quantities);

    List<Quantity> statusQuantities = new ArrayList<Quantity>();

    for (Map.Entry<String, Map<Integer, String>> entry : STATUS.entrySet()) {
      int randomUnit = (int)(Math.random() * entry.getValue().size());
      System.out.println("size" + entry.getValue().size());

      statusQuantities.add(new Quantity()
                               .setValue(randomUnit)
                               .setUnit(entry.getValue().get(randomUnit))
                               .setCode(entry.getKey()));
    }
    // Set Device Property Codes
    device.addProperty()
        .setType(new CodeableConcept().addCoding(
            new Coding("http://hl7.org/fhir/CodeSystem/device-property-type",
                       "status", null)))
        .setValueQuantity(statusQuantities);

    List<Quantity> ventilationSettingsQuantities = new ArrayList<Quantity>();
    for (Map.Entry<String, String> entry : VENTILATIONSETTINGS.entrySet()) {
      double randomValue =
          Math.random() * (entry.getValue().equals("%") ? 100 : 20);
      DecimalFormat decimalFormat = new DecimalFormat("##.##");
      String formattedValue = decimalFormat.format(randomValue);

      ventilationSettingsQuantities.add(
          new Quantity()
              .setValue(Double.parseDouble(formattedValue))
              .setUnit(entry.getValue())
              .setCode(entry.getKey()));
    }
    device.addProperty()
        .setType(new CodeableConcept().addCoding(
            new Coding("http://hl7.org/fhir/CodeSystem/device-property-type",
                       "ventilation settings", null)))
        .setValueQuantity(ventilationSettingsQuantities);

    List<Quantity> alarmsSettingsQuantities = new ArrayList<Quantity>();
    for (Map.Entry<String, String> entry : ALARMSSETTINGS.entrySet()) {
      double randomValue =
          Math.random() * (entry.getValue().equals("%") ? 100 : 20);
      DecimalFormat decimalFormat = new DecimalFormat("##.##");
      String formattedValue = decimalFormat.format(randomValue);

      alarmsSettingsQuantities.add(
          new Quantity()
              .setValue(Double.parseDouble(formattedValue))
              .setUnit(entry.getValue())
              .setCode(entry.getKey()));
    }
    device.addProperty()
        .setType(new CodeableConcept().addCoding(
            new Coding("http://hl7.org/fhir/CodeSystem/device-property-type",
                       "alarms settings", null)))
        .setValueQuantity(alarmsSettingsQuantities);

    // Add other property codes similarly...

    // Convert Device Resource to JSON
    // String json =
    //     fhirContext.newJsonParser().setPrettyPrint(true).encodeResourceToString(
    //         device);

    // // Print the generated JSON
    // System.out.println(json);

    return device;
  }
}
