package admiral_plasma.poetry.java.simple;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import admiral_plasma.definition.api.CaptnProtoContainer;
import admiral_plasma.definition.api.CaptnProtoValue;
import admiral_plasma.poetry.api.CodeContext;
import admiral_plasma.poetry.api.ContainerGenerator;
import admiral_plasma.poetry.api.EnumGenerator;
import admiral_plasma.poetry.api.GroupGenerator;
import admiral_plasma.poetry.api.StructGenerator;
import admiral_plasma.poetry.api.UnionGenerator;

public class SimpleContainerGenerator implements ContainerGenerator {

	protected CodeContext context;
	protected Builder classBuilder;
	protected com.squareup.javapoet.MethodSpec.Builder constructor;
	protected CaptnProtoContainer captainContainer;
	protected final Later<IOException> later = new Later<>();
	private ClassName className;
	private ClassTopology topology;

	public SimpleContainerGenerator(CodeContext context, CaptnProtoContainer captainContainer, ClassTopology myTopology,
			Modifier... modifiers) {
		this.context = context;
		this.captainContainer = captainContainer;
		this.topology = myTopology;
		this.className = ClassName.get(context.getPackageName(), myTopology.getRootName(), myTopology.getStructure());

		this.classBuilder = TypeSpec.classBuilder(myTopology.getClassName()).addModifiers(Modifier.PUBLIC);
		this.constructor = MethodSpec.constructorBuilder().addModifiers(modifiers);
	}

	@Override
	public ClassName getClassName() {
		return className;
	}

	@Override
	public String getName() {
		return topology.getClassName();
	}

	@Override
	public void addEnum(EnumGenerator enumGenerator) throws IOException {
		later.run(() -> classBuilder.addType(enumGenerator.generate()));
	}

	@Override
	public void addValue(CaptnProtoValue captainValue) {

		ClassName className = context.getClassName(captainValue);
		String name = captainValue.getName();
		addMember(className, name);

	}

	private void addMember(ClassName className, String originalName) {
		String name = JavaNames.toVariableName(originalName);
		FieldSpec spec = FieldSpec.builder(className, name, Modifier.PRIVATE, Modifier.FINAL).build();

		classBuilder.addField(spec);
		constructor.addParameter(className, name).addStatement("this.$N = $N", name, name);

		MethodSpec.Builder getter = MethodSpec.methodBuilder("get" + JavaNames.toClassName(name))
				.addModifiers(Modifier.PUBLIC).returns(className).addStatement("return this.$N", name);
		classBuilder.addMethod(getter.build());
	}

	@Override
	public void addUnion(UnionGenerator unionGenerator) throws IOException {
		addMember(unionGenerator.getClassName(), unionGenerator.getName());
		later.run(() -> classBuilder.addType(unionGenerator.generate()));
	}

	@Override
	public void addStruct(StructGenerator structGenerator) throws IOException {
		addMember(structGenerator.getClassName(), structGenerator.getName());

		later.run(() -> classBuilder.addType(structGenerator.generate()));
	}

	@Override
	public void addGroup(GroupGenerator groupGenerator) throws IOException {
		addMember(groupGenerator.getClassName(), groupGenerator.getName());
		later.run(() -> classBuilder.addType(groupGenerator.generate()));

	}

	@SuppressWarnings("unchecked")
	@Override
	public TypeSpec generate() throws IOException {

		classBuilder.addMethod(constructor.build());

		later.runNow();
		return classBuilder.build();

	}

	@Override
	public ClassTopology getClassTopology() {
		return topology;
	}

}