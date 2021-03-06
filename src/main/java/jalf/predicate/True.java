package jalf.predicate;

import java.util.Set;

import jalf.AttrList;
import jalf.AttrName;
import jalf.Predicate;
import jalf.Renaming;
import jalf.Tuple;
import jalf.util.Pair;

/**
 * The TRUE predicate.
 */
public class True extends Predicate {

    private static True INSTANCE;

    private True() {
    }

    public static synchronized Predicate instance() {
        if (INSTANCE == null) {
            INSTANCE = new True();
        }
        return INSTANCE;
    }

    @Override
    public Predicate and(Predicate other) {
        return other;
    }

    public Predicate not() {
        return FALSE;
    }

    @Override
    public boolean test(Tuple t) {
        return true;
    }

    @Override
    public void fillReferencedAttributes(Set<AttrName> attrNames) {
    }

    @Override
    public Predicate rename(Renaming renaming) {
        return this;
    }

    @Override
    public Pair<Predicate> split(AttrList list) {
        return new Pair<>(this, this);
    }

    @Override
    public String toString() {
        return "true";
    }
}
