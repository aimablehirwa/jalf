package jalf.constraint;

import static jalf.util.CollectionUtils.setOf;
import jalf.Renaming;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Keys implements Iterable<Key> {

    private Set<Key> keys;

    public Keys(Set<Key> keys) {
	this.keys = keys;
    }

    public Keys(Key[] keys) {
	this(setOf(keys));
    }

    public Keys(Key key) {
	this(setOf(key));
    }

    public static Keys keys(Key[] keys) {
	return new Keys(keys);
    }

    @Override
    public Iterator<Key> iterator() {
	return keys.iterator();

    }

    @Override
    public int hashCode() {
	return keys.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null || getClass() != obj.getClass())
	    return false;
	Keys other = (Keys) obj;
	return keys.equals(other.keys);
    }

    public Stream<Key> stream() {
	return keys.stream();
    }

    @Override
    public String toString() {
	return "keys("
		+ keys.stream()
			.flatMap(
				a -> a.toAttrList().stream()
					.map(b -> b.getName()))
			.collect(Collectors.joining(", ")) + ")";

    }

    public Keys rename(Renaming renaming) {
	Set<Key> keyrename = this.keys.stream().map(k -> k.rename(renaming))
		.collect(Collectors.toSet());
	return new Keys(keyrename);
    }

    public List<Key> toList() {
	return keys.stream().collect(Collectors.toList());
    }

    public Keys union(Keys other) {
	Set<Key> keyunion = new HashSet<Key>();
	this.stream().forEach(
		(k) -> {
		    Set<Key> newset = other.keys.stream().map(o -> o.union(k))
			    .collect(Collectors.toSet());
		    keyunion.addAll(newset);
		    ;
		});
	return new Keys(keyunion);
    }

}
