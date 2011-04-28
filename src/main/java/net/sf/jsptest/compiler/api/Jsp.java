package net.sf.jsptest.compiler.api;

import java.util.Map;

/**
 * @author Lasse Koskela
 */
public interface Jsp {

    JspExecution request(String httpMethod, Map requestAttributes, Map<String, String[]> requestParameters, Map sessionAttributes);
}
