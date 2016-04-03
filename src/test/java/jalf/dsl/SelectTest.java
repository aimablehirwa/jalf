package jalf.dsl;

import static jalf.DSL.relation;
import static jalf.DSL.select;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.NAME;
import static jalf.fixtures.SuppliersAndParts.suppliers;
import static org.junit.Assert.assertEquals;
import jalf.AttrName;
import jalf.Relation;
import jalf.Selection;
import jalf.SelectionMember;
import jalf.Type;

import org.junit.Test;

public class SelectTest {

    @Test
    public void testItWorksAsExpected() {
        AttrName LETTER = AttrName.attr("letter");
        Relation expected = relation(
                tuple(LETTER, "h"),
                tuple(LETTER, "s"),
                tuple(LETTER, "e"),
                tuple(LETTER, "k")
                );
        SelectionMember letterMember = SelectionMember.fn(
                Type.scalarType(String.class),
                t -> {
                    String name = (String) t.get(NAME);
                    return name.substring(name.length() - 1, name.length());
                });
        Selection sel = Selection.varargs(LETTER, letterMember);
        Relation actual = select(suppliers(), sel);
        assertEquals(expected, actual);
    }

}
