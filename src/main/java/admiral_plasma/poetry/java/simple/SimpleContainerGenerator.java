package admiral_plasma.poetry.java.simple;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import admiral_plasma.definition.api.CaptnProtoValue;
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
    private ClassTopology builderTopology;

    private com.squareup.javapoet.MethodSpec.Builder readerConstructor;

    private final Later<IOException> later = new Later<>();
    private final MethodSpec.Builder builderBuild;
    private final CodeBlock.Builder block;
    private boolean firstMember;

    public SimpleContainerGenerator(CodeContext context, ClassTopology myTopology, Modifier... modifiers) {
        this.context = context;

        this.readerTopology = myTopology;
        this.readerClassName = ClassName.get(context.getPackageName(), myTopology.getRootName(), myTopology.getStructure());
        this.reader = TypeSpec.classBuilder(readerTopology.getClassName()).addModifiers(Modifier.PUBLIC);
        this.readerConstructor = MethodSpec.constructorBuilder().addModifiers(modifiers);

        this.builderTopology = myTopology.add("Builder");
        this.builderClassName = ClassName.get(context.getPackageName(), myTopology.getRootName(), myTopology.getStructure());
        this.builder = TypeSpec.classBuilder(builderTopology.getClassName()).addModifiers(Modifier.PUBLIC);
        this.builderBuild = MethodSpec.methodBuilder("build").addModifiers(Modifier.PUBLIC).returns(readerClassName);
        this.block = CodeBlock.builder().add("$[return new $T(", readerClassName);
        this.firstMember = true;
        later.run(() -> builder.addMethod(builderBuild.addCode(block.add("$]").build()).build()));

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
    public void addValue(CaptnProtoValue captainValue) {

        ClassName className = context.getClassName(captainValue);
        String name = captainValue.getName();
        addMember(className, name);
    }

    private void addMember(ClassName className, String originalName) {
        String name = JavaNames.toVariableName(originalName);
        FieldSpec spec = FieldSpec.builder(className, name, Modifier.PRIVATE, Modifier.FINAL).build();

        // field for reader and builder
        reader.addField(spec);
        builder.addField(spec);

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
        MethodSpec setter = MethodSpec.methodBuilder("get" + JavaNames.toClassName(name))
                .addModifiers(Modifier.PUBLIC)
                .returns(builderClassName)
                .addStatement("this.$N=$N", name, name)
                .addStatement("return this")
                .addParameter(className, name)
                .build();
        builder.addMethod(setter);

        firstMember = false;
    }

    @Override
    public void addUnion(UnionGenerator unionGenerator) throws IOException {
        addMember(unionGenerator.getClassName(), unionGenerator.getName());
        later.run(() -> reader.addType(unionGenerator.generate()));
    }

    @Override
    public void addStruct(StructGenerator structGenerator) throws IOException {
        addMember(structGenerator.getClassName(), structGenerator.getName());
        later.run(() -> reader.addType(structGenerator.generate()));
    }

    @Override
    public void addGroup(GroupGenerator groupGenerator) throws IOException {
        addMember(groupGenerator.getClassName(), groupGenerator.getName());
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

}
