package jalf.constraint;

import jalf.AttrList;
import jalf.Relation;

public class Key  implements constraint{
    private AttrList attrsKey;

    public Key(AttrList attrsKey) {
        super();
        this.attrsKey=attrsKey;
    }
    public  static Key primary(AttrList attrsKey) {
        return new Key(attrsKey);
    }



    @Override
    public boolean Check(Relation r) {
        // TODO Auto-generated method stub
        return false;
    }


    public AttrList getAttrsKey() {
        return attrsKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || !(o instanceof Key))
            return false;
        Key otherkey = (Key) o;
        return (this.attrsKey.equals(otherkey.attrsKey));
    }



}
