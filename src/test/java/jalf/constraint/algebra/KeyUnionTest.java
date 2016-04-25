package jalf.constraint.algebra;

import static jalf.DSL.attr;
import static jalf.DSL.key;
import static jalf.DSL.keys;
import static jalf.DSL.relation;
import static jalf.DSL.tuple;
import static org.junit.Assert.assertEquals;
import jalf.AttrList;
import jalf.Relation;
import jalf.constraint.Key;
import jalf.type.Heading;

import org.junit.Test;

public class KeyUnionTest {
    @Test
    public void testUnionOperatorWithKey(){
	// union with header compatible
	// k1 union k2
	Relation r1 = relation(
		keys(key(attr("A"), attr("C"))),
		tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1"),
		tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3"),
		tuple(attr("A"), "a1", attr("B"), "b3", attr("C"), "c2"),
		tuple(attr("A"), "a2", attr("B"), "b1", attr("C"), "c1"),
		tuple(attr("A"), "a3", attr("B"), "b3", attr("C"), "c4"));

	Relation r2 = relation(
		keys(key(attr("A"), attr("B"))),
		tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1"),
		tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3"),
		tuple(attr("A"), "a2", attr("B"), "b4", attr("C"), "c2"),
		tuple(attr("A"), "a2", attr("B"), "b1", attr("C"), "c1"),
		tuple(attr("A"), "a3", attr("B"), "b1", attr("C"), "c2"));

	Relation r = r1.union(r2);
	Key actual = r.getKeys().toList().get(0);
	Key expected = key(attr("A"), attr("B"), attr("C"));
	assertEquals(expected, actual);
	// test if the header contain the key
	Heading h = r.getType().getHeading();
	AttrList l =  h.toAttrList().intersect(actual.toAttrList());
	assertEquals(l,actual.toAttrList());
    }

}
