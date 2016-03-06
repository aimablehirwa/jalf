package jalf.optimizer;

import static jalf.DSL.attr;
import static jalf.DSL.attrs;
import static jalf.DSL.count;
import static jalf.DSL.eq;
import static jalf.DSL.max;
import static jalf.DSL.summarize;
import static jalf.fixtures.SuppliersAndParts.NAME;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.QTY;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.shipments;

import org.junit.Test;

import jalf.Relation;
import jalf.TypeException;

public class TestOptimizedSummarize  extends OptimizerTest {

    private Relation rel = shipments();

    // projet optimization
    @Test
    public void testProject() {

        // normal execution
        Relation operator = summarize(rel,attrs(SID, PID),count(),attr("count"));
        Relation optimized = optimized(operator).project(attrs(SID, attr("count")));
        Relation expected = summarize(rel, attrs(SID, PID), count(), attr("count")).project(attrs(SID, attr("count")));
        assertSameExpression(expected, optimized);

    }

    // projet optimization
    // no As in project
    // pas de count dans project on oublie summarize
    @Test
    public void testProjectnoAs() {

        Relation operator = summarize(rel,attrs(SID, PID),count(),attr("count"));
        Relation optimized = optimized(operator).project(attrs(SID));
        Relation expected = rel.project(attrs(SID));
        assertSameExpression(expected, optimized);

    }

    @Test(expected=TypeException.class)
    public void testProject1() {

        // there is an intersect between by and on but on is not included in by
        Relation operator = summarize(rel,attrs(SID, PID), max(QTY),attr("count"));
        optimized(operator).project(attrs(NAME, attr("count")));

    }

    @Test(expected=TypeException.class)
    public void testProject2() {

        // by and on are the same
        Relation operator = summarize(rel,attrs(SID, PID),count(),attr("count"));
        optimized(operator).project(attrs(NAME, QTY));

    }

    @Test
    public void testProject3() {

        // by and on are the same
        Relation operator = summarize(rel, attrs(SID, PID),count(),attr("count"));
        Relation optimized = optimized(operator).project(attrs(SID, PID, attr("count")));
        Relation expected = summarize(rel, attrs(SID, PID), count(), attr("count")).project(attrs(SID, PID, attr("count")));
        assertSameExpression(expected, optimized);

    }

    // restrict optimization

    @Test
    public void testRestrict(){

        // restrict not on as
        Relation operator =  summarize(rel,attrs(SID, PID), count(), attr("count"));
        Relation optimized = optimized(operator).restrict(eq(SID, "S1"));

        Relation expected = rel
                .restrict(eq(SID, "S1"))
                .summarize(attrs(SID, PID), count(), attr("count"));

        assertSameExpression(expected, optimized);
    }

    @Test
    public void testRestrictPredMax(){
        // restrict  on as
        Relation operator = summarize(rel,attrs(SID), max(PID),attr("MAX_PID"));
        Relation optimized = optimized(operator).restrict(eq(attr("MAX_PID"),"P6"));

        Relation expected = rel
                .summarize(attrs(SID), max(PID),attr("MAX_PID"))
                .restrict(eq(attr("MAX_PID"),"P6"));

        assertSameExpression(expected, optimized);
    }

    @Test(expected=TypeException.class)
    public void testRestrict1(){

        // restrict attr is not in the header
        Relation operator =  summarize(rel,attrs(SID, PID), count(), attr("count"));
        optimized(operator).restrict(eq(NAME, "Jack"));
    }

}
