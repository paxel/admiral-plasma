package admiral_plasma.poetry.api;

import java.io.IOException;

import admiral_plasma.definition.api.CaptnProtoContainer;
import admiral_plasma.definition.api.CaptnProtoEnum;
import admiral_plasma.definition.api.CaptnProtoSchema;
import admiral_plasma.definition.api.CaptnProtoValue;
import admiral_plasma.definition.builder.Builder;
import admiral_plasma.poetry.java.simple.ClassTopology;
import admiral_plasma.poetry.java.simple.SimpleStructGenerator;

public class CodeFactory {

    public void creatCode(CaptnProtoSchema schema, CodeProperties properties) throws IOException {
        CodeContext context = new CodeContext(properties);
        for (CaptnProtoEnum captainEnum : schema.getEnums()) {
            FileGenerator enumFile = context.newFileGenerator(context);
            enumFile.addUnion(context.newEnumGenerator(context, captainEnum, new ClassTopology()));
            enumFile.generate();
        }
        for (CaptnProtoContainer container : schema.getStructs()) {
            switch (container.getType()) {
                case STRUCT:
                    StructGenerator structGenerator = context.newStructGenerator(context, container, new ClassTopology());
                    handleContainer(structGenerator, container, context);
                    FileGenerator classFile = context.newFileGenerator(context);
                    classFile.addStruct(structGenerator);
                    classFile.generate();
                    break;

                default:
                    throw new IllegalArgumentException("Unexpected type " + container.getType() + " in schema.");
            }
        }
    }

    private void handleContainer(ContainerGenerator parent, CaptnProtoContainer captainContainer, CodeContext context)
            throws IOException {
        for (CaptnProtoEnum captainEnum : captainContainer.getEnums()) {
            System.out.println("Enum " + captainContainer.getName() + ":" + captainEnum.getName());

            addEnum(parent, captainEnum, context, parent.getClassTopology().add(captainEnum.getName()));
        }
        for (CaptnProtoValue captainValue : captainContainer.getValues()) {
            System.out.println("Value " + captainContainer.getName() + ":" + captainValue.getName());
            addValue(parent, captainValue, context);
        }
        for (CaptnProtoContainer subContainer : captainContainer.getStructs()) {
            switch (subContainer.getType()) {
                case GROUP:
                    System.out.println("Group " + captainContainer.getName() + ":" + subContainer.getName());

                    GroupGenerator groupGenerator = context.newGroupGenerator(context, subContainer,
                            parent.getClassTopology());
                    parent.addGroup(groupGenerator);
                    handleContainer(groupGenerator, subContainer, context);
                    break;
                case STRUCT:
                    System.out.println("struct " + captainContainer.getName() + ":" + subContainer.getName());

                    StructGenerator structGenerator = context.newStructGenerator(context, subContainer,
                            parent.getClassTopology());
                    parent.addStruct(structGenerator);
                    handleContainer(structGenerator, subContainer, context);
                    break;
                case UNION:
                    System.out.println("Union " + captainContainer.getName() + ":" + subContainer.getName());

                    UnionGenerator unionGenerator = context.newUnionGenerator(context, subContainer,
                            parent.getClassTopology());
                    parent.addUnion(unionGenerator);
                    handleContainer(unionGenerator, subContainer, context);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown type" + captainContainer.getType());
            }
        }
    }

    private void addValue(ContainerGenerator builder, CaptnProtoValue captainValue, CodeContext context) {
        builder.addValue(captainValue);
    }

    private void addEnum(ContainerGenerator builder, CaptnProtoEnum captainEnum, CodeContext context,
            ClassTopology nestedClass) throws IOException {
        builder.addEnum(context.newEnumGenerator(context, captainEnum, nestedClass));
    }

}
