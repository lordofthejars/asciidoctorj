package org.asciidoctor.extension;

import java.io.File;
import java.util.HashMap;

import org.asciidoctor.Asciidoctor;
import org.junit.Test;

public class WhenReaderIsManipulatedInExtension {

    private Asciidoctor asciidoctor = Asciidoctor.Factory.create();

    @Test
    public void currentLineNumberShouldBeReturned() {

        JavaExtensionRegistry javaExtensionRegistry = asciidoctor.createExtensionRegistry(JavaExtensionRegistry.class);

        javaExtensionRegistry.preprocessor(NumberLinesPreprocessor.class);

        asciidoctor.convertFile(new File("target/test-classes/rendersample.asciidoc"), new HashMap<String, Object>());

    }

    @Test
    public void hasMoreLinesShouldBeReturned() {

        JavaExtensionRegistry javaExtensionRegistry = asciidoctor.createExtensionRegistry(JavaExtensionRegistry.class);

        javaExtensionRegistry.preprocessor(HasMoreLinesPreprocessor.class);

        asciidoctor.convertFile(new File("target/test-classes/rendersample.asciidoc"), new HashMap<String, Object>());

    }

    @Test
    public void isNextLineEmptyShouldBeReturned() {

        JavaExtensionRegistry javaExtensionRegistry = asciidoctor.createExtensionRegistry(JavaExtensionRegistry.class);

        javaExtensionRegistry.preprocessor(NextLineEmptyPreprocessor.class);

        asciidoctor.convertFile(new File("target/test-classes/rendersample.asciidoc"), new HashMap<String, Object>());

    }

}
