package jalf.relation.algebra;

import jalf.AttrList;
import jalf.Relation;
import jalf.Visitor;
import jalf.constraint.Key;
import jalf.type.RelationType;

import java.util.Arrays;
import java.util.List;

public class Minus extends BinaryOperator{
    private final Relation left;

    private final Relation right;

    private final RelationType type;

    private final Key key;

    public Minus(RelationType type, Relation left, Relation right) {
        this.left = left;
        this.right = right;
        this.type = type;
        this.key = keyCheck();
    }

    public Minus(Relation left, Relation right){
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
     * Return the attributes on which the union operates.
     * @return left attributes as an AttrList instance.
     */
    public AttrList getMinusAttrList() {
        AttrList leftAttrs = left.getType().toAttrList();
        return leftAttrs;
    }

    @Override
    protected RelationType typeCheck() {
        return left.getType().minus(right.getType());
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
        // TODO Auto-generated method stub
        return this.key;
    }

    @Override
    public void setKey(Key key) {
        // TODO Auto-generated method stub

    }

    @Override
    protected Key keyCheck() {
        return this.left.getKey();
    }
}
