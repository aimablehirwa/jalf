package jalf.constraint;

import static jalf.util.CollectionUtils.setOf;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Keys implements  Iterable<Constraint>{
    //attention ne peut conntenir qu'une seul primary keys
    private Set<Constraint> constraints;

    public Keys( Set<Constraint> constraints) {
        this.constraints = constraints;
    }

    public Keys(Constraint[] constraints) {
        this(setOf(constraints));
    }


    @Override
    public Iterator<Constraint> iterator() {
        return constraints.iterator();

    }

    @Override
    public int hashCode() {
        return constraints.hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Keys other = (Keys) obj;
        return constraints.equals(other.constraints);
    }



    public Stream<Constraint> stream() {
        return constraints.stream();
    }


    @Override
    public String toString() {
        return "keys("
                + constraints.stream().flatMap(a-> a.getAttrsKey()
                        .stream().map(b -> b.getName()))
                .collect(Collectors.joining(", ")) + ")";

    }

    public List<Constraint> toList() {
        return constraints.stream().collect(Collectors.toList());
    }


}
