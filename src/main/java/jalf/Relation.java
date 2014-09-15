package jalf;

import java.util.stream.Stream;

/**
 * A relation, aka a typed set of typed tuples/facts plus algebra.
 *
 * This interface is the main interface over all relation kinds in Jalf. Some
 * relations are represented as in-memory sets of tuples, while others may
 * denote data in files, or in relational/SQL databases; still others denote
 * the result of evaluating algebra expressions, and so on. JAlf guarantees
 * that all those relations can be used together in relational expressions.
 * Algebraic expressions are evaluated lazily when a terminal method is
 * invoked, in a similar way to Java's streams.
 *
 * This interface captures the public interface on JAlf's relations and their
 * algebra. It is not intended to be implemented directly, please subclass
 * `AbstractRelation` instead. The latter provides the protected API that is
 * needed for JAlf' compiler infrastructure to work.
 */
public interface Relation {

    /**
     * Projects this relation on a subset of its attributes.
     *
     * @pre attributes must be a subset of relation's attribute names.
     * @param attributes the list of attributes to project on.
     * @return the resulting relation.
     */
    Relation project(AttrList attributes);

    /**
     * Rename some attributes of this relation.
     *
     * @pre source attribute names must belong to relation's attributes. target
     * attributes names must not.
     * @param renaming a map of source -> target attribute renamings.
     * @return the resulting relation.
     */
    Relation rename(Renaming renaming);

    /**
     * Returns a stream over this relation's tuples.
     *
     * @return a stream of tuples.
     */
    public Stream<Tuple> stream();

    /**
     * Returns the size of this relation, as its number of tuples.
     *
     * This method is a terminal operation. Note that its actual complexity/cost
     * depends on the kind of relation you are facing. For instance, an entire
     * stream of tuples may be consumed for the size of a complex relational
     * expression to be computed in practice.
     *
     * @return the size of the relation.
     */
    public long count();

}
