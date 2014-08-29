package org.asciidoctor.internal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.asciidoctor.Asciidoctor;
import org.jruby.RubyString;
import org.jruby.runtime.builtin.IRubyObject;
import org.junit.Test;

public class WhenEnvironmentVariablesAreSet {

    @Test
    public void they_should_be_available_inside_ruby_engine() {

        Asciidoctor asciidoctor = Asciidoctor.Factory.create("My_gem_path");
        IRubyObject evalScriptlet = ((RubyAsciidoctorJ)asciidoctor.getAsciidoctorJ()).rubyRuntime.evalScriptlet("ENV['GEM_PATH']");

        RubyString gemPathValue = (RubyString) evalScriptlet;
        assertThat(gemPathValue.asJavaString(), is("My_gem_path"));

    }

}
