package jalf.constraint;

import static jalf.DSL.attr;
import static jalf.DSL.key;
import static jalf.DSL.keys;
import static jalf.DSL.renaming;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.QTY;
import static jalf.fixtures.SuppliersAndParts.SID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import jalf.Renaming;

public class KeysTest {


    @Test
    public void testHashCode() {
        Keys k1 = keys(key(SID, PID), key(SID,PID, QTY));
        Keys k2 =keys(key(SID, PID), key(SID,PID, QTY));
        Keys k3 = keys(key(PID, SID), key(SID,PID, QTY));
        Keys k4 =keys(key(PID, QTY));
        assertEquals(k1.hashCode(), k2.hashCode());
        assertEquals(k1.hashCode(), k3.hashCode());
        assertNotEquals(k1.hashCode(), k4.hashCode());
    }


    @Test
    public void testEquals() {
        Keys k1 = keys(key(SID, PID), key(SID,PID, QTY));
        Keys k2 = keys(key(PID, SID), key(SID,PID, QTY));
        Keys k3 =keys(key(PID, QTY));
        assertEquals(k1, k1);
        assertEquals(k1, k2);
        assertNotEquals(k1, k3);
    }

    @Test
    public void testUnion() {
        Keys k1 = keys(key(SID, PID), key(SID,PID, QTY));
        Keys k2 =keys(key(PID, QTY));
        Keys actual=k1.union(k2);
        Keys expected=keys(key(SID,PID, QTY));
        assertEquals(expected, actual);
    }
    @Test
    public void testRename(){
        Renaming rn = renaming(QTY, attr("QTY_Max"));
        Keys k1 = keys(key(SID, PID), key(SID,PID, QTY));
        Keys actual=k1.rename(rn);
        Keys expected=keys(key(SID, PID), key(SID,PID, attr("QTY_Max")));
        assertEquals(expected, actual);
    }

}
