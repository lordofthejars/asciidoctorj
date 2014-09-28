package org.asciidoctor.ruby;

import static org.asciidoctor.OptionsBuilder.options;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.SafeMode;
import org.asciidoctor.ruby.internal.RubyAsciidoctorJ;
import org.junit.Test;

public class WhenANoneStandardBackendIsSet {

    private Asciidoctor asciidoctor = new Asciidoctor(RubyAsciidoctorJ.Factory.create());
    
    @Test
    public void epub3_should_be_rendered_for_epub3_backend() {
        
        asciidoctor.convertFile(new File("target/test-classes/epub-index.adoc"),
                options().safe(SafeMode.SAFE).backend("epub3").get());
        
        assertThat(new File("target/test-classes/epub-index.epub").exists(), is(true));
        
    }
    
}
