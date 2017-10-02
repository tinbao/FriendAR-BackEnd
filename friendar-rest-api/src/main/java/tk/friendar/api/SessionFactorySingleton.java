package tk.friendar.api;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactorySingleton {

    private static SessionFactory instance = null;

    private SessionFactorySingleton() {
        // Exists only to defeat instantiation.
    }

    public static SessionFactory getInstance() {
        return new Configuration().configure().buildSessionFactory();
        // not a singleton as this gets closed, after a request.
    }
}