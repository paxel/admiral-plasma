package admiral_plasma.poetry.support;

public class Uint32 implements Comparable<Uint32> {

	private long value;

	public Uint32(long raw) {
		super();
		this.value = raw & 0xffff_ffff;
	}

	public Uint32(int raw) {
		super();
		this.value = Integer.toUnsignedLong(raw);
	}

	@Override
	public int compareTo(Uint32 o) {
		if (o == null)
			return 1;
		return Long.compare(value, o.value);
	}

	public long getValue() {
		return value;
	}
}
