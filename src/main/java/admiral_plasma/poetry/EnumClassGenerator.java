package admiral_plasma.poetry;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import admiral_plasma.definition.api.CaptnProtoEnum;

public class EnumClassGenerator {

	private CaptnProtoEnum cEnum;
	private CodeContext contex;

	public EnumClassGenerator(CodeContext contex, CaptnProtoEnum cEnum) {
		this.contex = contex;
		this.cEnum = cEnum;
	}

	public void buildJava() throws IOException {

		Builder enumBuilder = TypeSpec.enumBuilder(ClassName.get(contex.getPackageName(), cEnum.getUniqeName()))
				.addModifiers(Modifier.PUBLIC);
		for (String entry : cEnum.getEntries()) {
			enumBuilder.addEnumConstant(entry);
		}
		TypeSpec typeSpec = enumBuilder.build();
		JavaFile javaFile = JavaFile.builder(contex.getPackageName(), typeSpec).build();

		javaFile.writeTo(contex.getTargetDir());
	}

}
