package jalf.constraint;

import static jalf.DSL.attr;
import static jalf.DSL.attrs;
import static jalf.DSL.eq;
import static jalf.DSL.relation;
import static jalf.DSL.renaming;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.QTY;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.shipments;

import org.junit.Test;

import jalf.Relation;
import jalf.Renaming;

public class KeyTest {

    // test related to key constraints

    @Test
    public void testCheckUniquenessTrue(){
        // test if there is only one tuple that have the key
        Relation r = shipments();
        // r.setKeys(key(SID, PID));
        //Key k1 = key(SID, PID);
        //assertTrue(k1.checkUniqueness());
    }

    @Test
    public void testCheckUniquenessFalse(){
        Relation r = shipments();
        // r.setKeys(key(SID));
        // Key k1 = key(SID);
        //assertFalse(k1.checkUniqueness(r));
    }

    // test related to key itself

    @Test
    public void testIsASubKeyTrue1(){
        Relation r = shipments();
        // r.setKeys(key(SID, PID));
        // Key k1 = key(SID, PID);

        // assertTrue(k1.checkUniqueness());
        //assertTrue(k1.isSubKeys(r));
    }

    @Test
    public void testIsASubKeyTrue2(){
        Relation r = shipments();
        // r.setKeys(key(SID, PID));
        //Key k1 = key(SID);

        //assertTrue(k1.checkUniqueness(r));
        //assertTrue(k1.isSubKeys(r));
    }

    @Test
    public void testIsASubKeyTrue3(){
        Relation r = shipments();
        // Key k1 = key(SID, PID, QTY);

        //assertTrue(k1.checkUniqueness(r));
        // assertTrue(k1.isSubKeys(r));
    }

    @Test
    public void testIsASubKeyFalse(){
        Relation r = shipments();
        // r.setKeys(key(SID, PID));
        // Key k1 = key(SID, attr("cid"));

        // assertFalse(k1.isSubKeys(r));
    }

    @Test
    public void testIsAKeyOfRel(){
        // check if it is a key of the relation
        Relation r = shipments();
        // Key k = key(SID, PID);
        // assertTrue(k1.isAKeyOfRel());
    }

    @Test
    public void testIntersectionOfKeys(){
        // Key k1 = key(SID, PID);
        //Key k2 = key(SID);

        //Key actual = k1.intersect(k2);
        // Key expected = key(SID);

        // assertEquals(expected, actual);
    }

    @Test
    public void testUnionOfKeys(){
        // Key k1 = key(SID, PID);
        // Key k2 = key(attr("cid"));

        // Key actual = k1.union(k2);
        // Key expected = key(SID, PID, attr("cid"));

        // assertEquals(expected, actual);
    }

    @Test
    public void testRenameKey1(){
        // Key k1 = key(SID,PID);
        Renaming rn = renaming(SID, attr("RS"), PID, attr("RP"));

        //Key actual = k1.rename(rn);
        // Key expected = key(attr("RS"), attr("RP"));

        // assertEquals(expected, actual);
    }

    @Test
    public void testRenameKey2(){
        // Key k1 = key(SID,PID);
        // Renaming rn = renaming(SID, attr("RS"), QTY, attr("RQ"));

        // Key actual = k1.rename(rn);
        // Key expected = key(attr("RS"), PID);

        //assertEquals(expected, actual);
    }

    @Test
    public void testRenameKey3(){
        // test renaming if there is no intersection
        // between the key and the renaming attributes
        // Key k1 = key(SID,PID);
        // Renaming rn = renaming(SID, attr("RS"), QTY, attr("RQ"));

        //Key actual = k1.rename(rn);
        //Key expected = key(attr("RS"), PID);

        // assertEquals(expected, actual);
    }

    // test related to each operator

    @Test
    public void testProjectOperatorWithKey1(){
        // test Ps intersect Kx = null
        // the key of the projection must be the header of the projection
        Relation r = shipments();
        // r.setKeys(key(SID, PID));
        // r.project(attrs(QTY));
        //Keys actual = r.getKeys();
        //Keys expected = keys(key(QTY));
        // assertEquals(expected, actual);
    }

    @Test
    public void testProjectOperatorWithKey2(){
        // test Ps intersect Kx = Ps
        // the new key must be the intersect of the key and the projected attributes
        Relation r = shipments();
        // r.setKeys(key(SID, PID));
        r.project(attrs(SID, QTY));
        //Keys actual = r.getKeys();
        // Keys expected = keys(key(SID));
        //assertEquals(expected, actual);
    }

    @Test
    public void testProjectOperatorWithKey3(){
        // test Ps intersect Ks = Ks
        // the key and the projected attributes are the same,
        // so the key of the projection is the key of r
        Relation r = shipments();
        //r.setKeys(key(SID, PID));
        // r.project(attrs(SID, PID));
        //Keys actual = r.getKeys();
        //Keys expected = keys(key(SID, PID));
        // assertEquals(expected, actual);
    }

    @Test
    public void testRenameOperatorWithKey1(){
        // rename the entire key
        // k1 rename
        Relation r = shipments();
        //r.setKeys(key(SID, PID));
        //r.rename(renaming(attr(SID, attr("RS"), PID, attr("RP"))));
        // Keys actual = r.getKeys();
        //Keys expected = keys(key(attr("RS"), attr("RP")));
        //assertEquals(expected, actual);
    }

    @Test
    public void testRenameOperatorWithKey2(){
        // rename some part of the key
        // k1 rename
        Relation r = shipments();
        //r.setKeys(key(SID, PID));
        // r.rename(renaming(attr(SID, attr("RS"), QTY, attr("RQ"))));
        // Keys actual = r.getKeys();
        // Keys expected = keys(key(attr("RS"), PID));
        // assertEquals(expected, actual);
    }

    @Test
    public void testRestrictOperatorWithKey(){
        // restrict does not have impact on the key
        Relation r = shipments();
        // r.setKeys(key(SID, PID));
        r.restrict(eq(SID, "S1"));
        // Keys actual = r.getKeys();
        // Keys expected = keys(key(SID, PID));
        // assertEquals(expected, actual);
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

        // r1.setKeys(key(attr("A"), attr("C")));
        // r2.setKeys(key(attr("A"), attr("B")));
        Relation r = r1.intersect(r2);
        // Keys actual = r.getKeys();
        // Keys expected = keys(key(attr("A"), attr("B"), attr("C")));
        // assertEquals(expected, actual);
    }

    @Test
    public void testMinusOperatorWithKey(){
        // minus with header compatible
        // k2
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

        // r1.setKeys(key(attr("A"), attr("C")));
        //r2.setKeys(key(attr("A"), attr("B")));
        // Relation r = r1.minus(r2);
        // Keys actual = r.getKeys();
        // Keys expected = keys(key(attr("A"), attr("C")));
        // assertEquals(expected, actual);
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

        // r1.setKeys(key(attr("A"), attr("C")));
        //r2.setKeys(key(attr("A"), attr("B")));
        //Relation r = r1.union(r2);
        //Keys actual = r.getKeys();
        //Keys expected = keys(key(attr("A"), attr("B"), attr("C")));
        // assertEquals(expected, actual);
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

        //r1.setKeys(key(attr("A"), attr("C")));
        //r2.setKeys(key(attr("D")));
        // Relation r = r1.join(r2);
        //Keys actual = r.getKeys();
        // Keys expected = keys(key(attr("A"), attr("C"), attr("D")));
        // assertEquals(expected, actual);
    }

    @Test
    public void testJoinOperatorWithKey2(){
        // join with no joining attribute value
        // the result key must be empty
        Relation r1 = relation(
                tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1"),
                tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3"),
                tuple(attr("A"), "a1", attr("B"), "b3", attr("C"), "c2"),
                tuple(attr("A"), "a2", attr("B"), "b1", attr("C"), "c1"),
                tuple(attr("A"), "a3", attr("B"), "b3", attr("C"), "c4"));

        Relation r2 = relation(
                tuple(attr("A"), "a4", attr("D"), "d1", attr("E"), "e1"),
                tuple(attr("A"), "a4", attr("D"), "d2", attr("E"), "e3"),
                tuple(attr("A"), "a5", attr("D"), "d4", attr("E"), "e2"),
                tuple(attr("A"), "a6", attr("D"), "d1", attr("E"), "e1"),
                tuple(attr("A"), "a7", attr("D"), "d1", attr("E"), "e2"));

        // r1.setKeys(key(attr("A"), attr("C")));
        //r2.setKeys(key(attr("D")));
        // Relation r = r1.join(r2);
        // Keys actual = r.getKeys();
        //  Keys expected = keys(key());
        // assertEquals(expected, actual);
    }





}
