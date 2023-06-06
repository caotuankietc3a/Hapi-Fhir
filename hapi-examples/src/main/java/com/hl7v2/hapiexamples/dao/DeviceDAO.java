package com.hl7v2.hapiexamples.dao;

import com.hl7v2.hapiexamples.model.Device;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DeviceDAO {
  private SessionFactory sessionFactory;

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public void save(Device device) {
    try (Session session = this.sessionFactory.openSession()) {
      Transaction tx = session.beginTransaction();
      session.persist(device);
      tx.commit();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}
