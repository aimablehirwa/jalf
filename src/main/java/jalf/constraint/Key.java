package jalf.constraint;

import java.util.function.Supplier;
import java.util.stream.Stream;

import jalf.AttrList;
import jalf.Relation;
import jalf.Renaming;
import jalf.Tuple;
import jalf.type.TupleType;
public class Key  implements Constraint{
    private AttrList attrsKey;

    public Key(AttrList attrsKey) {
        super();
        this.attrsKey=attrsKey;
    }

    public  static Key primary(AttrList attrsKey) {
        return new Key(attrsKey);
    }

    @Override
    public boolean Check(Relation r, Key key) {
        //vérifier si les clef appartienne bien au atributs de la relation
        AttrList attrsrelation= r.getType().getHeading().toAttrList();
        AttrList intersect= getIntersectKeyAttr(attrsrelation,key);
        if (intersect.equals(key.getAttrsKey())){
            // deuxieme vérification sur la cardinality apres projection
            // sur la clef
            long lproject= getCardinalityKeyProject(r,key.getAttrsKey());
            return(lproject ==r.cardinality());
        }
        else{
            return false;
        }
    }

    public AttrList getIntersectKeyAttr( AttrList attrsrelation, Key key) {
        AttrList attrskey= key.getAttrsKey();
        return  attrsrelation.intersect(attrskey);
    }
    private long getCardinalityKeyProject( Relation r , AttrList intersect) {
        TupleType tt = r.getTupleType();
        Supplier<Stream<Tuple>> supplier;
        supplier = () -> r.stream()
                .map(t -> t.project(intersect, tt))
                .distinct();
        return supplier.get().count();
    }

    public AttrList getAttrsKey() {
        return attrsKey;
    }

    public Key rename(Renaming renaming) {
        AttrList newkey= this.attrsKey.rename(renaming);
        return new Key(newkey);
    }

    @Override
    public int hashCode() {
        return attrsKey.hashCode();
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
