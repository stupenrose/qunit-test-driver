package com.cj.qunit.mojo.http;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.httpobjects.HttpObject;
import org.httpobjects.Response;

class AutoGeneratedQunitHtmlWrappers extends HttpObject {
    private static final String TEMPLATE = readClasspathResource("/qunit.template.html");
    
    public AutoGeneratedQunitHtmlWrappers() {
        super("/{resource*}");
    }
    
    public Response get(org.httpobjects.Request req) {
        final String path = req.pathVars().valueFor("resource");
        
        if(!path.endsWith(".qunit.js.Qunit.html")) return null;
        
        final String impliedJavascriptFile = "/" + path.replaceAll(Pattern.quote(".Qunit.html"), "");
        final String generatedHtmlFileContent = TEMPLATE.replaceAll("YOUR_JAVASCRIPT_TEST_FILE_GOES_HERE", Matcher.quoteReplacement(impliedJavascriptFile));
        
        return OK(Html(generatedHtmlFileContent));
    }
    
    private static String readClasspathResource(String name){
        try {
            return IOUtils.toString(AutoGeneratedQunitHtmlWrappers.class.getResourceAsStream(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}