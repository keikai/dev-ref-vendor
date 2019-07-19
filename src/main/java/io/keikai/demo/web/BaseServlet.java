package io.keikai.demo.web;

import io.keikai.demo.Configuration;
import org.slf4j.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.*;

import static io.keikai.demo.Configuration.KEIKAI_PROPERTIES;

/**
 * accept "server" parameter e.g. http://localhost:8080?server=10.1.1.1:8888
 */
abstract public class BaseServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(BaseServlet.class);
    protected int DEFAULT_KEIKAI_SERVER_PORT = 8888;
    protected String keikaiServerAddress;
    protected File defaultFileFolder;
    static final protected String ATTR_TITLE = "title";
    static final protected String ATTR_DESCRIPTION = "description";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        defaultFileFolder = new File(getServletContext().getRealPath(Configuration.getDefaultFileFolder()));
//        Configuration.enableSocketIOLog(config.getInitParameter("logLevel"));
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        determineServerAddress(req);
        logger.info("keikai server: " + keikaiServerAddress);
        req.setAttribute("keikaiServer", keikaiServerAddress);

        initHeader(req);
    }

    /**
     * determine Keikai server address by properties file, query string, and default address.
     * @param request
     */
    protected void determineServerAddress(ServletRequest request) {
        if (Configuration.urlForJava != null){
            keikaiServerAddress = Configuration.urlForJava;
            logger.info("determine keikai server via " + KEIKAI_PROPERTIES);
            return;
        }

        String specifiedAddress = request.getParameter("server");
        if (specifiedAddress != null) {
            if (specifiedAddress.startsWith("http")) {
                keikaiServerAddress = specifiedAddress;
            } else {
                keikaiServerAddress = "http://" + specifiedAddress;
            }
            logger.info("determine keikai server via an URL parameter");
            return;
        }

        keikaiServerAddress = decideDefaultKeikaiServerAddress(request);
    }



    /**
     * determine default keikai server with below rules:
     * 1. a browser requests with "127.0.0.1", localhost debug mode, connect to keikai with 8888
     * 2. a browser requests with a URL other "127.0.0.1", demo mode, connect to keikai with 80
     *
     * @param request
     * @return
     */
    protected String decideDefaultKeikaiServerAddress(ServletRequest request) {
        try {
            if (request.getServerName().equals("127.0.0.1")) {
                return new URL(request.getScheme(),
                        request.getServerName(), DEFAULT_KEIKAI_SERVER_PORT, "/").toString();

            } else {
                return new URL(request.getScheme(),
                        "127.0.0.1", DEFAULT_KEIKAI_SERVER_PORT, "/").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("no server specified, connect to default keikai server");
        return Configuration.DEFAULT_KEIKAI_SERVER;
    }

    /**
     * set request attribute "title" and "description" for each demo's header.
     *
     * @param request
     */
    abstract protected void initHeader(HttpServletRequest request);
}
