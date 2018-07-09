package admiral_plasma.poetry;

import java.io.IOException;

import admiral_plasma.definition.api.CaptnProtoContainer;
import admiral_plasma.definition.api.CaptnProtoEnum;
import admiral_plasma.definition.api.CaptnProtoSchema;

public class CodeFactory {

	public void creatCode(CaptnProtoSchema schema, CodeProperties properties) throws IOException {
		CodeContext context = new CodeContext(properties);
		for (CaptnProtoEnum captainEnum : schema.getEnums()) {
			EnumClassGenerator enumClass = new EnumClassGenerator();
			context.add(captainEnum.getId(), enumClass);
			enumClass.buildJavaFile(context, captainEnum);
		}
		for (CaptnProtoContainer container : schema.getStructs()) {
			switch (container.getType()) {
			case STRUCT:
				StructClassGenerator structClass = new StructClassGenerator(context, container);
				context.add(container.getId(), structClass);
				structClass.buildJava();
				break;

			default:
				throw new IllegalArgumentException("Unexpected type " + container.getType() + " in schema.");
			}
		}
	}
}
