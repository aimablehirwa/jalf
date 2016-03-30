package jalf.relation.algebra;

import java.util.Collections;

import jalf.Relation;
import jalf.Tuple;
import jalf.Visitor;
import jalf.compiler.AbstractRelation;
import jalf.compiler.BaseCog;
import jalf.compiler.Cog;
import jalf.constraint.Key;
import jalf.type.RelationType;

/**
 * Dee, the relation with no attribute and no tuple.
 */
public class Dum extends AbstractRelation {

    private static final RelationType TYPE = RelationType.dress();

    private static Relation INSTANCE;

    private Dum() {}

    public static synchronized Relation instance() {
        if (INSTANCE == null) {
            INSTANCE = new Dum();
        }
        return INSTANCE;
    }

    @Override
    public RelationType getType() {
        return TYPE;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }

    public Cog toCog() {
        return new BaseCog(this, () -> Collections.<Tuple>emptyList().stream());
    }

    @Override
    public boolean equals(Relation arg) {
        if (arg == this)
            return true;
        if (arg == null || !(arg instanceof Relation))
            return false;
        Relation other = arg;
        return TYPE.equals(other.getType()) &&
                !other.stream().findFirst().isPresent();
    }

    @Override
    public Key getKey() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setKey(Key key) {
        // TODO Auto-generated method stub

    }

}
