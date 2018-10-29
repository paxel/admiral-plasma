package admiral_plasma.definition.api;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * The ProtoContainer is an immutable container for other ProtoContainer,
 * {@link ProtoEnum}s and {@link ProtoValue}s. The Container can be either a
 * group, an union or a struct.
 */
public class ProtoContainer implements IndentedPrinter {

    private final String name;
    private final ContainerType type;

    private final List<ProtoValue> values;
    private final List<ProtoContainer> structs;
    private final List<ProtoEnum> enums;
    private final Parents parents;

    public ProtoContainer(String name, ContainerType type, List<ProtoValue> values,
            List<ProtoContainer> structs, List<ProtoEnum> enums, Parents parents) {
        this.name = name;
        this.type = type;
        this.values = values;
        this.structs = structs;
        this.enums = enums;
        this.parents = parents;
    }

    public List<ProtoEnum> getEnums() {
        return enums;
    }

    public String getId() {
        return parents + name;
    }

    public String getName() {
        return name;
    }

    public Parents getParents() {
        return parents;
    }

    public List<ProtoContainer> getStructs() {
        return structs;
    }

    public ContainerType getType() {
        return type;
    }

    public List<ProtoValue> getValues() {
        return values;
    }

    @Override
    public void print(Writer out, int indent) throws IOException {
        out.append('\n');
        printIndent(out, indent);
        out.append(type.getSyntax() + " " + name + " {\n");
        for (ProtoValue value : values) {
            value.print(out, indent + INDENT_SIZE);
        }
        for (ProtoContainer struct : structs) {
            struct.print(out, indent + INDENT_SIZE);
        }
        for (ProtoEnum cEnum : enums) {
            cEnum.print(out, indent + INDENT_SIZE);
        }
        printIndent(out, indent);
        out.append("}\n");
    }

    public static ProtoContainerBuilder createStruct(String name, IdGenerator idGenerarator, Parents parents) {
        return new ProtoContainerBuilder(name, ContainerType.STRUCT, idGenerarator, parents);
    }

    public static class ProtoContainerBuilder implements Builder<ProtoContainer> {

        private final ContainerType type;
        private final String name;
        private final ContainerCollector containers;
        private final ProtoEnum.EnumCollector enums;
        private final ProtoValue.ValueCollector values = new ProtoValue.ValueCollector();
        private final IdGenerator idGenerator;
        private final Parents parents;

        private ProtoContainerBuilder(String name, ContainerType type, IdGenerator generator, Parents parents) {
            this.name = name;
            this.type = type;
            this.idGenerator = generator;
            this.parents = parents;
            Parents newParent = parents.add(name);
            this.containers = new ContainerCollector(newParent);
            this.enums = new ProtoEnum.EnumCollector(newParent);
        }

        public ProtoContainerBuilder addBodyUnion() {
            return containers.addUnion("", idGenerator);
        }

        public ProtoEnum.ProtoEnumBuilder addEnum(String name) {
            return enums.add(name);
        }

        public ProtoContainerBuilder addGroup(String name) {
            return containers.addGroup(name, idGenerator);
        }

        public ProtoContainerBuilder addStruct(String name) {
            return containers.addStruct(name);
        }

        public ProtoContainerBuilder addUnion(String name) {
            return containers.addUnion(name, idGenerator);
        }

        public ProtoValue.ProtoValueBuilder addValue(String name) {
            return values.add(name, idGenerator);
        }

        @Override
        public ProtoContainer build() throws BuildException {
            return new ProtoContainer(name, type, values.build(), containers.build(), enums.build(), parents);
        }
    }

    public static class ContainerCollector implements Builder<List<ProtoContainer>> {

        private final ListBuilder<ProtoContainer> container = new ListBuilder<>();
        private final Parents parents;

        public ContainerCollector(Parents parents) {
            super();
            this.parents = parents;
        }

        public ProtoContainerBuilder addGroup(String name, IdGenerator idGenerator) {
            final ProtoContainerBuilder builder = new ProtoContainerBuilder(name, ContainerType.GROUP,
                    idGenerator, parents);
            container.add(builder);
            return builder;
        }

        public ProtoContainerBuilder addStruct(String name) {
            final ProtoContainerBuilder builder = new ProtoContainerBuilder(name, ContainerType.STRUCT,
                    new IdGenerator(), parents);
            container.add(builder);
            return builder;
        }

        public ProtoContainerBuilder addUnion(String name, IdGenerator IdGenerator) {
            final ProtoContainerBuilder builder = new ProtoContainerBuilder(name, ContainerType.UNION,
                    IdGenerator, parents);
            container.add(builder);
            return builder;
        }

        @Override
        public List<ProtoContainer> build() throws BuildException {
            return container.build();
        }

    }

    public static enum ContainerType {
        STRUCT("struct"), GROUP("group"), UNION("union");
        private final String syntax;

        private ContainerType(String syntax) {
            this.syntax = syntax;

        }

        public String getSyntax() {
            return syntax;
        }

    }

}
