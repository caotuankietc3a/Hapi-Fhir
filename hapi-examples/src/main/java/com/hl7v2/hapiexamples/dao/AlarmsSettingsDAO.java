package com.hl7v2.hapiexamples.dao;

import com.hl7v2.hapiexamples.model.AlarmsSettings;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class AlarmsSettingsDAO {
  private SessionFactory sessionFactory;

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public void save(AlarmsSettings alarmsSettings) {
    try (Session session = this.sessionFactory.openSession()) {
      Transaction tx = session.beginTransaction();
      session.persist(alarmsSettings);
      tx.commit();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}
