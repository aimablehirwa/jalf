package jalf.dsl;

import static jalf.DSL.relation;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.NAME;
import static jalf.fixtures.SuppliersAndParts.SID;
import static org.junit.Assert.assertEquals;
import jalf.Relation;
import jalf.TypeException;
import jalf.type.RelationType;

import org.junit.Test;

public class RelationTest {

    @Test
    public void testItAllowsSpecifyingTheType() {
        Relation r = relation(
                RelationType.dress(SID, String.class),
                tuple(SID, "S1"),
                tuple(SID, "S2")
                );
        assertEquals(2, r.cardinality());
    }
    //
    //
    //    @Test
    //    public void testItAllowsSpecifyingTheTypeNokeyVerifKey() {
    //        // key become the entire header if no key specified
    //        Relation r = relation(
    //                RelationType.dress(SID, String.class, NAME,String.class),
    //                tuple(SID, "S1", NAME, "Smith"),
    //                tuple(SID, "S3", NAME, "Blake")
    //                );
    //        Key k1 = r.getKey();
    //        Key k2 = key(SID,NAME);
    //        Key k3 = key(SID);
    //        assertEquals(k1, k2);
    //    }
    //
    //    @Test
    //    public void testItAllowsSpecifyingTheTypekeyVerifKey() {
    //        //ici on  specifie la clef
    //        Relation r = relation(
    //                RelationType.dress(SID, String.class,NAME,String.class),
    //                key(SID),
    //                tuple(SID, "S1", NAME, "Smith"),
    //                tuple(SID, "S3", NAME, "Blake")
    //                );
    //        Key k1 = r.getKey();
    //        Key k2 = key(SID);
    //        Key k3 = key(SID,NAME);
    //        assertEquals(k1, k2);
    //    }



    @Test
    public void testItAllowsNoTupleProvidedTheTypeIsSpecified() {
        Relation r = relation(
                RelationType.dress(SID, String.class)
                );
        assertEquals(0, r.cardinality());
    }

    @Test(expected = TypeException.class)
    public void testItRaisesWhenNoTupleAndNoType() {
        relation();
    }

    @Test(expected = TypeException.class)
    public void testItRaisesWhenTupleTypeMismatch() {
        relation(
                RelationType.dress(SID, String.class),
                tuple(SID, "S1"),
                tuple(SID, 10)
                );
    }

    @Test(expected = TypeException.class)
    public void testItRaisesWhenTypeInferenceFails() {
        relation(
                tuple(SID, "S1"),
                tuple(NAME, "S2")
                );
    }

}
