package jalf.relation.algebra;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import jalf.AttrList;
import jalf.Relation;
import jalf.Visitor;
import jalf.constraint.Key;
import jalf.constraint.Keys;
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
    }

    public Project(Relation operand, AttrList attributes) {
        this.operand = operand;
        this.attributes = attributes;
        this.type = typeCheck();
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
    public Keys lazyComputeKey(){
        Keys keys=operand.getKeys();

        Predicate<Key> P= (k)-> k.intersect(this.attributes).equals(k.toAttrList());
        Set<Key> keyfilter=keys.stream().filter(P).collect(Collectors.toSet());
        if( keyfilter.isEmpty()){
            // no key is good
            return  new Keys(Key.candidate(this.attributes));
        }
        else{
            //on a filtrer les mauvaises clefs
            return new Keys(keyfilter);
        }
    }

}
