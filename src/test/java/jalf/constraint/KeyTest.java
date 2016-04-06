package jalf.constraint;

import static jalf.DSL.attr;
import static jalf.DSL.attrs;
import static jalf.DSL.eq;
import static jalf.DSL.key;
import static jalf.DSL.relation;
import static jalf.DSL.rename;
import static jalf.DSL.renaming;
import static jalf.DSL.select;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.NAME;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.QTY;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.SUPPLIER_ID;
import static jalf.fixtures.SuppliersAndParts.shipments;
import static jalf.fixtures.SuppliersAndParts.suppliers;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import jalf.AttrList;
import jalf.AttrName;
import jalf.Relation;
import jalf.Renaming;
import jalf.Selection;
import jalf.SelectionMember;
import jalf.Type;
import jalf.type.Heading;

public class KeyTest {

    @Test
    public void testHashCode() {
        Key k1 = key(SID, PID);
        Key k2 = key(SID, PID);
        Key k3 = key(PID, SID);
        Key k4 = key(PID, QTY);
        assertEquals(k1.hashCode(), k2.hashCode());
        assertEquals(k1.hashCode(), k3.hashCode());
        assertNotEquals(k1.hashCode(), k4.hashCode());
    }

    @Test
    public void testEquals() {
        Key k1 = key(SID, PID);
        Key k2 = key(PID, SID);
        Key k3 = key(PID, QTY);
        assertEquals(k1, k1);
        assertEquals(k1, k2);
        assertNotEquals(k1, k3);
    }

    @Test
    public void testCheckIfItisCandidateKey(){
        // test check if the key can be candidate
        Relation r = shipments();
        r.setKey(key(SID, PID));
        Key k1 = key(SID, PID);
        assertEquals(k1,r.getKey());
    }

    @Test
    public void testHeaderIsKey(){
        // the key is the header if the key entered don't support the uniqueness constraint
        Relation r = shipments();
        r.setKey(key(SID));
        Key k1 = key(SID,PID,QTY);
        assertEquals(r.getKey(),k1);
    }

    @Test
    public void testHeaderIsKeyIfKeyNotSpecified(){
        // the key is the header if no key specified
        Relation r = shipments();
        Key k1 = key(SID,PID,QTY);
        assertEquals(r.getKey(),k1);
    }

    @Test
    public void testCheckKeyUniquenessTrue(){
        Relation r = shipments();
        Key k = key(SID, PID);
        assertTrue(k.checkKeyUniqueness(r));
    }

    @Test
    public void testCheckKeyUniquenessFalse(){
        Relation r = shipments();
        Key k = key(SID);
        assertFalse(k.checkKeyUniqueness(r));
    }

    @Test
    public void testRenameTheKey(){
        Key k1 = key(SID,PID);
        Renaming rn = renaming(SID, attr("RS"), PID, attr("RP"));
        Key actual = k1.rename(rn);
        Key expected = key(attr("RS"), attr("RP"));
        assertEquals(expected, actual);
    }



    @Test
    public void testKeyRelationEquality(){
        Relation r1 = shipments();
        Relation r2 = shipments();
        assertEquals(r1.getKey(), r2.getKey());
    }

    @Test
    public void testKeyRelationEquality1(){
        AttrName LETTER = AttrName.attr("letter");
        Relation r1 = relation(
                tuple(LETTER, "h"),
                tuple(LETTER, "s"),
                tuple(LETTER, "e"),
                tuple(LETTER, "k")
                );
        Relation r2 = relation(
                tuple(LETTER, "h"),
                tuple(LETTER, "s"),
                tuple(LETTER, "e"),
                tuple(LETTER, "k")
                );
        assertEquals(r1.getKey(), r2.getKey());
    }

    // test related to each operator


    public void testRenameTheEntireKey(){
        Relation r = shipments();
        r.setKey(key(SID, PID));
        Key expected = key(attr("RS"), attr("RP"));
        Relation rn = rename(r, renaming(SID, attr("RS"), PID, attr("RP")));
        Key actual= rn.getKey();
        assertEquals(expected, actual);

        // test if the header contain the key
        Heading h = rn.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.getAttrsKey());
        assertEquals(l,actual.getAttrsKey());
    }


    @Test
    public void testRenameAnAttrOfKey(){
        Relation r = shipments();
        r.setKey(key(SID, PID));
        Key expected = key(SUPPLIER_ID,PID);
        Relation rn = rename(r, renaming(SID, SUPPLIER_ID));
        Key actual= rn.getKey();
        assertEquals(expected, actual);

        // test if the header contain the key
        Heading h = rn.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.getAttrsKey());
        assertEquals(l,actual.getAttrsKey());
    }

    @Test
    public void testRenameNotTheKey(){
        // test renaming if there is no intersection
        // between the key and the renaming attributes
        Relation r = shipments();
        r.setKey(key(SID, PID));
        Relation rn = rename(r, renaming(QTY, attr("RQ")));
        Key actual= rn.getKey();
        Key expected = key(SID,PID);
        assertEquals(expected, actual);
        // test if the header contain the key
        Heading h = rn.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.getAttrsKey());
        assertEquals(l,actual.getAttrsKey());
    }

    @Test
    public void testProjectOperatorWithKey1(){
        // test Ps intersect Kx = null : Kn = Ps
        Relation r = shipments();
        r.setKey(key(SID, PID));
        Relation p = r.project(attrs(QTY));
        Key actual = p.getKey();
        Key expected = key(QTY);
        assertEquals(expected, actual);
        // test if the header contain the key
        Heading h = p.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.getAttrsKey());
        assertEquals(l,actual.getAttrsKey());
    }

    @Test
    public void testProjectOperatorWithKey2(){
        // test Ps intersect Kx = Psx : Kn = on
        // the new key must be the projected attributes
        Relation r = shipments();
        r.setKey(key(SID, PID));
        Relation p = r.project(attrs(SID, QTY));
        Key actual = p.getKey();
        Key expected = key(SID, QTY);
        assertEquals(expected, actual);
        // test if the header contain the key
        Heading h = p.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.getAttrsKey());
        assertEquals(l,actual.getAttrsKey());
    }

    @Test
    public void testProjectOperatorWithKey3(){
        // test Ps intersect Ks = Ks : Kn = Ks
        // the key and the projected attributes are the same,
        // so the key of the projection is the key of r
        Relation r = shipments();
        r.setKey(key(SID, PID));
        Relation p = r.project(attrs(SID, PID, QTY));
        Key actual = p.getKey();
        Key expected = key(SID, PID);
        assertEquals(expected, actual);
        // test if the header contain the key
        Heading h = p.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.getAttrsKey());
        assertEquals(l,actual.getAttrsKey());
    }

    @Test
    public void testProjectOperatorWithKey4(){
        // test Ps intersect Ks = Ps : Kn = Ps
        // the key and the projected attributes are the same,
        // so the key of the projection is the key of r
        Relation r = shipments();
        r.setKey(key(SID, PID));
        Relation p = r.project(attrs(SID));
        Key actual = p.getKey();
        Key expected = key(SID);
        assertEquals(expected, actual);
        // test if the header contain the key
        Heading h = p.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.getAttrsKey());
        assertEquals(l,actual.getAttrsKey());
    }

    @Test
    public void testProjectOperatorWithKey4bis(){
        // test Ps intersect Ks = Ps : Kn = Ps
        // the key and the projected attributes are the same,
        // so the key of the projection is the key of r
        Relation r = shipments();
        Relation p = r.project(attrs(SID));
        Key actual = p.getKey();
        Key expected = key(SID);
        assertEquals(expected, actual);
        // test if the header contain the key
        Heading h = p.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.getAttrsKey());
        assertEquals(l,actual.getAttrsKey());
    }

    @Test
    public void testRestrictOperatorWithKey(){
        // restrict does not have impact on the key
        Relation r = shipments();
        r.setKey(key(SID, PID));

        Relation rs = r.restrict(eq(SID, "S1"));
        Key actual = rs.getKey();
        Key expected = key(SID, PID);
        assertEquals(expected, actual);
        // test if the header contain the key
        Heading h = rs.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.getAttrsKey());
        assertEquals(l,actual.getAttrsKey());

    }

    @Test
    public void testIntersectOperatorWithKey(){
        // intersect header compatible
        // k1 union k2
        Relation r1 = relation(
                tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1"),
                tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3"),
                tuple(attr("A"), "a1", attr("B"), "b3", attr("C"), "c2"),
                tuple(attr("A"), "a2", attr("B"), "b1", attr("C"), "c1"),
                tuple(attr("A"), "a3", attr("B"), "b3", attr("C"), "c4"));

        Relation r2 = relation(
                tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1"),
                tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3"),
                tuple(attr("A"), "a2", attr("B"), "b4", attr("C"), "c2"),
                tuple(attr("A"), "a2", attr("B"), "b1", attr("C"), "c1"),
                tuple(attr("A"), "a3", attr("B"), "b1", attr("C"), "c2"));

        r1.setKey(key(attr("A"), attr("C")));
        r2.setKey(key(attr("A"), attr("B")));
        Relation r = r1.intersect(r2);
        Key actual = r.getKey();
        Key expected = key(attr("A"), attr("B"), attr("C"));
        assertEquals(expected, actual);
        // test if the header contain the key
        Heading h = r.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.getAttrsKey());
        assertEquals(l,actual.getAttrsKey());
    }

    @Test
    public void testMinusOperatorWithKey(){
        // minus with header compatible
        // k1
        Relation r1 = relation(
                tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1"),
                tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3"),
                tuple(attr("A"), "a1", attr("B"), "b3", attr("C"), "c2"),
                tuple(attr("A"), "a2", attr("B"), "b1", attr("C"), "c1"),
                tuple(attr("A"), "a3", attr("B"), "b3", attr("C"), "c4"));

        Relation r2 = relation(
                tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1"),
                tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3"),
                tuple(attr("A"), "a2", attr("B"), "b4", attr("C"), "c2"),
                tuple(attr("A"), "a2", attr("B"), "b1", attr("C"), "c1"),
                tuple(attr("A"), "a3", attr("B"), "b1", attr("C"), "c2"));

        r1.setKey(key(attr("A"), attr("C")));
        r2.setKey(key(attr("A"), attr("B")));
        Relation r = r1.minus(r2);
        Key actual = r.getKey();
        Key expected = key(attr("A"), attr("C"));
        assertEquals(expected, actual);
        // test if the header contain the key
        Heading h = r.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.getAttrsKey());
        assertEquals(l,actual.getAttrsKey());
    }

    @Test
    public void testUnionOperatorWithKey(){
        // union with header compatible
        // k1 union k2
        Relation r1 = relation(
                tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1"),
                tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3"),
                tuple(attr("A"), "a1", attr("B"), "b3", attr("C"), "c2"),
                tuple(attr("A"), "a2", attr("B"), "b1", attr("C"), "c1"),
                tuple(attr("A"), "a3", attr("B"), "b3", attr("C"), "c4"));

        Relation r2 = relation(
                tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1"),
                tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3"),
                tuple(attr("A"), "a2", attr("B"), "b4", attr("C"), "c2"),
                tuple(attr("A"), "a2", attr("B"), "b1", attr("C"), "c1"),
                tuple(attr("A"), "a3", attr("B"), "b1", attr("C"), "c2"));

        r1.setKey(key(attr("A"), attr("C")));
        r2.setKey(key(attr("A"), attr("B")));
        Relation r = r1.union(r2);
        Key actual = r.getKey();
        Key expected = key(attr("A"), attr("B"), attr("C"));
        assertEquals(expected, actual);
        // test if the header contain the key
        Heading h = r.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.getAttrsKey());
        assertEquals(l,actual.getAttrsKey());
    }

    @Test
    public void testJoinOperatorWithKey1(){
        // join
        // k1 union k2
        Relation r1 = relation(
                tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1"),
                tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3"),
                tuple(attr("A"), "a1", attr("B"), "b3", attr("C"), "c2"),
                tuple(attr("A"), "a2", attr("B"), "b1", attr("C"), "c1"),
                tuple(attr("A"), "a3", attr("B"), "b3", attr("C"), "c4"));

        Relation r2 = relation(
                tuple(attr("A"), "a1", attr("D"), "d1", attr("E"), "e1"),
                tuple(attr("A"), "a1", attr("D"), "d2", attr("E"), "e3"),
                tuple(attr("A"), "a2", attr("D"), "d4", attr("E"), "e2"),
                tuple(attr("A"), "a2", attr("D"), "d1", attr("E"), "e1"),
                tuple(attr("A"), "a3", attr("D"), "d1", attr("E"), "e2"));

        r1.setKey(key(attr("A"), attr("C")));
        r2.setKey(key(attr("D")));
        Relation r = r1.join(r2);
        Key actual = r.getKey();
        Key expected = key(attr("A"), attr("C"), attr("D"), attr("E"));
        assertEquals(expected, actual);
        // test if the header contain the key
        Heading h = r.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.getAttrsKey());
        assertEquals(l,actual.getAttrsKey());
    }
    @Test
    public void testSelectOperatorWithKey(){
        AttrName LETTER = AttrName.attr("letter");
        Relation re = relation(
                tuple(LETTER, "h"),
                tuple(LETTER, "s"),
                tuple(LETTER, "e"),
                tuple(LETTER, "k")
                );
        SelectionMember letterMember = SelectionMember.fn(
                Type.scalarType(String.class),
                t -> {
                    String name = (String) t.get(NAME);
                    return name.substring(name.length() - 1, name.length());
                });
        Selection sel = Selection.varargs(LETTER, letterMember);
        Relation rs = select(suppliers(), sel);
        Key actual= rs.getKey();
        Key expected = key(LETTER);
        assertEquals(expected, actual);
    }

}
