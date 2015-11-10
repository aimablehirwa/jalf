package jalf.dsl;

import jalf.Relation;
import jalf.TypeException;

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
    public void testHeaderIncompatile() {
        Relation left = relation(
        	    tuple(PID, "P1"),
        		tuple(PID, "P2")
        );
        Relation right = relation(
                tuple(SID, "S2"),
                tuple(SID, "S3")
        );
        
        try {
            union(left, right);
            assertTrue(false);
        } catch (TypeException ex) {
            assertEquals("Headings must have same attributes", ex.getMessage());
        }       
    }    
   
 }
