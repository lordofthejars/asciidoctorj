package org.asciidoctor.extension;

import java.util.Map;

import org.asciidoctor.ast.RubyDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class CustomFooterPostProcessor extends Postprocessor {

    public CustomFooterPostProcessor(Map<String, Object> config) {
        super(config);
    }

    @Override
    public String process(RubyDocument rubyDocument, String output) {
        
        String copyright  = "Copyright Acme, Inc.";
        
        if(rubyDocument.basebackend("html")) {
            org.jsoup.nodes.Document doc = Jsoup.parse(output, "UTF-8");

            Element contentElement = doc.getElementById("footer-text");
            contentElement.append(copyright);
            
            output = doc.html();
            
        }

        
        return output;
    }

}
