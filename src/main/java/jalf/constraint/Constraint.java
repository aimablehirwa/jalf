package jalf.constraint;

import jalf.AttrList;
import jalf.Relation;

public interface Constraint {
    public boolean Check(Relation r,Key key);
    public AttrList getAttrsKey();

}
