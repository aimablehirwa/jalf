package jalf.constraint;

import jalf.AttrList;
import jalf.Relation;
import jalf.Renaming;
import jalf.Tuple;
import jalf.type.TupleType;

import java.util.function.Supplier;
import java.util.stream.Stream;
public class Key  implements Constraint{
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
    @Override
    public boolean checkKeyUniqueness(Relation r) {
        //vérifier si les clef appartienne bien au atributs de la relation
        AttrList attrsrelation= r.getType().getHeading().toAttrList();
        //AttrList intersect= getIntersectKeyAttr(attrsrelation, this);
        if (attrsrelation.intersect(this.getAttrsKey()).equals(this.getAttrsKey())){
            //if (intersect.equals(this.getAttrsKey())){
            // deuxieme vérification sur la cardinality apres projection
            // sur la clef
            long lproject= getCardinalityKeyProject(r);
            return(lproject == r.cardinality());
        }
        else{
            return false;
        }
    }

    private long getCardinalityKeyProject(Relation r) {
        TupleType tt = r.getTupleType();
        Supplier<Stream<Tuple>> supplier;
        supplier = () -> r.stream()
                .map(t -> t.project(this.attrsKey, tt))
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

    public AttrList insersect(AttrList attributes) {
        return this.attrsKey.intersect(attributes);
    }

    public AttrList union(Key key) {
        return this.attrsKey.union(key.attrsKey);
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
