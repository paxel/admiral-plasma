package admiral_plasma.poetry;

import admiral_plasma.definition.api.CaptnProtoEnum;

public class EnumClass {

	private CaptnProtoEnum cEnum;
	private CodeContext contex;

	public EnumClass(CodeContext contex, CaptnProtoEnum cEnum) {
		this.contex = contex;
		this.cEnum = cEnum;

	}

	public void buildJava() {
		System.out.println("Generate enum " + cEnum.getId());
		// TODO
	}

}
