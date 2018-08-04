package admiral_plasma.poetry.api;

import admiral_plasma.definition.api.ProtoContainer;
import admiral_plasma.definition.api.ProtoEnum;
import admiral_plasma.definition.api.ProtoSchema;
import admiral_plasma.definition.api.ProtoValue;
import admiral_plasma.poetry.java.simple.ClassTopology;
import java.io.IOException;

public class CodeFactory {

    public void creatCode(ProtoSchema schema, CodeProperties properties) throws IOException {
        CodeContext context = new CodeContext(properties);
        for (ProtoEnum captainEnum : schema.getEnums()) {
            FileGenerator enumFile = context.newFileGenerator(context);
            enumFile.addUnion(context.newEnumGenerator(context, captainEnum, new ClassTopology()));
            enumFile.generate();
        }
        for (ProtoContainer container : schema.getStructs()) {
            switch (container.getType()) {
                case STRUCT:
                    StructGenerator structGenerator = context.newStructGenerator(container.getName(), context, new ClassTopology());
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

    private void handleContainer(ContainerGenerator parent, ProtoContainer captainContainer, CodeContext context)
            throws IOException {
        for (ProtoEnum captainEnum : captainContainer.getEnums()) {

            addEnum(parent, captainEnum, context, parent.getClassTopology().add(captainEnum.getName()));
        }
        for (ProtoValue captainValue : captainContainer.getValues()) {
            addValue(parent, captainValue, context);
        }
        for (ProtoContainer subContainer : captainContainer.getStructs()) {
            switch (subContainer.getType()) {
                case GROUP:

                    GroupGenerator groupGenerator = context.newGroupGenerator(subContainer.getName(), context,
                            parent.getClassTopology());
                    parent.addGroup(groupGenerator);
                    handleContainer(groupGenerator, subContainer, context);
                    break;
                case STRUCT:

                    StructGenerator structGenerator = context.newStructGenerator(subContainer.getName(), context,
                            parent.getClassTopology());
                    parent.addStruct(structGenerator);
                    handleContainer(structGenerator, subContainer, context);
                    break;
                case UNION:

                    UnionGenerator unionGenerator = context.newUnionGenerator(subContainer.getName(), context,
                            parent.getClassTopology());
                    parent.addUnion(unionGenerator);
                    handleContainer(unionGenerator, subContainer, context);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown type" + captainContainer.getType());
            }
        }
    }

    private void addValue(ContainerGenerator builder, ProtoValue captainValue, CodeContext context) {
        builder.addValue(captainValue);
    }

    private void addEnum(ContainerGenerator builder, ProtoEnum captainEnum, CodeContext context,
            ClassTopology nestedClass) throws IOException {
        builder.addEnum(context.newEnumGenerator(context, captainEnum, nestedClass));
    }

}
