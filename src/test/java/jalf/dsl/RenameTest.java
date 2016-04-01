package jalf.dsl;

import static jalf.DSL.attr;
import static jalf.DSL.relation;
import static jalf.DSL.rename;
import static jalf.DSL.renaming;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.CITY;
import static jalf.fixtures.SuppliersAndParts.NAME;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.STATUS;
import static jalf.fixtures.SuppliersAndParts.SUPPLIER_ID;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jalf.AttrName;
import jalf.Relation;
import jalf.Renaming;

public class RenameTest {

    Relation SOURCE = relation(
            tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London")
            );

    @Test
    public void testItSupportsAnExplicitRenaming() {
        Relation expected = relation(
                tuple(SUPPLIER_ID, "S1", NAME, "Smith", STATUS, 20, CITY, "London")
                );
        Relation actual = rename(SOURCE, renaming(SID, SUPPLIER_ID));
        System.out.println("hhh"+actual.getType().toAttrList());
        System.out.println("kkkk"+actual.getKey().getAttrsKey());
        assertEquals(expected, actual);
    }

    @Test
    public void testItSupportsPrefixRenaming() {
        AttrName S_SID = attr("s_sid");
        AttrName S_NAME = attr("s_name");
        AttrName S_STATUS = attr("s_status");
        AttrName S_CITY = attr("s_city");
        Relation expected = relation(
                tuple(S_SID, "S1", S_NAME, "Smith", S_STATUS, 20, S_CITY, "London")
                );
        Relation actual = rename(SOURCE, Renaming.prefix("s_"));
        assertEquals(expected, actual);
    }

    @Test
    public void testItSupportsSuffixRenaming() {
        AttrName SID_S = attr("sid_s");
        AttrName NAME_S = attr("name_s");
        AttrName STATUS_S = attr("status_s");
        AttrName CITY_S = attr("city_s");
        Relation expected = relation(
                tuple(SID_S, "S1", NAME_S, "Smith", STATUS_S, 20, CITY_S, "London")
                );
        Relation actual = rename(SOURCE, Renaming.suffix("_s"));
        assertEquals(expected, actual);
    }

}
