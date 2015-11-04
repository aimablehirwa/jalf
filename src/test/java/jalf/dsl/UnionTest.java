package jalf.dsl;

import jalf.Relation;

import org.junit.Test;

import static jalf.DSL.*;
import static jalf.fixtures.SuppliersAndParts.*;
import static org.junit.Assert.*;

public class UnionTest {

    @Test
    public void testItWorksAsExpected() {
        Relation left = relation(
                tuple(SID, "S1"),
                tuple(SID, "S2")
        );
        Relation right = relation(
                tuple(SID, "S2"),
                tuple(SID, "S3")
        );
        Relation expected = relation(
                tuple(SID, "S1"),
                tuple(SID, "S2"),
                tuple(SID, "S3")
        );
        Relation actual = union(left, right);
        assertEquals(expected, actual);
    }
    
    
    @Test
    public void testItWorksNotExpected() {
        Relation left = relation(
                tuple(SID, "S1"),
                tuple(SID, "S2")
        );
        Relation right = relation(
                tuple(SID, "S2"),
                tuple(SID, "S3")
        );
        Relation expected = relation(
                tuple(SID, "S1"),
                tuple(SID, "S3")
        );
        Relation actual = union(left, right);
        assertNotEquals(expected, actual);
    }

}
