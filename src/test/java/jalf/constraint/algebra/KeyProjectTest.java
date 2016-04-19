package jalf.constraint.algebra;

import static jalf.DSL.attrs;
import static jalf.DSL.key;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.QTY;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.shipments;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jalf.AttrList;
import jalf.Relation;
import jalf.constraint.Key;
import jalf.type.Heading;

public class KeyProjectTest {


    @Test
    public void testProjectOperatorWithKey1(){
        // test Ps intersect Kx = null : Kn = Ps
        Relation r = shipments();
        Relation p = r.project(attrs(QTY));
        Key actual = p.getKeys().toList().get(0);
        Key expected = key(QTY);
        assertEquals(expected, actual);
        // test if the header contain the key
        Heading h = p.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.toAttrList());
        assertEquals(l,actual.toAttrList());
    }

    @Test
    public void testProjectOperatorWithKey2(){
        // test Ps intersect Kx = Psx : Kn = on
        // the new key must be the projected attributes
        Relation r = shipments();
        Relation p = r.project(attrs(SID, QTY));
        Key actual =p.getKeys().toList().get(0);
        Key expected = key(SID, QTY);
        assertEquals(expected, actual);
        // test if the header contain the key
        Heading h = p.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.toAttrList());
        assertEquals(l,actual.toAttrList());
    }

    @Test
    public void testProjectOperatorWithKey3(){
        // test Ps intersect Ks = Ks : Kn = Ks
        // the key and the projected attributes are the same,
        // so the key of the projection is the key of r
        Relation r = shipments();
        Relation p = r.project(attrs(SID, PID, QTY));
        Key actual = p.getKeys().toList().get(0);
        Key expected = key(SID, PID);
        assertEquals(expected, actual);
        // test if the header contain the key
        Heading h = p.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.toAttrList());
        assertEquals(l,actual.toAttrList());
    }

    @Test
    public void testProjectOperatorWithKey4(){
        // test Ps intersect Ks = Ps : Kn = Ps
        // the key and the projected attributes are the same,
        // so the key of the projection is the key of r
        Relation r = shipments();
        Relation p = r.project(attrs(SID));
        Key actual =p.getKeys().toList().get(0);
        Key expected = key(SID);
        assertEquals(expected, actual);
        // test if the header contain the key
        Heading h = p.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.toAttrList());
        assertEquals(l,actual.toAttrList());
    }

    @Test
    public void testProjectOperatorWithKey4bis(){
        // test Ps intersect Ks = Ps : Kn = Ps
        // the key and the projected attributes are the same,
        // so the key of the projection is the key of r
        Relation r = shipments();
        Relation p = r.project(attrs(SID));
        Key actual = p.getKeys().toList().get(0);
        Key expected = key(SID);
        assertEquals(expected, actual);
        // test if the header contain the key
        Heading h = p.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.toAttrList());
        assertEquals(l,actual.toAttrList());
    }


}
