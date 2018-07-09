package admiral_plasma.poetry;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import admiral_plasma.definition.api.CaptnProtoEnum;

public class EnumClassGenerator {



	public void buildJavaFile(CodeContext context, CaptnProtoEnum captainEnum) throws IOException {

		TypeSpec typeSpec = buildEnum(context, captainEnum);
		JavaFile javaFile = JavaFile.builder(context.getPackageName(), typeSpec).build();

		javaFile.writeTo(context.getTargetDir());
	}

	public TypeSpec buildEnum(CodeContext context, CaptnProtoEnum captainEnum) {
		Builder enumBuilder = TypeSpec.enumBuilder(ClassName.get(context.getPackageName(), captainEnum.getName()))
				.addModifiers(Modifier.PUBLIC);
		for (String entry : captainEnum.getEntries()) {
			enumBuilder.addEnumConstant(entry);
		}
		TypeSpec typeSpec = enumBuilder.build();
		return typeSpec;
	}

}
