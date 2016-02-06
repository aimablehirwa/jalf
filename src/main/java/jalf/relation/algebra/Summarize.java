package jalf.relation.algebra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jalf.AttrList;
import jalf.AttrName;
import jalf.Relation;
import jalf.Tuple;
import jalf.Visitor;
import jalf.aggregator.Aggregator;
import jalf.aggregator.Count;
import jalf.aggregator.Max;
import jalf.type.RelationType;
import jalf.type.TupleType;

public class Summarize extends UnaryOperator {

    private final Relation operand;

    private final AttrList by;

    private final RelationType type;

    private final Aggregator aggregator;

    private final AttrName as;


    public Summarize(Relation operand, AttrList by, RelationType type, Aggregator agg,AttrName as) {
        this.operand = operand;
        this.by = by;
        this.aggregator = agg;
        this.as=as;
        this.type = type;
    }

    public Summarize(Relation operand, AttrList by,  Aggregator agg, AttrName as) {
        this.operand = operand;
        this.by = by;
        this.aggregator = agg;
        this.as=as;
        this.type = typeCheck();
        System.out.println(this.aggregator.getClass());
    }

    public List<Tuple> test(Stream<Tuple> tuples, AttrList byNameAttrs,TupleType tt,AttrName as) {


        List<Tuple> list = new ArrayList<Tuple>();
        Map<List<Object>, Aggregator> map=null;;


        if (this.aggregator instanceof Count){
            map = tuples.collect(Collectors.groupingBy(t -> t.fetch(byNameAttrs),
                    Collector.of(Count::new, Aggregator::updateState, Aggregator::finishState)));


        }

        if (this.aggregator instanceof Max){
            map = tuples.collect(Collectors.groupingBy(t -> t.fetch(byNameAttrs),
                    Collector.of(() -> new Max(this.aggregator.getAggregatedField()), Aggregator::updateState, Aggregator::finishState)));


        }


        for (Entry<List<Object>,Aggregator> item : map.entrySet()) {

            list.add(Tuple.dress(computeKeyValuePairOfTuple(this.as.getName(),item, tuples, byNameAttrs)));
        }
        System.out.println(list);
        return list;
    }




    /**
     * Compute the key value pair list to form a tuple
     *
     * @param item
     * @param tuples
     * @param byNameAttrs
     * @return
     */
    Object[] computeKeyValuePairOfTuple(String newFieldName, Entry<List<Object>,Aggregator> item, Stream<Tuple> tuples, AttrList byNameAttrs){
        List<Object> list = new ArrayList<Object>();

        List<AttrName> attrs = byNameAttrs.toList();


        list.add(attrs.get(0).getName());
        list.add(item.getKey().get(0));
        Aggregator value = item.getValue();
        list.add(AttrName.attr(newFieldName));
        list.add(value.getState());

        return list.stream().toArray();
    }


    public List<Tuple> apply(Stream<Tuple> tuples, AttrList  byNameAttrs, TupleType tt, AttrName as){
        return test(tuples, byNameAttrs,tt, as);
    }



    @Override
    public RelationType getType() {
        return type;
    }

    @Override
    protected RelationType typeCheck() {
        return operand.getType().summarize(by,aggregator,as);
    }

    @Override
    public Relation getOperand() {
        return operand;
    }

    public AttrList getBy() {
        return by;
    }
    public AttrName getAs() {
        return as;
    }

    public Aggregator getAggregator() {
        return aggregator;
    }


    @Override
    public List<Object> getArguments() {
        return Arrays.asList(by);
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }


}
