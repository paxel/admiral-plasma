package admiral_plasma.poetry.java.simple;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import admiral_plasma.definition.api.ProtoValue;
import admiral_plasma.poetry.api.CodeContext;
import admiral_plasma.poetry.api.ContainerGenerator;
import admiral_plasma.poetry.api.EnumGenerator;
import admiral_plasma.poetry.api.GroupGenerator;
import admiral_plasma.poetry.api.StructGenerator;
import admiral_plasma.poetry.api.UnionGenerator;
import com.squareup.javapoet.CodeBlock;

public class SimpleContainerGenerator implements ContainerGenerator {

    private CodeContext context;
    private final ClassName readerClassName;
    private final Builder reader;
    private final ClassTopology readerTopology;

    private final ClassName builderClassName;
    private final Builder builder;
    private final ClassTopology builderTopology;

    private final com.squareup.javapoet.MethodSpec.Builder readerConstructor;

    private final Later<IOException> later = new Later<>();
    private final MethodSpec.Builder builderBuild;
    private final CodeBlock.Builder block;
    private boolean firstMember;

    public SimpleContainerGenerator(CodeContext context, ClassTopology myTopology, Modifier... modifiers) {
        this.context = context;

        this.readerTopology = myTopology;
        this.readerClassName = ClassName.get(context.getPackageName(), myTopology.getRootName(), myTopology.getStructure());
        this.reader = TypeSpec.classBuilder(readerTopology.getClassName()).addModifiers(Modifier.PUBLIC).addModifiers(modifiers);
        this.readerConstructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE);

        this.builderTopology = myTopology.add("Builder");
        this.builderClassName = ClassName.get(context.getPackageName(), builderTopology.getRootName(), builderTopology.getStructure());
        this.builder = TypeSpec.classBuilder(builderTopology.getClassName()).addModifiers(Modifier.PUBLIC).addModifiers(Modifier.STATIC);
        this.builderBuild = MethodSpec.methodBuilder("build").addModifiers(Modifier.PUBLIC).returns(readerClassName);

        this.reader.addMethod(MethodSpec.methodBuilder("create").addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(builderClassName).addStatement("return new $T()", builderClassName).build());
        this.block = CodeBlock.builder().add("return new $T(", readerClassName);
        this.firstMember = true;
        later.run(() -> reader.addType(builder.addMethod(builderBuild.addCode(block.add(");\n").build()).build()).build()));

    }

    public ClassName getBuilderClassName() {
        return builderClassName;
    }

    public final Builder getBuilder() {
        return builder;
    }

    public Builder getReader() {
        return reader;
    }

    public Later<IOException> getLater() {
        return later;
    }

    @Override
    public ClassName getClassName() {
        return readerClassName;
    }

    @Override
    public String getName() {
        return readerTopology.getClassName();
    }

    @Override
    public void addEnum(EnumGenerator enumGenerator) throws IOException {
        later.run(() -> reader.addType(enumGenerator.generate()));
    }

    @Override
    public void addValue(ProtoValue captainValue) {

        ClassName className = context.getClassName(captainValue);
        String name = captainValue.getName();
        if (captainValue.isConstant()) {
            addConstant(className, name, captainValue.getDefaultValue());
        } else {
            addMember(className, name, captainValue.getDefaultValue());
        }
    }

    protected void addMember(ClassName className, String originalName, String defaultValue) {
        String name = JavaNames.toVariableName(originalName);

        // field for reader and builder
        reader.addField(FieldSpec.builder(className, name, Modifier.PRIVATE, Modifier.FINAL).build());
        final FieldSpec.Builder field = FieldSpec.builder(className, name, Modifier.PRIVATE);
        if (defaultValue != null) {
            field.initializer(formatDefaultValue(className, defaultValue));
        }

        builder.addField(field.build());

        // constructor and factory method
        readerConstructor.addParameter(className, name).addStatement("this.$N = $N", name, name);
        if (!firstMember) {
            block.add(",$W");
        }
        block.add("this.$N", name);

        // getter for reader and builder
        MethodSpec getter = MethodSpec.methodBuilder("get" + JavaNames.toClassName(name))
                .addModifiers(Modifier.PUBLIC).returns(className).addStatement("return this.$N", name).build();
        reader.addMethod(getter);
        builder.addMethod(getter);

        // setter for builder
        MethodSpec setter = prepareSetter(name, className)
                .build();
        builder.addMethod(setter);

        firstMember = false;
    }

    protected void addConstant(ClassName className, String originalName, String value) {
        String name = JavaNames.toConstantName(originalName);
        final FieldSpec.Builder field = FieldSpec.builder(className, name, Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC);
        if (value != null) {
            field.initializer(formatDefaultValue(className, value));
        }

        reader.addField(field.build());
    }

    protected MethodSpec.Builder prepareSetter(String name, ClassName className) {
        return MethodSpec.methodBuilder("set" + JavaNames.toClassName(name))
                .addModifiers(Modifier.PUBLIC)
                .returns(builderClassName)
                .addStatement("this.$N=$N", name, name)
                .addStatement("return this")
                .addParameter(className, name);
    }

    @Override
    public void addUnion(UnionGenerator unionGenerator) throws IOException {
        addMember(unionGenerator.getClassName(), unionGenerator.getName(), null);
        later.run(() -> reader.addType(unionGenerator.generate()));
    }

    @Override
    public void addStruct(StructGenerator structGenerator) throws IOException {
        addMember(structGenerator.getClassName(), structGenerator.getName(), null);
        later.run(() -> reader.addType(structGenerator.generate()));
    }

    @Override
    public void addGroup(GroupGenerator groupGenerator) throws IOException {
        addMember(groupGenerator.getClassName(), groupGenerator.getName(), null);
        later.run(() -> reader.addType(groupGenerator.generate()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public TypeSpec generate() throws IOException {

        // finish all sub classes
        later.isNow();
        reader.addMethod(readerConstructor.build());
        return reader.build();
    }

    @Override
    public ClassTopology getClassTopology() {
        return readerTopology;
    }

    private CodeBlock formatDefaultValue(ClassName className, String value) {
        if (className.equals(ClassName.get(String.class))) {
            return CodeBlock.of("$S", value);
        }
        if (className.isBoxedPrimitive() || className.isBoxedPrimitive()) {
            return CodeBlock.of("$L", value);
        }

        // Constant of a Class that has to be defined yet, like
        // const bob :Person = (name = "Bob", email = "bob@example.com");
        // const secret :Data = 0x"9f98739c2b53835e 6720a00907abd42f";
        // const baz :SomeStruct = (id = .foo, message = .bar);
        throw new UnsupportedOperationException("Not supported yet. " + className + " = " + value);
    }

}
