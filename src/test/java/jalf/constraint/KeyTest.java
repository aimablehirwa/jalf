package jalf.constraint;

import static jalf.fixtures.SuppliersAndParts.shipments;
import static org.junit.Assert.assertEquals;
import jalf.Relation;

import org.junit.Test;

public class KeyTest {

    @Test
    public void testCheckUniquenessTrue(){
        Relation r = shipments();
        r.setKeys(key("SID", "PID"));
        Key k1 = key("SID", "PID");
        assertTrue(k1.checkUniqueness());
    }

    @Test
    public void testCheckUniquenessFalse(){
        Relation r = shipments();
        r.setKeys(key("SID"));
        Key k1 = key("SID");
        assertTrue(k1.checkUniqueness(r));
    }

    @Test
    public void testIsASubKeyTrue1(){
        Relation r = shipments();
        r.setKeys(key("SID", "PID"));
        Key k1 = key("SID", "PID");

        assertTrue(k1.checkUniqueness());
        assertTrue(k1.isSubKeys(r));
    }

    @Test
    public void testIsASubKeyTrue2(){
        Relation r = shipments();
        r.setKeys(key("SID", "PID"));
        Key k1 = key("SID");

        assertTrue(k1.checkUniqueness());
        assertTrue(k1.isSubKeys(r));
    }

    @Test
    public void testIsASubKeyTrue3(){
        Relation r = shipments();
        Key k1 = key("SID", "PID", "QTY");

        assertTrue(k1.checkUniqueness());
        assertTrue(k1.isSubKeys(r));
    }

    @Test
    public void testIsASubKeyFalse(){
        Relation r = shipments();
        r.setKeys(key("SID", "PID"));
        Key k1 = key("SID", "CID");

        assertTrue(k1.checkUniqueness());
        assertFalse(k1.isSubKeys(r));
    }

    @Test
    public void testIsAKeyOfRel(){
        // check if it is a key of the relation
        Relation r = shipments();
        Key k = key("SID", "PID");
        assertTrue(k1.isAKeyOfRel());
    }

    @Test
    public void testIntersectionOfKeys(){
        Key k1 = key("SID","PID");
        Key k2 = key("SID");

        Key actual = k1.intersect(k2);
        Key expected = key("SID");

        assertEquals(expected, actual);
    }

    @Test
    public void testUnionOfKeys(){
        Key k1 = key("SID","PID");
        Key k2 = key("CID");

        Key actual = k1.union(k2);
        Key expected = key("SID", "PID", "CID");

        assertEquals(expected, actual);
    }

    @Test
    public void testProjectOfKeys(){
        Key k1 = key("SID","PID");
        Key k2 = key("CID");

        Key actual = k1.union(k2);
        Key expected = key("SID", "PID", "CID");

        assertEquals(expected, actual);
    }

    @Test
    public void testRenameKey(){
        Key k1 = key("SID","PID");
        Key k2 = key("CID");

        Key actual = k1.union(k2);
        Key expected = key("SID", "PID", "CID");

        assertEquals(expected, actual);
    }

}
