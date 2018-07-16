package admiral_plasma.poetry.support;

public class Uint64 implements Comparable<Uint64> {

    private long value;

    public Uint64(long raw) {
        super();
        this.value = raw;
    }

    @Override
    public int compareTo(Uint64 o) {
        if (o == null) {
            return 1;
        }
        return Long.compareUnsigned(value, o.value);
    }

    public long getValue() {
        return value;
    }
}
