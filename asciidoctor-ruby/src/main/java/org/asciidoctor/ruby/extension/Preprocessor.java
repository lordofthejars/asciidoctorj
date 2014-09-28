package org.asciidoctor.ruby.extension;

import java.util.HashMap;
import java.util.Map;

import org.asciidoctor.ast.Document;
import org.asciidoctor.ruby.ast.RubyDocument;

public abstract class Preprocessor extends Processor {

    public Preprocessor() {
        this(new HashMap<String, Object>());
    }
    
    public Preprocessor(Map<String, Object> config) {
        super(config);
    }

    public abstract PreprocessorReader process(RubyDocument rubyDocument, PreprocessorReader reader);
    
    public PreprocessorReader process(Document document, PreprocessorReader reader) {
    	return this.process(rubyDocument(document), reader);
    }
    
}
