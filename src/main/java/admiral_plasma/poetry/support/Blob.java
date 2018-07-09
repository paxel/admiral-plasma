package admiral_plasma.poetry.support;

import java.util.Arrays;

public class Blob {

	private final byte[] data;

	public Blob(byte[] data) {
		super();
		this.data = data;
	}

	public byte[] getData() {
		return data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Blob other = (Blob) obj;
		if (!Arrays.equals(data, other.data))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Blob [");
		if (data != null) {
			for (byte b : data) {
				int i = b & 0xff;
				if (i < 0x10)
					builder.append('0');
				builder.append(Integer.toHexString(i)).append(' ');
			}
		}
		builder.append("]");
		return builder.toString();
	}

}
