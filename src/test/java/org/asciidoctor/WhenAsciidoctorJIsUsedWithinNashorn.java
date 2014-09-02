package org.asciidoctor;

import java.util.HashMap;

import org.asciidoctor.internal.AsciidoctorJ;
import org.asciidoctor.internal.NashornAsciidoctorJ;
import org.junit.Test;

public class WhenAsciidoctorJIsUsedWithinNashorn {

    @Test
    public void asciidoc_string_content_should_be_rendered() {
        AsciidoctorJ asciidoctorJ = NashornAsciidoctorJ.create();
        System.out.println(asciidoctorJ.convert("*Hello World*", new HashMap<>()));
    }
    
}
