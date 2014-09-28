package org.asciidoctor.extension.internal;

import java.util.ServiceLoader;

import org.asciidoctor.extension.spi.ExtensionRegistry;
import org.asciidoctor.internal.AsciidoctorJ;

public class ExtensionRegistryExecutor {

    private static ServiceLoader<ExtensionRegistry> extensionRegistryServiceLoader = ServiceLoader
            .load(ExtensionRegistry.class);

    private AsciidoctorJ asciidoctor;

    public ExtensionRegistryExecutor(AsciidoctorJ asciidoctor) {
        this.asciidoctor = asciidoctor;
    }

    public void registerAllExtensions() {
        for (ExtensionRegistry extensionRegistry : extensionRegistryServiceLoader) {
            extensionRegistry.register(asciidoctor);
        }
    }
}
