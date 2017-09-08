package tk.friendar.api;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactorySingleton {

    private static SessionFactory instance = null;

    private SessionFactorySingleton() {
        // Exists only to defeat instantiation.
    }

    public static SessionFactory getInstance() {
        if (instance == null) {
            instance = new Configuration().configure().buildSessionFactory();
        }
        return instance;
    }
}