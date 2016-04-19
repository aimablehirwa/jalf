package jalf.constraint.algebra;

import static jalf.DSL.attr;
import static jalf.DSL.key;
import static jalf.DSL.rename;
import static jalf.DSL.renaming;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.QTY;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.SUPPLIER_ID;
import static jalf.fixtures.SuppliersAndParts.shipments;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jalf.AttrList;
import jalf.Relation;
import jalf.constraint.Key;
import jalf.type.Heading;

public class KeyRenameTest {

    public void testRenameTheEntireKey(){
        Relation r = shipments();
        Key expected = key(attr("RS"), attr("RP"));
        Relation rn = rename(r, renaming(SID, attr("RS"), PID, attr("RP")));
        Key actual= rn.getKeys().toList().get(0);
        assertEquals(expected, actual);

        // test if the header contain the key
        Heading h = rn.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.toAttrList());
        assertEquals(l,actual.toAttrList());
    }


    @Test
    public void testRenameAnAttrOfKey(){
        Relation r = shipments();
        Key expected = key(SUPPLIER_ID,PID);
        Relation rn = rename(r, renaming(SID, SUPPLIER_ID));
        Key actual= rn.getKeys().toList().get(0);
        assertEquals(expected, actual);

        // test if the header contain the key
        Heading h = rn.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.toAttrList());
        assertEquals(l,actual.toAttrList());
    }

    @Test
    public void testRenameNotTheKey(){
        // test renaming if there is no intersection
        // between the key and the renaming attributes
        Relation r = shipments();
        Relation rn = rename(r, renaming(QTY, attr("RQ")));
        Key actual=  rn.getKeys().toList().get(0);
        Key expected = key(SID,PID);
        assertEquals(expected, actual);
        // test if the header contain the key
        Heading h = rn.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.toAttrList());
        assertEquals(l,actual.toAttrList());
    }

}
