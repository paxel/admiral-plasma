package admiral_plasma.definition.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Parents {

	private final List<String> parents;

	public Parents() {
		parents = new ArrayList<>();
	}

	private Parents(List<String> parents) {
		super();
		this.parents = parents;
	}

	public Parents add(String name) {
		ArrayList<String> newParents = new ArrayList<>(this.parents);
		newParents.add(name);
		return new Parents(newParents);
	}

	@Override
	public String toString() {
		return parents.stream().collect(Collectors.joining("."));
	}

}
