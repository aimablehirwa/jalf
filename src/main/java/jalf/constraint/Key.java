package jalf.constraint;

import jalf.AttrList;
import jalf.Relation;

public class Key  implements constraint{
    private AttrList attrsKey;

    public Key(AttrList attrsKey) {
        super();
        this.setAttrsKey(attrsKey);
    }


    @Override
    public boolean Check(Relation r) {
        // TODO Auto-generated method stub
        return false;
    }


    public AttrList getAttrsKey() {
        return attrsKey;
    }


    public void setAttrsKey(AttrList attrsKey) {
        this.attrsKey = attrsKey;
    }

}
