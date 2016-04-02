package jalf.relation.algebra;

import jalf.AttrList;
import jalf.Relation;
import jalf.Visitor;
import jalf.constraint.Key;
import jalf.type.RelationType;

import java.util.Arrays;
import java.util.List;

/**
 * Relational natural join based on same name attributes.
 */
public class Join extends BinaryOperator {

    private final Relation left;

    private final Relation right;

    private final RelationType type;

    private final Key key;

    public Join(RelationType type, Relation left, Relation right) {
        this.left = left;
        this.right = right;
        this.type = type;
        this.key = keyCheck();
    }

    public Join(Relation left, Relation right){
        this.left = left;
        this.right = right;
        this.type = typeCheck();
        this.key = keyCheck();
    }

    @Override
    public RelationType getType() {
        return type;
    }

    @Override
    public Relation getLeft() {
        return left;
    }

    @Override
    public Relation getRight() {
        return right;
    }

    /**
     * Return the attributes on which the join operates. As Join is a natural
     * join, these are the attributes common to right and left operands.
     *
     * @return the common attributes as an AttrList instance.
     */
    public AttrList getJoinAttrList() {
        AttrList leftAttrs = left.getType().toAttrList();
        AttrList rightAttrs = right.getType().toAttrList();
        return leftAttrs.intersect(rightAttrs);
    }

    @Override
    protected RelationType typeCheck() {
        return left.getType().join(right.getType());
    }

    @Override
    public List<Object> getArguments() {
        return Arrays.asList();
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Key getKey() {
        return this.key;
    }

    @Override
    public void setKey(Key key) {
        // TODO Auto-generated method stub
    }

    @Override
    protected Key keyCheck() {
        AttrList l = left.getKey().union(right.getKey());
        return Key.primary(l);
    }

}
