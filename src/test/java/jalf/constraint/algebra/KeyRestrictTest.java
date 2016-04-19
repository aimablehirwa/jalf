package jalf.constraint.algebra;

import static jalf.DSL.eq;
import static jalf.DSL.key;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.shipments;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jalf.AttrList;
import jalf.Relation;
import jalf.constraint.Key;
import jalf.type.Heading;

public class KeyRestrictTest {
    @Test
    public void testRestrictOperatorWithKey(){
        // restrict does not have impact on the key
        Relation r = shipments();

        Relation rs = r.restrict(eq(SID, "S1"));
        Key actual =rs.getKeys().toList().get(0);
        Key expected = key(SID, PID);
        assertEquals(expected, actual);
        // test if the header contain the key
        Heading h = rs.getType().getHeading();
        AttrList l =  h.toAttrList().intersect(actual.toAttrList());
        assertEquals(l,actual.toAttrList());

    }

}
