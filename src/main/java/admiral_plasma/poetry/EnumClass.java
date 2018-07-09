package admiral_plasma.poetry;

import java.io.IOException;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import admiral_plasma.definition.api.CaptnProtoEnum;

public class EnumClass {

	private CaptnProtoEnum cEnum;
	private CodeContext contex;

	public EnumClass(CodeContext contex, CaptnProtoEnum cEnum) {
		this.contex = contex;
		this.cEnum = cEnum;

	}

	public void buildJava() throws IOException {
		System.out.println("Generate enum " + cEnum.getId());
		Builder enumBuilder = TypeSpec.enumBuilder(ClassName.get(contex.getPackageName(), cEnum.getUniqeName()));
		for (String entry : cEnum.getEntries()) {
			enumBuilder.addEnumConstant(entry);
		}

		JavaFile javaFile = JavaFile.builder(contex.getPackageName(), enumBuilder.build()).build();

		javaFile.writeTo(contex.getTargetDir());
	}

}
