package org.asciidoctor;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.HashMap;

import org.asciidoctor.internal.AsciidoctorJ;
import org.asciidoctor.internal.NashornAsciidoctorJ;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

public class WhenAsciidoctorJIsUsedWithinNashorn {

    @Test
    public void asciidoc_string_content_should_be_rendered() {
        
        AsciidoctorJ asciidoctorJ = NashornAsciidoctorJ.create();
        String content = asciidoctorJ.convert("*Hello World*", new HashMap<>());
        
        Document doc = Jsoup.parse(content, "UTF-8");
        Element paragraph = doc.getElementsByTag("strong").first();
        
        assertThat(paragraph.text(), is("Hello World"));
    }
    
}
