package admiral_plasma.poetry.java.simple;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;

import admiral_plasma.poetry.api.CodeContext;
import admiral_plasma.poetry.api.UnionGenerator;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;

public class SimpleUnionGenerator extends SimpleContainerGenerator implements UnionGenerator {

    public SimpleUnionGenerator(String name, CodeContext context, ClassTopology nestedClass) {
        super(context, nestedClass.add(name + "Union"), Modifier.STATIC);
        getBuilder().addField(FieldSpec.builder(ClassName.BOOLEAN, "unionSet", Modifier.PRIVATE).build());
        getBuilder().addMethod(MethodSpec.methodBuilder("toggleUnionSet").addModifiers(Modifier.PRIVATE, Modifier.SYNCHRONIZED)
                .addCode(CodeBlock.builder().beginControlFlow("if (this.unionSet)$W")
                        .addStatement("throw new $T($S)", IllegalStateException.class, "A Union can have only one value.")
                        .endControlFlow()
                        .addStatement("this.$N=true", "unionSet").build()
                )
                .build()
        );
    }

    @Override
    protected MethodSpec.Builder prepareSetter(String name, ClassName className) {
        return MethodSpec.methodBuilder("set" + JavaNames.toClassName(name))
                .addModifiers(Modifier.PUBLIC)
                .returns(getBuilderClassName())
                .addStatement("toggleUnionSet()")
                .addStatement("this.$N=$N", name, name)
                .addStatement("return this")
                .addParameter(className, name);
    }

}
