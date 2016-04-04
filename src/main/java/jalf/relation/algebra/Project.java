package jalf.relation.algebra;

import java.util.Arrays;
import java.util.List;

import jalf.AttrList;
import jalf.Relation;
import jalf.Visitor;
import jalf.constraint.Key;
import jalf.type.RelationType;

/**
 * Relational projection.
 */
public class Project extends UnaryOperator {

    private final Relation operand;

    private final AttrList attributes;

    private final RelationType type;



    public Project(Relation operand, AttrList attributes, RelationType type) {
        this.operand = operand;
        this.attributes = attributes;
        this.type = type;
        this.setKey(keyCheck());
    }

    public Project(Relation operand, AttrList attributes) {
        this.operand = operand;
        this.attributes = attributes;
        this.type = typeCheck();
        this.setKey(keyCheck());
    }

    @Override
    public RelationType getType() {
        return type;
    }

    @Override
    protected RelationType typeCheck() {
        return operand.getType().project(attributes);
    }

    @Override
    public Relation getOperand() {
        return operand;
    }

    public AttrList getAttributes() {
        return attributes;
    }

    @Override
    public List<Object> getArguments() {
        return Arrays.asList(attributes);
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }


    /**
     * 4 case to check
     * - Ps intersect Kx = Kx : Kn = Kx
     * - Ps intersect Kx = Ps : Kn = on
     * - Ps intersect Kx = null : Kn = on
     * - Ps intersect Kx = sx : Kn = on
     */
    @Override
    public Key keyCheck(){
        AttrList l = operand.getKey().getAttrsKey().intersect(this.attributes);
        if (l.equals(operand.getKey().getAttrsKey())){
            return operand.getKey();
        }else{
            return Key.primary(this.attributes);
        }
    }

}
