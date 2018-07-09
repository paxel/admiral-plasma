package admiral_plasma.poetry;

import admiral_plasma.definition.api.CaptnProtoContainer;
import admiral_plasma.definition.api.CaptnProtoEnum;
import admiral_plasma.definition.api.CaptnProtoSchema;

public class CodeFactory {

	public void creatCode(CaptnProtoSchema schema, CodeProperties properties) {
		CodeContext contex = new CodeContext(properties);
		for (CaptnProtoEnum cEnum : schema.getEnums()) {
			EnumClass enumClass = new EnumClass(contex, cEnum);
			contex.add(cEnum.getId(), enumClass);
			enumClass.buildJava();
		}
		for (CaptnProtoContainer container : schema.getStructs()) {
			switch (container.getType()) {
			case STRUCT:
				StructClass structClass = new StructClass(contex, container);
				contex.add(container.getId(), structClass);
				structClass.buildJava();
				break;

			default:
				throw new IllegalArgumentException("Unexpected type " + container.getType() + " in schema.");
			}
		}
	}
}
