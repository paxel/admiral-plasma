package admiral_plasma.poetry.java.simple;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import admiral_plasma.definition.api.CaptnProtoEnum;
import admiral_plasma.poetry.api.CodeContext;
import admiral_plasma.poetry.api.EnumGenerator;

public class SimpleEnumGenerator implements EnumGenerator {

    private final CodeContext context;
    private final CaptnProtoEnum captainEnum;
    private ClassTopology topology;

    public SimpleEnumGenerator(CodeContext contex, CaptnProtoEnum captainEnum, ClassTopology parentTopology) {
        super();
        this.context = contex;
        this.captainEnum = captainEnum;
        this.topology = parentTopology.add(captainEnum.getName());
    }

    @SuppressWarnings("unchecked")
    public TypeSpec generate() {
        Builder enumBuilder = TypeSpec
                .enumBuilder(ClassName.get(context.getPackageName(), topology.getRootName(), topology.getStructure()))
                .addModifiers(Modifier.PUBLIC);
        for (String name : captainEnum.getEntries()) {
            enumBuilder.addEnumConstant(JavaNames.toConstantName(name));
        }
        TypeSpec typeSpec = enumBuilder.build();
        return typeSpec;
    }

}
