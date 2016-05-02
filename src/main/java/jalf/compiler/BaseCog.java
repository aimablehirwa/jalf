package jalf.compiler;

import java.util.function.Supplier;
import java.util.stream.Stream;

import jalf.Relation;
import jalf.Tuple;

public class BaseCog extends Cog {

    private Supplier<Stream<Tuple>> streamSupplier;

    public BaseCog(Relation expr, Supplier<Stream<Tuple>> streamSupplier) {
        super(expr);
        this.streamSupplier = streamSupplier;
    }

    @Override
    public Stream<Tuple> stream() {
        return streamSupplier.get();
    }

}
