package org.asciidoctor.nashorn.internal;

import static org.asciidoctor.OptionsBuilder.options;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.HashMap;

import org.asciidoctor.Options;
import org.asciidoctor.OptionsBuilder;
import org.asciidoctor.SafeMode;
import org.asciidoctor.internal.AsciidoctorJ;
import org.asciidoctor.nashorn.internal.NashornAsciidoctorJ;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Ignore;
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
    @Ignore
    public void asciidoc_string_should_be_rendered_to_file() {
        
        Options options = options().inPlace(false)
                .toFile(new File(testFolder.getRoot(), "output.html"))
                .safe(SafeMode.UNSAFE).get();
        
        asciidoctorJ.convert("This is Asciidoctor", options.map());
        
    }
    
    @Test
    @Ignore
    public void asciiDoc_file_should_be_rendered() {
        Options options = options().inPlace(true).get();
        String renderContent = asciidoctorJ.convertFile(new File("target/test-classes/rendersample.asciidoc"), options.map());

        File expectedFile = new File("target/test-classes/rendersample.html");

        assertThat(expectedFile.exists(), is(true));
        assertThat(renderContent, is(nullValue()));

        expectedFile.delete();
    }
    
    @Test
    @Ignore
    public void asciiDoc_string_should_be_rendered_into_document() {
        org.asciidoctor.ast.Document content = asciidoctorJ.load("*Hello World*", new HashMap<>());
        content.title();
    }
    
    @Test
    @Ignore
    public void asciiDoc_file_should_be_rendered_into_document() {
        org.asciidoctor.ast.Document content = asciidoctorJ.loadFile(new File("target/test-classes/rendersample.asciidoc"), new HashMap<>());
        content.title();
    }
    
    @Test
    @Ignore
    public void should_return_the_runtime_version_of_asciidoctor() {
        System.out.println(asciidoctorJ.asciidoctorVersion());
    }
    
}
