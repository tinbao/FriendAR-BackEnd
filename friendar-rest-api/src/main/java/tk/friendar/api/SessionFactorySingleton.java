package tk.friendar.api;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactorySingleton {
    static SessionFactory f = null;
    public static SessionFactory getInstance() {
        if(f == null)
            f = new Configuration().configure().buildSessionFactory();
        return f; // not a singleton as this gets closed, after a request.
    }
}