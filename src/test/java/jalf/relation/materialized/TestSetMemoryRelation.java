package jalf.relation.materialized;

import jalf.*;
import jalf.type.RelationType;

import org.junit.*;

import static jalf.DSL.*;
import static jalf.util.CollectionUtils.*;
import static jalf.fixtures.SuppliersAndParts.*;
import static org.junit.Assert.*;

public class TestSetMemoryRelation {

    Relation dum = new SetMemoryRelation(
            RelationType.dress(),
            setOf());

    Relation s1 = new SetMemoryRelation(
            RelationType.dress(SID, String.class),
            setOf(tuple(SID, "S1")));

    Relation s1bis = new SetMemoryRelation(
            RelationType.dress(SID, String.class),
            setOf(tuple(SID, "S1")));
    
    Relation s2 = new SetMemoryRelation(
            RelationType.dress(SID, String.class),
            setOf(tuple(SID, "S2")));

    @Test
    public void testEqualsAgainstAnotherOne() {
        assertTrue(s1.equals(s1));
        assertTrue(s1.equals(s1bis));
        assertFalse(s1.equals(s2));
        assertFalse(s1.equals(dum));
        assertFalse(dum.equals(s1));
    }

    @Test
    public void testEqualsAgainstAStream() {
        assertTrue(s1.equals(rename(s1, renaming(SID, SID))));
        assertTrue(s1.equals(rename(s1bis, renaming(SID, SID))));
        assertFalse(s1.equals(rename(s2, renaming(SID, SID))));
        assertFalse(s1.equals(rename(dum, renaming(SID, SID))));
        assertFalse(dum.equals(rename(s1, renaming(SID, SID))));
    }

}
