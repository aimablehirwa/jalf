package jalf.compiler;

import jalf.AttrList;
import jalf.Relation;
import jalf.Renaming;
import jalf.Tuple;
import jalf.relation.algebra.Project;
import jalf.relation.algebra.Rename;

import java.util.stream.Stream;

/**
 * Parent (abstract) class of all relation implementations.
 *
 * Abstract relations are able to compile themselves as Cogs that encapsulate
 * a stream of tuples, which is the one served by `stream()`. The actual
 * compilation process depends on the kind of Relation (e.g. expressions
 * vs. in-memory relations). See documentation of those for more about the
 * compilation process.
 */
public abstract class AbstractRelation implements Relation {

    @Override
    public Relation project(AttrList attributes) {
        return new Project(this, attributes);
    }

    @Override
    public Relation rename(Renaming renaming) {
        return new Rename(this, renaming);
    }

    ///

    @Override
    public Stream<Tuple> stream() {
        return compile().stream();
    }

    @Override
    public long count() {
        return stream().count();
    }

    ///

    public abstract Cog compile();

}
