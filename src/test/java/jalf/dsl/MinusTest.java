package jalf.dsl;

import static jalf.DSL.key;
import static jalf.DSL.minus;
import static jalf.DSL.relation;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.NAME;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.SID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import jalf.Relation;
import jalf.TypeException;


public class MinusTest {

    @Test
    public void testItWorksAsExpected(){
        Relation left = relation(
                tuple(SID, "S1"),
                tuple(SID, "S2"),
                tuple(SID, "S3"),
                tuple(SID, "S4")
                );
        Relation right = relation(
                tuple(SID, "S2"),
                tuple(SID, "S3"),
                tuple(SID, "S5")
                );
        Relation expected = relation(
                tuple(SID, "S1"),
                tuple(SID, "S4")
                );
        Relation actual = minus(left, right);

        // real test
        assertEquals(expected, actual);

    }


    @Test
    public void testItWorksAsExpectedSameKey(){
        Relation left = relation(
                key(SID),
                tuple(SID, "S1",NAME ,"Berth"),
                tuple(SID, "S2",NAME ,"Max"),
                tuple(SID, "S3",NAME ,"Tom"),
                tuple(SID, "S4",NAME ,"Jojo")
                );
        Relation right = relation(
                key(SID),
                tuple(SID, "S2",NAME ,"Max"),
                tuple(SID, "S3",NAME ,"Tom"),
                tuple(SID, "S5",NAME ,"Leny")
                );
        Relation expected = relation(
                key(SID),
                tuple(SID, "S1",NAME ,"Berth"),
                tuple(SID, "S4",NAME ,"Jojo")
                );
        Relation actual = minus(left, right);
        //verif key
        assertEquals(right.getKeys().toList().get(0), left.getKeys().toList().get(0));
        assertEquals(left.getKeys().toList().get(0), actual.getKeys().toList().get(0));
        // real test
        assertEquals(expected, actual);

    }

    @Test
    public void testItWorksAsExpectedNotSameKey(){
        Relation left = relation(
                key(SID),
                tuple(SID, "S1",NAME ,"Berth"),
                tuple(SID, "S2",NAME ,"Max"),
                tuple(SID, "S3",NAME ,"Tom"),
                tuple(SID, "S4",NAME ,"Jojo")
                );
        Relation right = relation(
                tuple(SID, "S2",NAME ,"Max"),
                tuple(SID, "S3",NAME ,"Tom"),
                tuple(SID, "S5",NAME ,"Leny")
                );
        Relation expected = relation(
                key(SID),
                tuple(SID, "S1",NAME ,"Berth"),
                tuple(SID, "S4",NAME ,"Jojo")
                );
        Relation actual = minus(left, right);
        //verif key
        assertNotEquals(right.getKeys().toList().get(0), left.getKeys().toList().get(0));
        assertEquals(left.getKeys().toList().get(0), actual.getKeys().toList().get(0));


        // real test
        assertEquals(expected, actual);

    }

    @Test(expected=TypeException.class)
    public void testHeaderIncompatile() {
        Relation left = relation(
                tuple(PID, "P1"),
                tuple(PID, "P2")
                );
        Relation right = relation(
                tuple(SID, "S2"),
                tuple(SID, "S3")
                );
        minus(left, right);
    }
}
