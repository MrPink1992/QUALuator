package at.fh.bac.Model;

import java.util.Objects;

public class TripletModel<T, U, V> {

    private final T type;
    private final U constraint;
    private final V value;

    public TripletModel(T type, U constraint, V value) {
        this.type = type;
        this.constraint = constraint;
        this.value = value;
    }

    public T getType() { return type; }
    public U getConstraint() { return constraint; }
    public V getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripletModel<?, ?, ?> that = (TripletModel<?, ?, ?>) o;
        return type.equals(that.type) &&
                constraint.equals(that.constraint) &&
                value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, constraint, value);
    }

    @Override
    public String toString() {
        return "TripletModel{" +
                "type=" + type +
                ", constraint=" + constraint +
                ", value=" + value +
                '}';
    }
}
