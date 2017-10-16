package tk.friendar.api;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequest) throws IOException {
        Response UnAuth = Response.status(Response.Status.UNAUTHORIZED)
                .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"realm\"")
                .entity("Page requires login.").build();
        // Automatically allow certain requests.
        String method = containerRequest.getMethod();
        String path = containerRequest.getUriInfo().getPath(true);
        if (method.equals("GET") && path.equals("application.wadl") ||
                method.equals("POST") && path.equals("users")) {
            return;
        }

        /* Check auth */

        // Get the authentication passed in HTTP headers parameters
        String auth = containerRequest.getHeaderString("authorization");
        if (auth == null) {
            containerRequest.abortWith(UnAuth);
            return;
        }
        auth = auth.replaceFirst("[Bb]asic ", "");
        String[] userColonPass = new String(Base64.getDecoder().decode(auth.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8).split(":");

        try {
            Session session = SessionFactorySingleton.getInstance().openSession();
            try {
                UserDB user = (UserDB) session.createCriteria(UserDB.class).add(Restrictions.eq("username", userColonPass[0])).uniqueResult();
                if (user == null) {
                    // invalid username
                    containerRequest.abortWith(UnAuth);
                    return;
                }
                if (!user.validPassword(userColonPass[1])) {
                    // invalid password
                    containerRequest.abortWith(UnAuth);
                    return;
                }
            } finally {
                session.close();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            throw e;
        }
    }
}