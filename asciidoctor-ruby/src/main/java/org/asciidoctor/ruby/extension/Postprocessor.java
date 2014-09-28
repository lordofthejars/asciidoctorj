package org.asciidoctor.ruby.extension;

import java.util.HashMap;
import java.util.Map;

import org.asciidoctor.ast.Document;
import org.asciidoctor.ruby.ast.RubyDocument;

public abstract class Postprocessor extends Processor {

    public Postprocessor() {
        this(new HashMap<String, Object>());
    }
    
    public Postprocessor(Map<String, Object> config) {
        super(config);
    }

    public abstract String process(RubyDocument rubyDocument, String output);
    
    public String process(Document document, String output) {
    	return process(rubyDocument(document), output);
    }
    
}
