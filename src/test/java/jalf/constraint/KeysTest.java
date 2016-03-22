package jalf.constraint;

import static jalf.fixtures.SuppliersAndParts.shipments;
import jalf.Relation;

import org.junit.Test;

public class KeysTest {

    @Test
    public void testGetAllPossibleKeys(){
        // get all possible keys of a relation
        Relation r = shipments();
        Keys expected = keys(key("SID", "PID"), key("SID","PID", "QTY"));
        Keys actual = r.getKeys();
        assertTrue(k1.checkUniqueness());
    }


}
