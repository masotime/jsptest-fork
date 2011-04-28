package net.sf.jsptest.compiler.jsp20.mock;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Lasse Koskela
 */
public class MockHttpServletResponse implements HttpServletResponse {

    private ServletOutputStream servletOutputStream;
    private String description;
    private PrintWriter writer;

    public MockHttpServletResponse() {
        this(new ByteArrayOutputStream());
        description = "(in-memory)";
    }

    public MockHttpServletResponse(File output) throws FileNotFoundException {
        this(new FileOutputStream(output));
        description = "(output file: " + output.getAbsolutePath() + ")";
    }

    private MockHttpServletResponse(final OutputStream output) {
        this.servletOutputStream = new JspTestServletOutputStream(output);
        writer = new PrintWriter(servletOutputStream) {
        };
    }

    public String toString() {
        return super.toString() + " " + description;
    }

    public void addCookie(Cookie cookie) {
    }

    public void addDateHeader(String s, long l) {
    }

    public void addHeader(String s, String s1) {
    }

    public void addIntHeader(String s, int i) {
    }

    public boolean containsHeader(String s) {
        return false;
    }

    public String encodeRedirectURL(String s) {
        return null;
    }

    /** @deprecated */
    public String encodeRedirectUrl(String s) {
        return null;
    }

    public String encodeURL(String s) {
        return s;
    }

    /** @deprecated */
    public String encodeUrl(String s) {
        return s;
    }

    public void flushBuffer() throws IOException {
        getOutputStream().flush();
    }

    public int getBufferSize() {
        return 0;
    }

    public String getCharacterEncoding() {
        return null;
    }

    public Locale getLocale() {
        return null;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return servletOutputStream;
    }

    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    public boolean isCommitted() {
        return false;
    }

    public void reset() {
    }

    public void resetBuffer() {
    }

    public void sendError(int i) throws IOException {
        throw new RuntimeException("Not implemented");
    }

    public void sendError(int i, String s) throws IOException {
        throw new RuntimeException("Not implemented");
    }

    public void sendRedirect(String s) throws IOException {
        throw new RuntimeException("Not implemented");
    }

    public void setBufferSize(int i) {
    }

    public void setContentLength(int i) {
    }

    public void setContentType(String s) {
    }

    public void setDateHeader(String s, long l) {
    }

    public void setHeader(String s, String s1) {
    }

    public void setIntHeader(String s, int i) {
    }

    public void setLocale(Locale locale) {
    }

    /** @deprecated */
    public void setStatus(int i) {
    }

    public void setStatus(int i, String s) {
    }

    public String getContentType() {
        return null;
    }

    public void setCharacterEncoding(String reference) {
    }
}
