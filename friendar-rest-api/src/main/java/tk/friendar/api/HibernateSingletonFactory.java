package tk.friendar.api;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSingletonFactory {

   private static Session instance = null;
   
   private HibernateSingletonFactory() {
      // Exists only to defeat instantiation.
   }
   
   public static Session getInstance() {
      if(instance == null) {
         SessionFactory factory = new Configuration().configure().buildSessionFactory();
         instance = factory.openSession();
      }
      return instance;
   }
}