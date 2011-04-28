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
package net.sf.jsptest;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

/**
 * @author Lasse Koskela
 */
public abstract class AbstractHtmlTestCaseTestCase extends AbstractFakeJspCompilerTestCase {

    protected HtmlTestCase testcase;

    protected void setUp() throws Exception {
        super.setUp();
        testcase = new HtmlTestCase() {
        };        
        testcase.setUp();
        
        File f = new File("index.jsp");
		f.createNewFile();
		Writer w = new FileWriter(f);
		StringBuffer sb = new StringBuffer();
		appendOutput(sb);
		w.write(sb.toString());
		w.close();
		
		testcase.get("/index.jsp");
    }
}
