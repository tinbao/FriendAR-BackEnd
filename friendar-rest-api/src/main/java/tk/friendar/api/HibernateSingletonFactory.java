package tk.friendar.api;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSingletonFactory {

   private static SessionFactory instance = null;
   
   private HibernateSingletonFactory() {
      // Exists only to defeat instantiation.
   }
   
   public static ClassicSingleton getInstance() {
      if(instance == null) {
         instance = SessionFactory = new Configuration().configure().buildSessionFactory();
      }
      return instance;
   }
}