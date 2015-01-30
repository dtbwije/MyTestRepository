/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onsp487.corstester;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 *
 * @author tharanga
 */
public class Onsp487CORSTester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Server testServer = new Server(8085);
            WebAppContext webapp = new WebAppContext();
            webapp.addFilter(org.eclipse.jetty.servlets.CrossOriginFilter.class, "/*", null);
            webapp.setContextPath("/");
            webapp.setWar("src/web-app");
            testServer.setHandler(webapp);

            testServer.start();

            Server server = new Server(8089);
            ServletContextHandler handler = new ServletContextHandler();

            FilterHolder filter = new FilterHolder();
            filter.setInitParameter("allowedOrigins", "http://localhost:8085,http://localhost:8089");
            filter.setInitParameter("allowedMethods", "POST,GET,OPTIONS,PUT,DELETE,HEAD");
            filter.setInitParameter("allowedHeaders", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, Authentication’ ");
            filter.setInitParameter("preflightMaxAge", "728000");
            filter.setInitParameter("allowCredentials", "true");
            CrossOriginFilter corsFilter = new CrossOriginFilter();
            filter.setFilter(corsFilter);

            handler.addFilter(filter, "/2", null);

            server.setHandler(handler);
            ServletHolder holder_1 = new ServletHolder(new SimpleCORSServlet());
            ServletHolder holder_2 = new ServletHolder(new CORSPrefligtedServlet());
            handler.addServlet(holder_1, "/1");
            handler.addServlet(holder_2, "/2");

            server.start();

        } catch (Exception ex) {
                    Logger.getLogger(Onsp487CORSTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static FilterMapping createFilterMapping(String string, FilterHolder filter) {
        FilterMapping mapping = new FilterMapping();

        return mapping;
    }

}
