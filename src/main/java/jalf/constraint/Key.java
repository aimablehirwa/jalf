package jalf.constraint;

import java.util.function.Supplier;
import java.util.stream.Stream;

import jalf.AttrList;
import jalf.AttrName;
import jalf.Relation;
import jalf.Renaming;
import jalf.Tuple;
import jalf.type.TupleType;
public class Key  implements constraint{
    private AttrList attrsKey;

    public Key(AttrList attrsKey) {
        super();
        this.attrsKey=attrsKey;
    }

    public  static Key primary(AttrList attrsKey) {
        return new Key(attrsKey);
    }

    public boolean Check(Relation r, Key key) {
        //vérifier si les clef appartienne bien au atributs de la relation
        AttrList attrsrelation= r.getType().getHeading().toAttrList();
        AttrList intersect= getIntersectKeyAttr(attrsrelation,key);
        if (intersect.equals(key.getAttrsKey())){
            // deuxieme vérification sur la cardinality apres projection

            TupleType tt = r.getTupleType();
            Supplier<Stream<Tuple>> supplier;
            supplier = () -> r.stream()
                    .map(t -> t.project(intersect, tt))
                    .distinct();
            long lproject= supplier.get().count();
            System.out.println(lproject);
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


    public AttrList getAttrsKey() {
        return attrsKey;
    }


    public Key rename(Renaming renaming) {
        AttrList newkey=AttrList.attrs(AttrName.attr("rid"));
        return new Key(newkey);
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
