package org.asciidoctor;

import static org.asciidoctor.OptionsBuilder.options;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

public class WhenANoneStandardBackendIsSet {

    private Asciidoctor asciidoctor = Asciidoctor.Factory.create();
    
    @Test
    public void epub3_should_be_rendered_for_epub3_backend() {
        
        asciidoctor.convertFile(new File("target/test-classes/epub-index.adoc"),
                options().safe(SafeMode.SAFE).backend("epub3").get());
        
        assertThat(new File("target/test-classes/epub-index.epub").exists(), is(true));
        
    }
    
}
