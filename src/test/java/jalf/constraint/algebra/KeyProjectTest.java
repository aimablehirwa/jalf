package jalf.constraint.algebra;

import static jalf.DSL.attr;
import static jalf.DSL.attrs;
import static jalf.DSL.key;
import static jalf.DSL.keys;
import static jalf.DSL.relation;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.QTY;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.shipments;
import static org.junit.Assert.assertEquals;
import jalf.Relation;
import jalf.constraint.Keys;

import org.junit.Test;

public class KeyProjectTest {


    @Test
    public void testProjectOperatorWithKey1(){
	// test Ps intersect Kx = null : Kn = on
	Relation r = shipments();
	Relation p = r.project(attrs(QTY));
	Keys actual = p.getKeys();
	Keys expected = keys(key(QTY));
	assertEquals(expected, actual);
	// test if the header contain the key
	//Heading h = p.getType().getHeading();
	//AttrList l =  h.toAttrList().intersect(actual.toAttrList());
	//assertEquals(l,actual.toAttrList());
    }

    @Test
    public void testProjectOperatorWithKey2(){
	// test Ps intersect Kx = Psx : Kn = on
	// the new key must be the projected attributes
	Relation r = shipments();
	Relation p = r.project(attrs(SID, QTY));
	Keys actual = p.getKeys();
	Keys expected = keys(key(SID, QTY));
	assertEquals(expected, actual);
	// test if the header contain the key
	//Heading h = p.getType().getHeading();
	//AttrList l =  h.toAttrList().intersect(actual.toAttrList());
	//assertEquals(l,actual.toAttrList());
    }

    @Test
    public void testProjectOperatorWithKey3(){
	// test Ps intersect Ks = Ks : Kn = Ks
	// the key and the projected attributes are the same,
	// so the key of the projection is the key of r
	Relation r = shipments();
	Relation p = r.project(attrs(SID, PID, QTY));
	Keys actual = p.getKeys();
	Keys expected = keys(key(SID, PID));
	assertEquals(expected, actual);
	// test if the header contain the key
	//Heading h = p.getType().getHeading();
	//AttrList l =  h.toAttrList().intersect(actual.toAttrList());
	//assertEquals(l,actual.toAttrList());
    }

    @Test
    public void testProjectOperatorWithKey4(){
	// test Ps intersect Ks = Ps : Kn = on
	// the key and the projected attributes are the same,
	// so the key of the projection is the key of r
	Relation r = shipments();
	Relation p = r.project(attrs(SID));
	Keys actual = p.getKeys();
	Keys expected = keys(key(SID));
	assertEquals(expected, actual);
	// test if the header contain the key
	//Heading h = p.getType().getHeading();
	//AttrList l =  h.toAttrList().intersect(actual.toAttrList());
	//assertEquals(l,actual.toAttrList());
    }

    @Test
    public void testProjectOperatorWithMultiKey(){
	// test Ps intersect Ks = Ks : Kn = Ks
	Relation r = relation(
		keys(key(attr("A"), attr("C")), key(attr("B"))),
		tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1"),
		tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3"),
		tuple(attr("A"), "a1", attr("B"), "b3", attr("C"), "c2"),
		tuple(attr("A"), "a2", attr("B"), "b5", attr("C"), "c1"),
		tuple(attr("A"), "a3", attr("B"), "b7", attr("C"), "c4"));

	Relation p = r.project(attrs(attr("A"), attr("B")));
	Keys actual = p.getKeys();
	Keys expected = keys(key(attr("B")));
	assertEquals(expected, actual);
    }

    @Test
    public void testProjectOperatorWithMultiKey2(){
	// test Ps intersect Ks = Ks : Kn = Ks
	Relation r = relation(
		keys(key(attr("A"), attr("C")), key(attr("B")), key(attr("D"))),
		tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1", attr("D"), "d1"),
		tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3", attr("D"), "d7"),
		tuple(attr("A"), "a1", attr("B"), "b3", attr("C"), "c2", attr("D"), "d5"),
		tuple(attr("A"), "a2", attr("B"), "b5", attr("C"), "c1", attr("D"), "d3"),
		tuple(attr("A"), "a3", attr("B"), "b7", attr("C"), "c4", attr("D"), "d4"));

	Relation p = r.project(attrs(attr("A"), attr("C"), attr("D")));
	Keys actual = p.getKeys();
	Keys expected = keys(key(attr("A"), attr("C")), key(attr("D")));
	assertEquals(expected, actual);
    }


}
