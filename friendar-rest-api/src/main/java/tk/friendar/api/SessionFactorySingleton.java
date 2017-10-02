package tk.friendar.api;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactorySingleton {
    public static SessionFactory getInstance() {
        return new Configuration().configure().buildSessionFactory(); // not a singleton as this gets closed, after a request.
    }
}