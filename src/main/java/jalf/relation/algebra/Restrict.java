package jalf.relation.algebra;

import java.util.Arrays;
import java.util.List;

import jalf.Predicate;
import jalf.Relation;
import jalf.Visitor;
import jalf.constraint.Key;
import jalf.type.RelationType;

/**
 * Relational restrict.
 */
public class Restrict extends UnaryOperator {

    private final Relation operand;

    private final Predicate predicate;

    private RelationType type;


    public Restrict(Relation operand, Predicate predicate, RelationType type) {
        this.operand = operand;
        this.predicate = predicate;
        this.type = type;
        this.setKey(keyCheck());
    }

    public Restrict(Relation operand, Predicate predicate) {
        this.operand = operand;
        this.predicate = predicate;
        this.type = typeCheck();
        this.setKey(keyCheck());
    }

    @Override
    public RelationType getType() {
        return type;
    }

    @Override
    protected RelationType typeCheck() {
        return operand.getType().restrict(predicate);
    }

    @Override
    public Relation getOperand() {
        return operand;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    @Override
    public List<Object> getArguments() {
        return Arrays.asList(predicate);
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }

    /*
     * no change for the key
     * (non-Javadoc)
     * @see jalf.relation.algebra.Operator#keyCheck()
     */
    @Override
    protected Key keyCheck() {
        return this.operand.getKey();
    }

}
