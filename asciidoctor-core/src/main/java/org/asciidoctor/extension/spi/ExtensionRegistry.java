package org.asciidoctor.extension.spi;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.internal.AsciidoctorJ;

public interface ExtensionRegistry {

    void register(AsciidoctorJ asciidoctor);
    
}
