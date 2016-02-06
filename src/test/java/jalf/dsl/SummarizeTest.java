package jalf.dsl;
import static jalf.DSL.attr;
import static jalf.DSL.attrs;
import static jalf.DSL.count;
import static jalf.DSL.max;
import static jalf.DSL.relation;
import static jalf.DSL.summarize;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.QTY;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.shipments;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jalf.Relation;
public class SummarizeTest {

    @Test
    public void testItWorksAsExpectedForCount() {

        Relation expected = relation(
                tuple(SID, "S1", attr("count"), 6),
                tuple(SID, "S2", attr("count"), 2),
                tuple(SID, "S3", attr("count"), 1),
                tuple(SID, "S4", attr("count"), 3)
                );

        // summarize takes :
        // a relation,
        // an attribut name (for grouping),
        // an attribut name (for result of aggreation),
        // an aggregation function (TODO)

        Relation actual = summarize(shipments(),attrs(SID), count(),attr("count"));

        // real test
        assertEquals(expected, actual);

    }

    @Test
    public void testItWorksAsExpectedForMax() {

        Relation expected = relation(
                tuple(SID, "S1", attr("MAX_QTY"), 400),
                tuple(SID, "S2", attr("MAX_QTY"), 400),
                tuple(SID, "S3", attr("MAX_QTY"), 200),
                tuple(SID, "S4", attr("MAX_QTY"),400)
                );

        // summarize takes :
        // a relation,
        // an attribut name (for grouping),
        // an attribut name (for result of aggreation),
        // an aggregation function (TODO)

        Relation actual = summarize(shipments(),attrs(SID), max(QTY),attr("MAX_QTY"));

        // real test
        assertEquals(expected, actual);

    }






}
