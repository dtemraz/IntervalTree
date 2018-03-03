package interval;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * This class wraps {@link IntervalTree} and provides key only view of the tree. The class supports all operations found
 * in the tree except {@link IntervalTree#find(Interval)} which is replaced with {@link #contains(Interval)}.
 * The class offers same method complexities as the underlying tree.
 * 
 * @author dtemraz
 *
 * @param <K> type of endpoint in the interval
 */
public class IntervalSet<K extends Comparable<? super K>> implements Iterable<Interval<K>> {

    private static final String EMPTY_VALUE = ""; // IntervalTree does not allow null values, use empty string for all keys
    
    private final IntervalTree<K, String> tree;
    
    public IntervalSet() {
        this(new IntervalTree<>());
    }
    
    IntervalSet(IntervalTree<K, String> intervalTree) {
        tree = intervalTree;
    }
    
    /**
     * Adds <em>interval</em> to this set, overriding duplicate keys. Returns <em>true</em> if the interval was not present
     * in this set, false otherwise.
     * 
     * @param interval to add in the set
     * @return <em>true</em> if the key was not already in the set, <em>false</em> otherwise
     * @throws IllegalArgumentException if interval is null
     */
    public boolean put(Interval<K> interval) {
        return tree.put(interval, EMPTY_VALUE);
    }
    
    /**
     * Adds all intervals to Interval Set. Overrides duplicate keys..
     * 
     * @param intervals to add into Interval Set
     * @throws IllegalArgumentException if intervals are null
     */
    public void putAll(List<Interval<K>> intervals) {
        if (intervals == null) {
            throw new IllegalArgumentException("intervals must not be null");
        }
        intervals.forEach(interval -> tree.put(interval, EMPTY_VALUE));
    }
    
    /**
     * Returns true if this set contains interval, false otherwise.
     * 
     * @param interval key to check
     * @return true if this set contains interval, false otherwise
     * @throws IllegalArgumentException if interval is null
     */
    public boolean contains(Interval<K> interval) {
        return tree.find(interval).isPresent();
    }
    
    /**
     * Returns first found overlapping interval if there are any overlaps, empty optional otherwise.
     *
     * @param interval key for which to find overlapping interval
     * @return Optional overlapping interval if it exists, empty Optional otherwise
     * @throws IllegalArgumentException if interval is null
     */
    public Optional<Interval<K>> findAnyOverlap(Interval<K> interval) {
        return tree.findAnyOverlap(interval).map(IntervalValuePair::getInterval);
    }
    
    /**
     * Returns all overlapping intervals for the interval if there are any overlaps, empty list otherwise.
     * 
     * @param interval key for which to find overlapping interval
     * @return List of overlapping intervals
     * @throws IllegalArgumentException if interval is null
     */
    public List<Interval<K>> findAllOverlaps(Interval<K> interval) {
        return tree.findAllOverlaps(interval).stream().map(IntervalValuePair::getInterval).collect(Collectors.toList());
    }
    
    /**
     * Returns number of intervals in this set.
     * 
     * @return number of intervals in this set
     */
    public int size() {
        return tree.size();
    }
    
    /**
     * Returns <em>iterator</em> over all intervals in this set.
     * 
     * @return <em>iterator</em> over all intervals in this set
     */
    @Override
    public Iterator<Interval<K>> iterator() {
        return StreamSupport.stream(tree.spliterator(), false)
                            .map(IntervalValuePair::getInterval)
                            .collect(Collectors.toList()).iterator();
    }

}
