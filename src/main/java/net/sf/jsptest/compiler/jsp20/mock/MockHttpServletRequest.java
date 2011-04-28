/*
 * Copyright 2007 Lasse Koskela.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.jsptest.compiler.jsp20.mock;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Lasse Koskela
 */
public class MockHttpServletRequest implements HttpServletRequest {

    private Map headers = new HashMap();
    private Map parameters = new HashMap();
    private Map attributes = new HashMap();
    private MockHttpSession httpSession = null;
    private byte[] body;
    private String httpMethod;
    private String characterEncoding;

    public MockHttpServletRequest() {
        this((MockHttpSession) null);
    }

    public MockHttpServletRequest(MockHttpSession httpSession) {
        setSession(httpSession);
    }

    public MockHttpServletRequest(byte[] body) {
        this.body = body;
        characterEncoding = "UTF-8";
        this.headers.put("Content-type", "multipart/form-data; boundary=XXXBOUNDARYXXX");
    }

    public String getAuthType() {
        return null;
    }

    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    public long getDateHeader(String s) {
        try {
            // TODO: use the HTTP date format pattern
            return new SimpleDateFormat().parse((String) headers.get(s)).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public String getHeader(String reference) {
        return (String) headers.get(reference);
    }

    public Enumeration getHeaders(String s) {
        return new Vector().elements();
    }

    public Enumeration getHeaderNames() {
        return new Vector(headers.keySet()).elements();
    }

    public int getIntHeader(String s) {
        return Integer.parseInt(getHeader(s));
    }

    public void setMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getMethod() {
        return httpMethod;
    }

    public String getPathInfo() {
        return "/";
    }

    public String getPathTranslated() {
        return "/";
    }

    public String getContextPath() {
        return "/";
    }

    public String getQueryString() {
        return null;
    }

    public String getRemoteUser() {
        return null;
    }

    public boolean isUserInRole(String s) {
        return false;
    }

    public Principal getUserPrincipal() {
        return null;
    }

    public String getRequestedSessionId() {
        return "JSPTESTSESSIONID";
    }

    public String getRequestURI() {
        return "/";
    }

    public StringBuffer getRequestURL() {
        return new StringBuffer("/");
    }

    public String getServletPath() {
        return "/";
    }

    public HttpSession getSession(boolean flag) {
        if (flag && httpSession == null) {
            httpSession = new MockHttpSession();
        }
        return httpSession;
    }

    public HttpSession getSession() {
        return httpSession;
    }

    public boolean isRequestedSessionIdValid() {
        return true;
    }

    public boolean isRequestedSessionIdFromCookie() {
        return true;
    }

    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public Enumeration getAttributeNames() {
        return new Vector(attributes.keySet()).elements();
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public void setCharacterEncoding(String env) {
        characterEncoding = env;
    }

    public int getContentLength() {
        return body.length;
    }

    public String getContentType() {
        return "multipart/form-data; boundary=xxx";
    }

    public ServletInputStream getInputStream() throws IOException {
        return new MockServletInputStream(body);
    }

    public String getParameter(String name) {
        String values[] = (String[]) parameters.get(name);
        if (values != null) {
            return (values[0]);
        } else {
            return (null);
        }
    }

    public Map getParameterMap() {
        return parameters;
    }

    public String getProtocol() {
        return "http";
    }

    public String getScheme() {
        return "http://";
    }

    public String getServerName() {
        return "servername";
    }

    public int getServerPort() {
        return 8080;
    }

    public BufferedReader getReader() throws IOException {
        return null;
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        return null;
    }

    public void setAttributes(Map attributes) {
        this.attributes.putAll(attributes);
    }

    public void setAttribute(String name, Object o) {
        attributes.put(name, o);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public Locale getLocale() {
        return Locale.getDefault();
    }

    public Enumeration getLocales() {
        return new Vector(Arrays.asList(Locale.getAvailableLocales())).elements();
    }

    public boolean isSecure() {
        return false;
    }

    public RequestDispatcher getRequestDispatcher(String s) {
        return new RequestDispatcher() {

            public void forward(ServletRequest request, ServletResponse response)
                    throws ServletException, IOException {
            }

            public void include(ServletRequest request, ServletResponse response)
                    throws ServletException, IOException {
            }
        };
    }

    public String getRealPath(String s) {
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        File jsptestTmpDir = new File(tmpDir, "jsptest");
        return new File(jsptestTmpDir, s).getAbsolutePath();
    }

    public int getRemotePort() {
        return 11111;
    }

    public String getLocalName() {
        return null;
    }

    public String getLocalAddr() {
        return null;
    }

    public int getLocalPort() {
        return 22222;
    }

    public void addParameter(String name, String values[]) {
        parameters.put(name, values);
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName()).append("{");
        Enumeration enumeration = getParameterNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String[] values = getParameterValues(key);
            buffer.append("parameter:");
            buffer.append(key);
            buffer.append("=[");
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                buffer.append(value);
                if (i < values.length - 1) {
                    buffer.append(",");
                }
            }
            buffer.append("]"+ System.getProperty("line.separator"));
        }
        buffer.append("}");
        return buffer.toString();
    }

    public Enumeration getParameterNames() {
        return new Vector(parameters.keySet()).elements();
    }

    public String[] getParameterValues(String name) {
        return (String[]) parameters.get(name);
    }

    public void setSession(MockHttpSession session) {
        this.httpSession = session;
    }
}
