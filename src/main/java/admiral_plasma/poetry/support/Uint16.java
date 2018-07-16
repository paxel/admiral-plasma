package admiral_plasma.poetry.support;

public class Uint16 implements Comparable<Uint16> {

    private int value;

    public Uint16(int raw) {
        super();
        this.value = raw & 0xffff;
    }

    public Uint16(short raw) {
        super();
        this.value = raw & 0xffff;
    }

    @Override
    public int compareTo(Uint16 o) {
        if (o == null) {
            return 1;
        }
        return Integer.compare(value, o.value);
    }

    public int getValue() {
        return value;
    }
}
