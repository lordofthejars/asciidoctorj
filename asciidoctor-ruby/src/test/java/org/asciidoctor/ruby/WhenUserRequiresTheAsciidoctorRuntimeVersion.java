package org.asciidoctor.ruby;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.ruby.internal.RubyAsciidoctorJ;
import org.junit.Test;

public class WhenUserRequiresTheAsciidoctorRuntimeVersion {

    @Test
    public void current_version_should_be_retrieved() {
        
        Asciidoctor asciidoctor = new Asciidoctor(RubyAsciidoctorJ.Factory.create());
        String asciidoctorVersion = asciidoctor.asciidoctorVersion();
        
        assertThat(asciidoctorVersion, is(notNullValue()));
        
    }
    
}
