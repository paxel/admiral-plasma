package admiral_plasma.poetry;

import admiral_plasma.definition.api.CaptnProtoContainer;

public class StructClass {

	private CodeContext contex;
	private CaptnProtoContainer container;

	public StructClass(CodeContext contex, CaptnProtoContainer container) {
		this.contex = contex;
		this.container = container;
	}

	public void buildJava() {
		System.out.println("Generate class " + container.getId());
		// TODO
	}

}
