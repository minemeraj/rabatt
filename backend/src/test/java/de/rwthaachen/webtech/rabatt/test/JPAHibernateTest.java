package de.rwthaachen.webtech.rabatt.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import de.rwthaachen.webtech.rabatt.model.Discount;
import de.rwthaachen.webtech.rabatt.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Connection;
import java.sql.SQLException;

public class JPAHibernateTest {
  protected static Configuration configuration;
  protected static SessionFactory sessionFactory;
  protected static Session session;
  protected static EntityManagerFactory emf;
  protected static EntityManager em;

  @BeforeClass
  public static void init() throws SQLException {
    configuration = new Configuration();

    configuration.addAnnotatedClass(User.class).addAnnotatedClass(Discount.class);
    configuration.setProperty("javax.persistence.jdbc.url", "jdbc:h2:mem:test");
    configuration.setProperty("javax.persistence.jdbc.driver", "org.h2.Driver");

    configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
    configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
    configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:test");
    configuration.setProperty("hibernate.hbm2ddl.auto", "create");

    sessionFactory = configuration.buildSessionFactory();
    session = sessionFactory.openSession();
    emf = session.getEntityManagerFactory();
    em = emf.createEntityManager();
  }

  @Before
  public void initializeDatabase() {
    Session ss = em.unwrap(Session.class);
    ss.doWork(new Work() {
      @Override
      public void execute(Connection connection) throws SQLException {
        init();
      }
    });
  }

  @AfterClass
  public static void tearDown() {
    em.clear();
    em.close();
    emf.close();
  }
}
