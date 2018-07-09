package admiral_plasma.poetry.support;

public class Uint8 implements Comparable<Uint8> {

	private int value;

	public Uint8(int raw) {
		super();
		this.value = raw & 0xff;
	}

	public Uint8(byte raw) {
		super();
		this.value = raw & 0xff;
	}

	@Override
	public int compareTo(Uint8 o) {
		if (o == null)
			return 1;
		return Integer.compare(value, o.value);
	}

	public int getValue() {
		return value;
	}
}
