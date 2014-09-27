package jalf;

import jalf.predicate.*;
import jalf.relation.materialized.SetMemoryRelation;
import jalf.type.Heading;
import jalf.type.RelationType;

import java.util.stream.Stream;

/**
 * @author amirm
 */
public class DSL {

    // AttrName

    public static AttrName attr(String attr) {
        return AttrName.attr(attr);
    }

    // AttrList

    public static AttrList attrs(AttrName... attrNames) {
        return AttrList.attrs(attrNames);
    }

    public static AttrList attrs(String... attrNames) {
        return AttrList.attrs(attrNames);
    }

    // Heading and Types

    public static Heading heading(Object... pairs) {
        return Heading.varargs(pairs);
    }

    // Renaming

    public static Renaming renaming(AttrName... namePairs) {
        return Renaming.extension(namePairs);
    }

    public static Renaming renaming(String... namePairs) {
        return Renaming.extension(namePairs);
    }

    // Predicate

    public static Predicate among(AttrName val, Iterable<?> vals) {
        return new Among(val, vals);
    }

    public static Predicate eq(Object left, Object right) {
        return Predicate.eq(left, right);
    }

    public static Predicate neq(Object left, Object right) {
        return Predicate.neq(left, right);
    }

    public static Predicate gt(Comparable<?> left, Comparable<?> right) {
        return Predicate.gt(left, right);
    }

    public static Predicate gte(Comparable<?> left, Comparable<?> right) {
        return Predicate.gte(left, right);
    }

    public static Predicate lt(Comparable<?> left, Comparable<?> right) {
        return Predicate.lt(left, right);
    }

    public static Predicate lte(Comparable<?> left, Comparable<?> right) {
        return Predicate.lte(left, right);
    }

    // Tuple

    public static Tuple tuple(Object... keyValuePairs) {
        return Tuple.varargs(keyValuePairs);
    }

    // Relation

    public static Relation relation(RelationType type, Tuple... tuples) {
        return SetMemoryRelation.tuples(type, tuples);
    }

    public static Relation relation(Heading heading, Tuple... tuples) {
        return relation(RelationType.heading(heading), tuples);
    }

    public static Relation relation(Tuple... tuples) {
        return relation(RelationType.infer(Stream.of(tuples)), tuples);
    }

    // Relational algebra

    public static Relation project(Relation relation, AttrList attrNames) {
        return relation.project(attrNames);
    }

    public static Relation rename(Relation relation, Renaming renaming) {
        return relation.rename(renaming);
    }

    public static Relation restrict(Relation relation, Predicate predicate) {
        return relation.restrict(predicate);
    }

    public static Relation restrict(Relation relation, java.util.function.Predicate<Tuple> fn) {
        return restrict(relation, Predicate.java(fn));
    }

    public static Relation join(Relation left, Relation right) {
        return left.join(right);
    }
}
