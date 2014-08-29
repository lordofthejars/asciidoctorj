package org.asciidoctor.internal;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.asciidoctor.Attributes;
import org.asciidoctor.Options;
import org.asciidoctor.ast.Document;
import org.asciidoctor.ast.RubyDocument;
import org.asciidoctor.extension.ExtensionRegistry;
import org.asciidoctor.extension.internal.ExtensionRegistryExecutor;
import org.jruby.CompatVersion;
import org.jruby.Ruby;
import org.jruby.RubyHash;
import org.jruby.RubyInstanceConfig;
import org.jruby.RubyInstanceConfig.CompileMode;
import org.jruby.embed.ScriptingContainer;
import org.jruby.javasupport.JavaEmbedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RubyAsciidoctorJ implements AsciidoctorJ {

    private static final String GEM_PATH = "GEM_PATH";
    private static final Logger log = LoggerFactory.getLogger(RubyAsciidoctorJ.class.getName());
    
    private AsciidoctorModule asciidoctorModule;
    protected RubyGemsPreloader rubyGemsPreloader;
    protected Ruby rubyRuntime;
    
    public static AsciidoctorJ create() {
        Map<String, Object> env = new HashMap<String, Object>();
        // ideally, we want to clear GEM_PATH by default, but for backwards compatibility we play nice
        //env.put(GEM_PATH, null);
        AsciidoctorJ asciidoctor = createJRubyAsciidoctorInstance(env);
        registerExtensions(asciidoctor);

        return asciidoctor;
    }

    public static AsciidoctorJ create(String gemPath) {
        Map<String, Object> env = new HashMap<String, Object>();
        // a null value will clear the GEM_PATH and GEM_HOME
        env.put(GEM_PATH, gemPath);

        AsciidoctorJ asciidoctor = createJRubyAsciidoctorInstance(env);
        registerExtensions(asciidoctor);

        return asciidoctor;
    }

    public static AsciidoctorJ create(List<String> loadPaths) {
        AsciidoctorJ asciidoctor = createJRubyAsciidoctorInstance(loadPaths);
        registerExtensions(asciidoctor);

        return asciidoctor;
    }

    public static AsciidoctorJ create(ClassLoader classloader) {
        AsciidoctorJ asciidoctor = createJRubyAsciidoctorInstance(classloader);
        registerExtensions(asciidoctor);

        return asciidoctor;
    }
    
    private static void registerExtensions(AsciidoctorJ asciidoctor) {
        new ExtensionRegistryExecutor(asciidoctor).registerAllExtensions();
    }

    private static AsciidoctorJ createJRubyAsciidoctorInstance(List<String> loadPaths) {

        RubyInstanceConfig config = createOptimizedConfiguration();

        Ruby rubyRuntime = JavaEmbedUtils.initialize(loadPaths, config);
        JRubyRuntimeContext.set(rubyRuntime);

        JRubyAsciidoctorModuleFactory jRubyAsciidoctorModuleFactory = new JRubyAsciidoctorModuleFactory(rubyRuntime);

        AsciidoctorModule asciidoctorModule = jRubyAsciidoctorModuleFactory.createAsciidoctorModule();
        RubyAsciidoctorJ jRubyAsciidoctor = new RubyAsciidoctorJ(asciidoctorModule, rubyRuntime);

        return jRubyAsciidoctor;
    }

    private static AsciidoctorJ createJRubyAsciidoctorInstance(Map<String, Object> environmentVars) {

        RubyInstanceConfig config = createOptimizedConfiguration();
        injectEnvironmentVariables(config, environmentVars);

        Ruby rubyRuntime = JavaEmbedUtils.initialize(Collections.EMPTY_LIST, config);

        JRubyRuntimeContext.set(rubyRuntime);

        JRubyAsciidoctorModuleFactory jRubyAsciidoctorModuleFactory = new JRubyAsciidoctorModuleFactory(rubyRuntime);

        AsciidoctorModule asciidoctorModule = jRubyAsciidoctorModuleFactory.createAsciidoctorModule();

        RubyAsciidoctorJ jRubyAsciidoctor = new RubyAsciidoctorJ(asciidoctorModule, rubyRuntime);
        return jRubyAsciidoctor;
    }

    private static AsciidoctorJ createJRubyAsciidoctorInstance(ClassLoader classloader) {

        ScriptingContainer container = new ScriptingContainer();
        container.setClassLoader(classloader);
        Ruby rubyRuntime = container.getProvider().getRuntime();

        JRubyRuntimeContext.set(rubyRuntime);

        JRubyAsciidoctorModuleFactory jRubyAsciidoctorModuleFactory = new JRubyAsciidoctorModuleFactory(rubyRuntime);

        AsciidoctorModule asciidoctorModule = jRubyAsciidoctorModuleFactory.createAsciidoctorModule();
        RubyAsciidoctorJ jRubyAsciidoctor= new RubyAsciidoctorJ(asciidoctorModule, rubyRuntime);

        return jRubyAsciidoctor;
    }
    
    private static void injectEnvironmentVariables(RubyInstanceConfig config, Map<String, Object> environmentVars) {
        EnvironmentInjector environmentInjector = new EnvironmentInjector(config);
        environmentInjector.inject(environmentVars);
    }

    private static RubyInstanceConfig createOptimizedConfiguration() {
        RubyInstanceConfig config = new RubyInstanceConfig();
        config.setCompatVersion(CompatVersion.RUBY1_9);
        config.setCompileMode(CompileMode.OFF);

        return config;
    }
    
    private RubyAsciidoctorJ(AsciidoctorModule asciidoctorModule, Ruby rubyRuntime) {
        super();
        this.asciidoctorModule = asciidoctorModule;
        this.rubyRuntime = rubyRuntime;
        this.rubyGemsPreloader = new RubyGemsPreloader(this.rubyRuntime);
    }
    
    @Override
    public String convert(String source) {
        return this.convert(source, new HashMap<>());
    }

    @Override
    public String convert(String source, Map<String, Object> options) {
        this.rubyGemsPreloader.preloadRequiredLibraries(options);

        if (log.isDebugEnabled()) {
            log.debug(AsciidoctorUtils.toAsciidoctorCommand(options, "-"));

            if (AsciidoctorUtils.isOptionWithAttribute(options, Attributes.SOURCE_HIGHLIGHTER, "pygments")) {
                log.debug("In order to use Pygments with Asciidoctor, you need to install Pygments (and Python, if you don’t have it yet). Read http://asciidoctor.org/news/#syntax-highlighting-with-pygments.");
            }
        }

        String currentDirectory = rubyRuntime.getCurrentDirectory();

        if (options.containsKey(Options.BASEDIR)) {
            rubyRuntime.setCurrentDirectory((String) options.get(Options.BASEDIR));
        }

        RubyHash rubyHash = RubyHashUtil.convertMapToRubyHashWithSymbols(rubyRuntime, options);

        Object object = this.asciidoctorModule.convert(source, rubyHash);

        // we restore current directory to its original value.
        rubyRuntime.setCurrentDirectory(currentDirectory);

        return returnExpectedValue(object);

    }

    @Override
    public String convert_file(File filename) {
        return this.convert_file(filename, new HashMap<>());
    }

    @Override
    public String convert_file(File filename, Map<String, Object> options) {
        this.rubyGemsPreloader.preloadRequiredLibraries(options);

        if (log.isDebugEnabled()) {
            log.debug(AsciidoctorUtils.toAsciidoctorCommand(options, filename.getAbsolutePath()));
        }

        String currentDirectory = rubyRuntime.getCurrentDirectory();

        if (options.containsKey(Options.BASEDIR)) {
            rubyRuntime.setCurrentDirectory((String) options.get(Options.BASEDIR));
        }

        RubyHash rubyHash = RubyHashUtil.convertMapToRubyHashWithSymbols(rubyRuntime, options);

        Object object = this.asciidoctorModule.convertFile(filename.getAbsolutePath(), rubyHash);

        // we restore current directory to its original value.
        rubyRuntime.setCurrentDirectory(currentDirectory);

        return returnExpectedValue(object);
    }
    
    @Override
    public <T extends ExtensionRegistry> T createExtensionRegistry(Class<T> type) throws IllegalArgumentException {
        // TODO this may change when Javascript is accepted but for now we can blindly call constructor because all classes implements the same constructor
        try {
            Constructor<T> constructor = type.getConstructor(AsciidoctorModule.class, Ruby.class);
            return constructor.newInstance(this.asciidoctorModule, this.rubyRuntime); 
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }

    
    
    @Override
    public void unregisterAllExtensions() {
        this.asciidoctorModule.unregister_all_extensions();
    }
    
    @Override
    public void shutdown() {
        this.rubyRuntime.tearDown();
    }
    
    @Override
    public String asciidoctorVersion() {
        return this.asciidoctorModule.asciidoctorRuntimeEnvironmentVersion();
    }

    
    @Override
    public Document load(String content, Map<String, Object> options) {
        RubyHash rubyHash = RubyHashUtil.convertMapToRubyHashWithSymbols(rubyRuntime, options);
        return new RubyDocument(this.asciidoctorModule.load(content, rubyHash), this.rubyRuntime);
    }

    @Override
    public Document load_file(File filename, Map<String, Object> options) {
        RubyHash rubyHash = RubyHashUtil.convertMapToRubyHashWithSymbols(rubyRuntime, options);
        return new RubyDocument(this.asciidoctorModule.load_file(filename.getAbsolutePath(), rubyHash), this.rubyRuntime);
    }

    /**
     * This method has been added to deal with the fact that asciidoctor 0.1.2 can return an Asciidoctor::RubyDocument or a
     * String depending if content is write to disk or not. This may change in the future
     * (https://github.com/asciidoctor/asciidoctor/issues/286)
     * 
     * @param object
     * @return
     */
    private String returnExpectedValue(Object object) {
        if (object instanceof String) {
            return object.toString();
        } else {
            return null;
        }
    }

}
