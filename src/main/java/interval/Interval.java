package interval;

import java.util.Objects;

/**
 * This class represents one dimensional interval of generic {@link Comparable} endpoints. When referring to endpoints,
 * the term <em>left</em> and <em>from</em> is used interchangeably, likewise <em>right</em> and <em>to</em>.
 *
 * @author dtemraz
 *
 * @param <T> type of interval endpoints
 */
public class Interval<T extends Comparable<? super T>> implements Comparable<Interval<T>> {

    private final T from;
    private final T to;

    /**
     * Constructs new interval instance between <em>from</em> and <em>to</em>, throwing illegal argument exception if <em>from</em>
     * is smaller than <em>to</em>.
     * @param from left interval endpoint
     * @param to right interval endpoint
     * @throws IllegalArgumentException if <em>from</em>  is smaller than <em>to</em>
     */
    public Interval(T from, T to) {
        this.from = Objects.requireNonNull(from);
        this.to = Objects.requireNonNull(to);
        if (from.compareTo(to) > 0) {
            throw new IllegalArgumentException(String.format("from cannot be smaller than to, from: %s, to: %s", from, to));
        }
    }

    /**
     * Returns <em>smaller</em> endpoint of this interval.
     *
     * @return <em>smaller</em> endpoint of this interval
     */
    public T getFrom() {
        return from;
    }

    /**
     * Returns <em>greater</em> endpoint of this interval.
     *
     * @return <em>greater</em> endpoint of this interval
     */
    public T getTo() {
        return to;
    }
    
    /**
     * Returns true if the <em>other</em>interval overlaps with this interval, false otherwise.
     * 
     * @param other interval to check for overlap with this interval
     * @return true if the <em>other</em>interval overlaps with this interval, false otherwise
     * @throws IllegalArgumentException if <em>other</em> is null
     */
    public boolean overlaps(Interval<T> other) {
        if (other == null) {
            throw new IllegalArgumentException("other must not be null");
        }
        return from.compareTo(other.to) <= 0 && other.from.compareTo(to) <= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interval)) return false;

        Interval<?> interval = (Interval<?>) o;

        if (from != null ? !from.equals(interval.from) : interval.from != null) return false;
        return to != null ? to.equals(interval.to) : interval.to == null;
    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Interval<T> other) {
        if (other == null) {
            throw new IllegalArgumentException("other must not be null");
        }
        int compareFrom = from.compareTo(other.getFrom());
        if (compareFrom == 0) {
            return to.compareTo(other.getTo());
        }
        return compareFrom;
    }
    
    @Override
    public String toString(){
        return String.format("[from: %s, to: %s]", from, to);
    }

}