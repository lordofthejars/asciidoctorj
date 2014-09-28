package org.asciidoctor;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.asciidoctor.ast.AbstractBlock;
import org.asciidoctor.ast.ContentPart;
import org.asciidoctor.ast.Document;
import org.asciidoctor.ast.DocumentHeader;
import org.asciidoctor.ast.StructuredDocument;
import org.asciidoctor.ast.Title;
import org.asciidoctor.internal.AsciidoctorJ;
import org.asciidoctor.internal.IOUtils;
import org.asciidoctor.extension.ExtensionRegistry;

/**
 * 
 * 
 * @author lordofthejars
 * 
 */
public class Asciidoctor {

    public static final String STRUCTURE_MAX_LEVEL = "STRUCTURE_MAX_LEVEL";
    private static final int DEFAULT_MAX_LEVEL = 1;

    private AsciidoctorJ asciidoctorJ;
    
    public Asciidoctor(AsciidoctorJ asciidoctorJ) {
        this.asciidoctorJ = asciidoctorJ;
    }
    
    /**
     * Parse the AsciiDoc source input into an RubyDocument {@link Document} and render it to the specified backend
     * format.
     * 
     * Accepts input as String object.
     * 
     * 
     * @param content
     *            the AsciiDoc source as String.
     * @param options
     *            a Hash of options to control processing (default: {}).
     * @return the rendered output String is returned
     */
    public String convert(String content, Map<String, Object> options) {
        return this.asciidoctorJ.convert(content, options);
    }

    /**
     * Parse the AsciiDoc source input into an RubyDocument {@link Document} and render it to the specified backend
     * format.
     * 
     * Accepts input as String object.
     * 
     * 
     * @param content
     *            the AsciiDoc source as String.
     * @param options
     *            a Hash of options to control processing (default: {}).
     * @return the rendered output String is returned
     */
    public String convert(String content, Options options) {
        return this.convert(content, options.map());
    }

    /**
     * Parse the AsciiDoc source input into an RubyDocument {@link Document} and render it to the specified backend
     * format.
     * 
     * Accepts input as String object.
     * 
     * 
     * @param content
     *            the AsciiDoc source as String.
     * @param options
     *            a Hash of options to control processing (default: {}).
     * @return the rendered output String is returned
     */
    public String convert(String content, OptionsBuilder options) {
        return this.convert(content, options.asMap());
    }

    /**
     * Parse the document read from reader, and rendered result is sent to writer.
     * 
     * @param contentReader
     *            where asciidoc content is read.
     * @param rendererWriter
     *            where rendered content is written. Writer is flushed, but not closed.
     * @param options
     *            a Hash of options to control processing (default: {}).
     * @throws IOException
     *             if an error occurs while writing rendered content, this exception is thrown.
     */
    public void convert(Reader contentReader, Writer rendererWriter, Map<String, Object> options) throws IOException {
        String content = IOUtils.readFull(contentReader);
        String renderedContent = convert(content, options);
        IOUtils.writeFull(rendererWriter, renderedContent);
    }

    /**
     * Parse the document read from reader, and rendered result is sent to writer.
     * 
     * @param contentReader
     *            where asciidoc content is read.
     * @param rendererWriter
     *            where rendered content is written. Writer is flushed, but not closed.
     * @param options
     *            a Hash of options to control processing (default: {}).
     * @throws IOException
     *             if an error occurs while writing rendered content, this exception is thrown.
     */
    public void convert(Reader contentReader, Writer rendererWriter, Options options) throws IOException {
        this.convert(contentReader, rendererWriter, options.map());
    }

    /**
     * Parse the document read from reader, and rendered result is sent to writer.
     * 
     * @param contentReader
     *            where asciidoc content is read.
     * @param rendererWriter
     *            where rendered content is written. Writer is flushed, but not closed.
     * @param options
     *            a Hash of options to control processing (default: {}).
     * @throws IOException
     *             if an error occurs while writing rendered content, this exception is thrown.
     */
    public void convert(Reader contentReader, Writer rendererWriter, OptionsBuilder options) throws IOException {
        this.convert(contentReader, rendererWriter, options.asMap());
    }

    /**
     * Parse the AsciiDoc source input into an RubyDocument {@link Document} and render it to the specified backend
     * format.
     * 
     * Accepts input as File path.
     * 
     * If the :in_place option is true, and the input is a File, the output is written to a file adjacent to the input
     * file, having an extension that corresponds to the backend format. Otherwise, if the :to_file option is specified,
     * the file is written to that file. If :to_file is not an absolute path, it is resolved relative to :to_dir, if
     * given, otherwise the RubyDocument#base_dir. If the target directory does not exist, it will not be created unless the
     * :mkdirs option is set to true. If the file cannot be written because the target directory does not exist, or
     * because it falls outside of the RubyDocument#base_dir in safe mode, an IOError is raised.
     * 
     * @param filename
     *            an input Asciidoctor file.
     * @param options
     *            a Hash of options to control processing (default: {}).
     * @return returns nothing if the rendered output String is written to a file.
     */
    public String convertFile(File filename, Map<String, Object> options) {
        return this.asciidoctorJ.convertFile(filename, options);
    }

    /**
     * Parse the AsciiDoc source input into an RubyDocument {@link Document} and render it to the specified backend
     * format.
     * 
     * Accepts input as File path.
     * 
     * If the :in_place option is true, and the input is a File, the output is written to a file adjacent to the input
     * file, having an extension that corresponds to the backend format. Otherwise, if the :to_file option is specified,
     * the file is written to that file. If :to_file is not an absolute path, it is resolved relative to :to_dir, if
     * given, otherwise the RubyDocument#base_dir. If the target directory does not exist, it will not be created unless the
     * :mkdirs option is set to true. If the file cannot be written because the target directory does not exist, or
     * because it falls outside of the RubyDocument#base_dir in safe mode, an IOError is raised.
     * 
     * @param filename
     *            an input Asciidoctor file.
     * @param options
     *            a Hash of options to control processing (default: {}).
     * @return returns nothing if the rendered output String is written to a file.
     */
    public String convertFile(File filename, Options options) {
        return this.convertFile(filename, options.map());
    }

    /**
     * Parse the AsciiDoc source input into an RubyDocument {@link Document} and render it to the specified backend
     * format.
     * 
     * Accepts input as File path.
     * 
     * If the :in_place option is true, and the input is a File, the output is written to a file adjacent to the input
     * file, having an extension that corresponds to the backend format. Otherwise, if the :to_file option is specified,
     * the file is written to that file. If :to_file is not an absolute path, it is resolved relative to :to_dir, if
     * given, otherwise the RubyDocument#base_dir. If the target directory does not exist, it will not be created unless the
     * :mkdirs option is set to true. If the file cannot be written because the target directory does not exist, or
     * because it falls outside of the RubyDocument#base_dir in safe mode, an IOError is raised.
     * 
     * @param filename
     *            an input Asciidoctor file.
     * @param options
     *            a Hash of options to control processing (default: {}).
     * @return returns nothing if the rendered output String is written to a file.
     */
    public String convertFile(File filename, OptionsBuilder options) {
        return this.convertFile(filename, options.asMap());
    }

    /**
     * Parse all AsciiDoc files found using DirectoryWalker instance.
     * 
     * @param directoryWalker
     *            strategy used to retrieve all files to be rendered.
     * @param options
     *            a Hash of options to control processing (default: {}).
     * @return returns an array of 0 positions if the rendered output is written to a file.
     */
    public String[] convertDirectory(DirectoryWalker directoryWalker, Map<String, Object> options) {
        final List<File> asciidoctorFiles = scanForAsciiDocFiles(directoryWalker);
        List<String> asciidoctorContent = renderAllFiles(options, asciidoctorFiles);

        return asciidoctorContent.toArray(new String[asciidoctorContent.size()]);
    }

    private List<File> scanForAsciiDocFiles(DirectoryWalker directoryWalker) {
        final List<File> asciidoctorFiles = directoryWalker.scan();
        return asciidoctorFiles;
    }
    
    private List<String> renderAllFiles(Map<String, Object> options, final Collection<File> asciidoctorFiles) {
        List<String> asciidoctorContent = new ArrayList<String>();

        for (File asciidoctorFile : asciidoctorFiles) {
            String renderedFile = convertFile(asciidoctorFile, options);

            if (renderedFile != null) {
                asciidoctorContent.add(renderedFile);
            }

        }

        return asciidoctorContent;
    }
    

    /**
     * Parse all AsciiDoc files found using DirectoryWalker instance.
     * 
     * @param directoryWalker
     *            strategy used to retrieve all files to be rendered.
     * @param options
     *            a Hash of options to control processing (default: {}).
     * @return returns an array of 0 positions if the rendered output is written to a file.
     */
    public String[] convertDirectory(DirectoryWalker directoryWalker, Options options) {
        return this.convertDirectory(directoryWalker, options.map());
    }

    /**
     * Parse all AsciiDoc files found using DirectoryWalker instance.
     * 
     * @param directoryWalker
     *            strategy used to retrieve all files to be rendered.
     * @param options
     *            a Hash of options to control processing (default: {}).
     * @return returns an array of 0 positions if the rendered output is written to a file.
     */
    public String[] convertDirectory(DirectoryWalker directoryWalker, OptionsBuilder options) {
        return this.convertDirectory(directoryWalker, options.asMap());
    }

    /**
     * Parses all files added inside a collection.
     * 
     * @param asciidoctorFiles
     *            to be rendered.
     * @param options
     *            a Hash of options to control processing (default: {}).
     * @return returns an array of 0 positions if the rendered output is written to a file.
     */
    public String[] convertFiles(Collection<File> asciidoctorFiles, Map<String, Object> options) {
        List<String> asciidoctorContent = renderAllFiles(options, asciidoctorFiles);
        return asciidoctorContent.toArray(new String[asciidoctorContent.size()]);
    }

    /**
     * Parses all files added inside a collection.
     * 
     * @param asciidoctorFiles
     *            to be rendered.
     * @param options
     *            a Hash of options to control processing (default: {}).
     * @return returns an array of 0 positions if the rendered output is written to a file.
     */
    public String[] convertFiles(Collection<File> asciidoctorFiles, Options options) {
        return this.convertFiles(asciidoctorFiles, options.map());
    }

    /**
     * Parses all files added inside a collection.
     * 
     * @param asciidoctorFiles
     *            to be rendered.
     * @param options
     *            a Hash of options to control processing (default: {}).
     * @return returns an array of 0 positions if the rendered output is written to a file.
     */
    public String[] convertFiles(Collection<File> asciidoctorFiles, OptionsBuilder options) {
        return this.convertFiles(asciidoctorFiles, options.asMap());
    }

    /**
     * Reads and creates structured document containing header and content chunks. By default it dig only one level down
     * but it can be tweak by setting STRUCTURE_MAX_LEVEL option.
     * 
     * @param filename
     *            to read the attributes.
     * @param options
     *            a Hash of options to control processing (default: {}).
     * @return structured document.
     */
    public StructuredDocument readDocumentStructure(File filename, Map<String, Object> options) {

        Document document = this.asciidoctorJ.loadFile(filename, options);
        int maxDeepLevel = options.containsKey(STRUCTURE_MAX_LEVEL) ? (Integer) (options.get(STRUCTURE_MAX_LEVEL))
                : DEFAULT_MAX_LEVEL;
        return toDocument(document, maxDeepLevel);
    }

    /**
     * Reads and creates structured document containing header and content chunks. By default it dig only one level down
     * but it can be tweak by setting STRUCTURE_MAX_LEVEL option.
     * 
     * @param content
     *            where rendered content is written. Writer is flushed, but not closed.
     * @param options
     *            a Hash of options to control processing (default: {}).
     * @return structured document.
     */
    public StructuredDocument readDocumentStructure(String content, Map<String, Object> options) {

        Document document = this.asciidoctorJ.load(content, options);
        int maxDeepLevel = options.containsKey(STRUCTURE_MAX_LEVEL) ? (Integer) (options.get(STRUCTURE_MAX_LEVEL))
                : DEFAULT_MAX_LEVEL;
        return toDocument(document, maxDeepLevel);
    }

    /**
     * Reads and creates structured document containing header and content chunks. By default it dig only one level down
     * but it can be tweak by setting STRUCTURE_MAX_LEVEL option.
     * 
     * @param contentReader
     *            where asciidoc content is read.
     * @param options
     *            a Hash of options to control processing (default: {}).
     * @return structured document.
     */
    public StructuredDocument readDocumentStructure(Reader contentReader, Map<String, Object> options) {
        String content = IOUtils.readFull(contentReader);
        return readDocumentStructure(content, options);
    }

    /**
     * Reads only header parameters instead of all document.
     * 
     * @param filename
     *            to read the attributes.
     * @return header.
     */
    public DocumentHeader readDocumentHeader(File filename) {
        Document document = this.asciidoctorJ.loadFile(filename, new HashMap<>());
        return toDocumentHeader(document);
    }

    /**
     * Reads only header parameters instead of all document.
     * 
     * @param content
     *            where rendered content is written. Writer is flushed, but not closed.
     * @return header.
     */
    public DocumentHeader readDocumentHeader(String content) {
        Document document = this.asciidoctorJ.load(content, new HashMap<>());
        return toDocumentHeader(document);
    }

    /**
     * Reads only header parameters instead of all document.
     * 
     * @param contentReader
     *            where asciidoc content is read.
     * @return header.
     */
    public DocumentHeader readDocumentHeader(Reader contentReader) {
        String content = IOUtils.readFull(contentReader);
        return readDocumentHeader(content);
    }

    private StructuredDocument toDocument(Document document, int maxDeepLevel) {
        List<ContentPart> contentParts = getContents(document.blocks(), 1, maxDeepLevel);
        return StructuredDocument.createStructuredDocument(toDocumentHeader(document), contentParts);
    }
    
    private DocumentHeader toDocumentHeader(Document document) {
        Map<Object, Object> opts = new HashMap<Object, Object>();
        opts.put("partition", true);

        return DocumentHeader.createDocumentHeader((Title) document.doctitle(opts), document.title(),
                document.getAttributes());
    }
    
    private List<ContentPart> getContents(List<AbstractBlock> blocks, int level, int maxDeepLevel) {
        // finish getting childs if max structure level was riched
        if (level > maxDeepLevel) {
            return null;
        }
        // if document has only one child don't treat as actual contentpart
        // unless
        // it has no childs
        /*
         * if (blocks.size() == 1 && blocks.get(0).blocks().size() > 0) { return getContents(blocks.get(0).blocks(), 0,
         * maxDeepLevel); }
         */
        // add next level of contentParts
        List<ContentPart> parts = new ArrayList<ContentPart>();
        for (AbstractBlock block : blocks) {
            parts.add(getContentPartFromBlock(block, level, maxDeepLevel));
        }
        return parts;
    }

    private ContentPart getContentPartFromBlock(AbstractBlock child, int level, int maxDeepLevel) {
        Object content = child.content();
        String textContent;
        if (content instanceof String) {
            textContent = (String) content;
        } else {
            textContent = child.convert();
        }
        ContentPart contentPart = ContentPart.createContentPart(child.id(), level, child.context(), child.title(),
                child.style(), child.role(), child.attributes(), textContent);
        contentPart.setParts(getContents(child.blocks(), level + 1, maxDeepLevel));
        return contentPart;
    }
    
    public <T extends ExtensionRegistry> T createExtensionRegistry(final Class<T> type) {
        return this.asciidoctorJ.createExtensionRegistry(type);
    }

    /**
     * Unregister all registered extensions.
     */
    public void unregisterAllExtensions() {
        this.asciidoctorJ.unregisterAllExtensions();
    }

    /**
     * This method frees all resources consumed by asciidoctorJ module. Keep in mind that if this method is called,
     * instance becomes unusable and you should create another instance.
     */
    public void shutdown() {
        this.asciidoctorJ.shutdown();
    }

    /**
     * Method that gets the asciidoctor version which is being used..
     * 
     * @return Version number.
     */
    public String asciidoctorVersion() {
        return this.asciidoctorJ.asciidoctorVersion();
    }

    /**
     * Loads AsciiDoc content and returns the RubyDocument object.
     * 
     * @param content
     *            to be parsed.
     * @param options
     * @return RubyDocument of given content.
     */
    public Document load(String content, Map<String, Object> options) {
        return this.asciidoctorJ.load(content, options);
    }

    /**
     * Loads AsciiDoc content from file and returns the RubyDocument object.
     * 
     * @param file
     *            to be loaded.
     * @param options
     * @return RubyDocument of given content.
     */
    public Document loadFile(File file, Map<String, Object> options) {
        return this.asciidoctorJ.loadFile(file, options);
    }
    
    public AsciidoctorJ getAsciidoctorJ() {
        return asciidoctorJ;
    }

}
