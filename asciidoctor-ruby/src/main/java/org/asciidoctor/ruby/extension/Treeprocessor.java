package org.asciidoctor.ruby.extension;

import java.util.HashMap;
import java.util.Map;

import org.asciidoctor.ast.Document;
import org.asciidoctor.ruby.ast.RubyDocument;

public abstract class Treeprocessor extends Processor {

    public Treeprocessor() {
        this(new HashMap<String, Object>());
    }
    
    public Treeprocessor(Map<String, Object> config) {
        super(config);
    }

    public abstract RubyDocument process(RubyDocument rubyDocument);
    
    public Document process(Document document) {
    	return process(rubyDocument(document));
    }
    
}
