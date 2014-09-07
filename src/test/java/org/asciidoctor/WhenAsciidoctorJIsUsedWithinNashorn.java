package org.asciidoctor;

import static org.asciidoctor.OptionsBuilder.options;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.HashMap;

import org.asciidoctor.internal.AsciidoctorJ;
import org.asciidoctor.internal.NashornAsciidoctorJ;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class WhenAsciidoctorJIsUsedWithinNashorn {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    AsciidoctorJ asciidoctorJ = NashornAsciidoctorJ.create();
    
    @Test
    public void asciidoc_string_content_should_be_rendered() {
        
        String content = asciidoctorJ.convert("*Hello World*", new HashMap<>());
        
        Document doc = Jsoup.parse(content, "UTF-8");
        Element paragraph = doc.getElementsByTag("strong").first();
        
        assertThat(paragraph.text(), is("Hello World"));
    }
    
    @Test
    public void asciidoc_string_content_should_be_rendered_with_given_options() {
        
        OptionsBuilder optionsBuilder = OptionsBuilder.options().headerFooter(true);
        
        String content = asciidoctorJ.convert("*Hello World*", optionsBuilder.asMap());

        Document doc = Jsoup.parse(content, "UTF-8");
        Element root = doc.getElementsByTag("html").first();
        
        assertThat(root, is(notNullValue()));
    }
    
    @Test
    public void asciidoc_string_should_be_rendered_to_file() {
        
        Options options = options().inPlace(false)
                .toFile(new File(testFolder.getRoot(), "output.html"))
                .safe(SafeMode.UNSAFE).get();
        
        asciidoctorJ.convert("This is Asciidoctor", options.map());
        
    }
    
    @Test
    public void asciiDoc_file_should_be_rendered() {
        Options options = options().inPlace(true).get();
        String renderContent = asciidoctorJ.convert_file(new File("target/test-classes/rendersample.asciidoc"), options.map());

        File expectedFile = new File("target/test-classes/rendersample.html");

        assertThat(expectedFile.exists(), is(true));
        assertThat(renderContent, is(nullValue()));

        expectedFile.delete();
    }
    
}
