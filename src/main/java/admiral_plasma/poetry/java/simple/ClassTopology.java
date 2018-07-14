package admiral_plasma.poetry.java.simple;

import java.util.ArrayList;
import java.util.List;

public class ClassTopology {
	private final List<String> names = new ArrayList<>();

	private ClassTopology(List<String> names) {
		this.names.addAll(names);
	}

	public ClassTopology() {
		super();
	}

	public String getRootName() {
		if (!names.isEmpty())
			return names.get(0);
		return null;
	}

	public String getClassName() {
		if (!names.isEmpty())
			return names.get(names.size() - 1);
		return null;
	}

	public String[] getStructure() {
		if (names.isEmpty())
			return new String[0];
		return names.subList(1, names.size()).toArray(new String[names.size() - 1]);
	}

	public ClassTopology add(String name) {
		ClassTopology clone = new ClassTopology(names);
		clone.names.add(JavaNames.toClassName(name));
		return clone;
	}
}
