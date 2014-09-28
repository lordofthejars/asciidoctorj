package org.asciidoctor.ruby;

import static org.asciidoctor.OptionsBuilder.options;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.ruby.internal.RubyAsciidoctorJ;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

public class WhenCustomTemplatesAreUsed {

    private Asciidoctor asciidoctor = new Asciidoctor(RubyAsciidoctorJ.Factory.create());
    
    @Test
    public void document_should_be_rendered_using_given_template_dir() {
        
        Options options = options().templateDir(new File("target/test-classes/src/custom-backends/haml/html5-tweaks")).toFile(false).get();
        String renderContent = asciidoctor.convertFile(new File("target/test-classes/rendersample.asciidoc"), options);
        
        Document doc = Jsoup.parse(renderContent, "UTF-8");
        Element paragraph = doc.select("div.content").first();
        assertThat(paragraph, notNullValue());
    }
    
}
