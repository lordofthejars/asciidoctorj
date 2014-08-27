package org.asciidoctor.extension;

import java.util.Map;

import org.asciidoctor.ast.RubyDocument;

public class ChangeAttributeValuePreprocessor extends Preprocessor {

	public ChangeAttributeValuePreprocessor(Map<String, Object> config) {
		super(config);
	}

	@Override
	public PreprocessorReader process(RubyDocument rubyDocument,
			PreprocessorReader reader) {

		rubyDocument.getAttributes().put("content", "Alex");
		
		return reader;
	}

}