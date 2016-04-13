package jalf.constraint;

import java.util.function.Supplier;
import java.util.stream.Stream;

import jalf.AttrList;
import jalf.Relation;
import jalf.Renaming;
import jalf.Tuple;
import jalf.type.TupleType;

public class Key  implements Constraint{

    public static final Key EMPTY = new Key(AttrList.EMPTY);

    private AttrList attrsKey;

    public Key(AttrList attrsKey) {
        super();
        this.attrsKey=attrsKey;
    }

    public  static Key primary(AttrList attrsKey) {
        return new Key(attrsKey);
    }

    /*
     * check if each tuples containt unique key
     *
     * (non-Javadoc)
     * @see jalf.constraint.Constraint#checkKeyUniqueness(jalf.Relation)
     */

    public boolean checkKeyUniqueness(Relation r) {
        //vérifier si les clef appartienne bien au atributs de la relation
        AttrList attrsrelation= r.getType().getHeading().toAttrList();
        AttrList intersect= getIntersectKeyAttr(attrsrelation);
        if (intersect.equals(this.attrsKey)){
            // deuxieme vérification sur la cardinality apres projection
            // sur la clef
            long lproject= getCardinalityKeyProject(r);
            return(lproject == r.cardinality());
        }
        else{
            return false;
        }
    }

    public AttrList getIntersectKeyAttr( AttrList attrsrelation) {
        return  attrsrelation.intersect( this.attrsKey);
    }

    private long getCardinalityKeyProject(Relation r) {
        TupleType tt = r.getTupleType();
        Supplier<Stream<Tuple>> supplier;
        supplier = () -> r.stream()
                .map(t -> t.project(this.attrsKey, tt))
                .distinct();
        return supplier.get().count();
    }

    @Override
    public AttrList getAttrsKey() {
        return attrsKey;
    }

    public Key rename(Renaming renaming) {
        AttrList newkey= this.attrsKey.rename(renaming);
        return new Key(newkey);
    }

    public Key union(Key other) {
        AttrList newkey=  this.attrsKey.union(other.attrsKey);
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

    @Override
    public boolean Check(Relation r) {
        // TODO Auto-generated method stub
        return false;
    }

}
