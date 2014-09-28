package org.asciidoctor.ruby.extension;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.asciidoctor.ruby.ast.RubyDocument;

public class NextLineEmptyPreprocessor extends Preprocessor {

    public NextLineEmptyPreprocessor(Map<String, Object> config) {
        super(config);
    }

    @Override
    public PreprocessorReader process(RubyDocument rubyDocument, PreprocessorReader reader) {

        assertThat(reader.isNextLineEmpty(), is(false));

        reader.advance();

        assertThat(reader.isNextLineEmpty(), is(true));

        return reader;
    }

}
