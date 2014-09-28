package org.asciidoctor.ruby.extension;

import org.asciidoctor.extension.spi.ExtensionRegistry;
import org.asciidoctor.internal.AsciidoctorJ;

public class ArrowsAndBoxesExtension implements ExtensionRegistry {

    
    @Override
    public void register(AsciidoctorJ asciidoctor) {
        
        JavaExtensionRegistry javaExtensionRegistry = asciidoctor.createExtensionRegistry(JavaExtensionRegistry.class);
        javaExtensionRegistry.postprocessor(ArrowsAndBoxesIncludesPostProcessor.class);
        javaExtensionRegistry.block("arrowsAndBoxes", ArrowsAndBoxesBlock.class);
        
    }

}
