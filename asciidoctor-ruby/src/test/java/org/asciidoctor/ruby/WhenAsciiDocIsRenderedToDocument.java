package org.asciidoctor.ruby;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.ast.AbstractBlock;
import org.asciidoctor.ast.Document;
import org.asciidoctor.ast.Section;
import org.asciidoctor.ruby.internal.RubyAsciidoctorJ;
import org.junit.Test;

public class WhenAsciiDocIsRenderedToDocument {

    private static final String DOCUMENT = "= RubyDocument Title\n" + 
            "\n" + 
            "preamble\n" + 
            "\n" + 
            "== Section A\n" + 
            "\n" + 
            "paragraph\n" + 
            "\n" + 
            "--\n" + 
            "Exhibit A::\n" + 
            "+\n" + 
            "[#tiger.animal]\n" + 
            "image::tiger.png[Tiger]\n" + 
            "--\n" + 
            "\n" + 
            "image::cat.png[Cat]\n" + 
            "\n" + 
            "== Section B\n" + 
            "\n" + 
            "paragraph";
    
    private Asciidoctor asciidoctor = new Asciidoctor(RubyAsciidoctorJ.Factory.create());

    @Test
    public void should_return_section_blocks() {
        Document rubyDocument = asciidoctor.load(DOCUMENT, new HashMap<String, Object>());
        Section section = (Section) rubyDocument.blocks().get(1);
        assertThat(section.index(), is(0));
        assertThat(section.sectname(), is("sect1"));
        assertThat(section.special(), is(false));
    }
    
    @Test
    public void should_return_blocks_from_a_document() {
        
        Document rubyDocument = asciidoctor.load(DOCUMENT, new HashMap<String, Object>());
        assertThat(rubyDocument.doctitle(new HashMap<>()), is("RubyDocument Title"));
        
    }
    
    @Test
    public void should_return_a_document_object_from_string() {
        
        Document rubyDocument = asciidoctor.load(DOCUMENT, new HashMap<String, Object>());
        assertThat(rubyDocument.doctitle(new HashMap<>()), is("RubyDocument Title"));
    }
    
    @Test
    public void should_find_elements_from_document() {
        
        Document rubyDocument = asciidoctor.load(DOCUMENT, new HashMap<String, Object>());
        Map<Object, Object> selector = new HashMap<Object, Object>();
        selector.put("context", ":image");
        List<AbstractBlock> findBy = rubyDocument.findBy(selector);
        assertThat(findBy, hasSize(2));
        
        assertThat((String)findBy.get(0).attributes().get("target"), is("tiger.png"));
        
    }
    
}
