package net.sf.jsptest.compiler.jsp20;

import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspFactory;
import net.sf.jsptest.compiler.api.Jsp;
import net.sf.jsptest.compiler.api.JspExecution;
import net.sf.jsptest.compiler.jsp20.mock.MockHttpServletRequest;
import net.sf.jsptest.compiler.jsp20.mock.MockHttpServletResponse;
import net.sf.jsptest.compiler.jsp20.mock.MockHttpSession;
import net.sf.jsptest.compiler.jsp20.mock.MockJspFactory;
import net.sf.jsptest.compiler.jsp20.mock.MockJspWriter;
import net.sf.jsptest.compiler.jsp20.mock.MockPageContext;
import net.sf.jsptest.compiler.jsp20.mock.MockServletConfig;
import net.sf.jsptest.compiler.jsp20.mock.MockServletContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Lasse Koskela
 */
public class JspImpl implements Jsp {

    private static final Log log = LogFactory.getLog(JspImpl.class);
    private final Class servletClass;
    private MockPageContext pageContext;

    public JspImpl(Class servletClass) {
        this.servletClass = servletClass;
    }

    public JspExecution request(String httpMethod, Map requestAttributes, Map<String, String[]> requestParameters, Map sessionAttributes) {
        ServletContext servletContext = new MockServletContext();
        ServletConfig servletConfig = new MockServletConfig(servletContext);
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttributes(sessionAttributes);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(httpSession);
        request.setMethod(httpMethod);
        request.setAttributes(requestAttributes);
        
        // [20110428] Ben: Code to support request parameters
        for (Map.Entry<String, String[]> entry : requestParameters.entrySet()) {
        	request.addParameter(entry.getKey(), entry.getValue());
        }
        
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockJspWriter jspWriter = configureJspFactory(servletContext, request, httpSession);
        initializeAndInvokeJsp(servletClass, servletConfig, request, response);
        return createExecutionResult(jspWriter.getContents());
    }

    protected MockJspWriter configureJspFactory(ServletContext httpContext,
            HttpServletRequest httpRequest, HttpSession httpSession) {
        pageContext = new MockPageContext();
        pageContext.setRequest(httpRequest);
        pageContext.setServletContext(httpContext);
        pageContext.setSession(httpSession);
        MockJspWriter jspWriter = new MockJspWriter();
        pageContext.setJspWriter(jspWriter);
        JspFactory.setDefaultFactory(new MockJspFactory(pageContext));
        return jspWriter;
    }

    protected void initializeAndInvokeJsp(Class jspClass, ServletConfig servletConfig,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            log.debug("Instantiating Servlet: " + jspClass.getName());
            HttpServlet servlet = (HttpServlet) jspClass.newInstance();
            log.debug("Initializing Servlet: " + jspClass.getName());
            servlet.init(servletConfig);
            log.debug("Invoking Servlet: " + jspClass.getName());
            servlet.service(request, response);
            if (pageContext.getException() != null) {
                log.debug("An exception was stored into PageContext. Rethrowing it...");
                throw new RuntimeException(pageContext.getException());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected JspExecution createExecutionResult(String output) {
        return new JspExecutionImpl(output);
    }
}
