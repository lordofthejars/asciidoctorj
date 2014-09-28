package org.asciidoctor.ruby.ast;

import java.util.List;

import org.asciidoctor.ast.Block;
import org.jruby.Ruby;

public class RubyBlock extends RubyAbstractBlock implements Block {
    private Block blockDelegate;

    public RubyBlock(Block blockDelegate, Ruby runtime) {
        super(blockDelegate, runtime);
        this.blockDelegate = blockDelegate;
    }

    @Override
    public List<String> lines() {
        return blockDelegate.lines();
    }
    
    @Override
    public String source() {
        return blockDelegate.source();
    }
}
