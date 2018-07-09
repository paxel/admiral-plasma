package admiral_plasma.poetry;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import admiral_plasma.definition.api.CaptnProtoContainer;
import admiral_plasma.definition.api.CaptnProtoEnum;
import admiral_plasma.definition.api.CaptnProtoValue;

public class StructClassGenerator {

	private CodeContext context;
	private CaptnProtoContainer container;

	public StructClassGenerator(CodeContext contex, CaptnProtoContainer container) {
		this.context = contex;
		this.container = container;
	}

	public void buildJava() throws IOException {

		Builder classBuilder = TypeSpec.classBuilder(ClassName.get(context.getPackageName(), container.getName()))
				.addModifiers(Modifier.PUBLIC);

		for (CaptnProtoEnum entry : container.getEnums()) {
			classBuilder.addType(new EnumClassGenerator().buildEnum(context, entry));
		}
		// prepare constructor
		MethodSpec.Builder constructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC);
		for (CaptnProtoValue entry : container.getValues()) {
			ClassName className = context.getClassName(entry);
			String name = entry.getName();
			FieldSpec spec = FieldSpec.builder(className, name, Modifier.PRIVATE, Modifier.FINAL).build();

			classBuilder.addField(spec);
			constructor.addParameter(className, name).addStatement("this.$N = $N", name, name);

			MethodSpec.Builder getter = MethodSpec.methodBuilder("get_" + name).addModifiers(Modifier.PUBLIC)
					.returns(className).addStatement("return this.$N;", name);
			classBuilder.addMethod(getter.build());

		}
		classBuilder.addMethod(constructor.build());
		TypeSpec typeSpec = classBuilder.build();
		JavaFile javaFile = JavaFile.builder(context.getPackageName(), typeSpec).build();

		javaFile.writeTo(context.getTargetDir());

	}
}

/*
 * VOID("Void"), BOOL("Bool"), INT_8("Int8"), INT_16("Int16"), INT_32("Int32"),
 * INT_64("Int64"), UINT_8("UInt8"), UINT_16("UInt16"), UINT_32("UInt32"),
 * UINT_64("UInt64"), FLOAT_32("Float32"), FLOAT_64("Float64"), TEXT("Text"),
 * DATA("Data"), LIST("List");
 */
