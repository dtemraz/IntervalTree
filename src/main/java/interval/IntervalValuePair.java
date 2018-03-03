package interval;

/**
 * This interface represents key value pair, where key is the interval of generic endpoints and value is a generic type.
 * This class could be used in {@link IntervalTree} for methods that should return both key and associated value.
 *
 * @param <K> comparable endpoint
 * @param <V> type of value
 *
 * @author dtemraz
 */
public interface IntervalValuePair<K extends Comparable<? super K>, V> {
    
    /**
     * Returns interval from this key-value pair.
     * 
     * @return interval from this key-value pair
     */
    Interval<K> getInterval();
    
    /**
     * Returns value associated with the interval.
     * 
     * @return value associated with the interval
     */
    V getValue();
}
