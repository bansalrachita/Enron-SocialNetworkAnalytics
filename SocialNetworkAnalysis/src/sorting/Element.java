package sorting;

import java.text.DecimalFormat;

/**
 * represents a unit of sorting element which is (Key, Value) pair
 */

public class Element<K, V> implements Comparable {
    public K index;
    public V value;
    DecimalFormat df = new DecimalFormat("#.####");

    public Element(K _index, V _value) {
        value = _value;
        index = _index;
    }

    @Override
    public String toString() {
        return "(" + index + ", " + Double.valueOf(df.format(value)) + ")";

    }

    @Override
    public int compareTo(Object o) {
        return Double.compare((Double) ((Element) o).value, (Double) value);
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return (value == ((Element) obj).value);
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return value.hashCode();
    }

}
