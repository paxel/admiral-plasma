package admiral_plasma.definition.api;

import admiral_plasma.definition.api.ProtoContainer.ContainerCollector;
import admiral_plasma.definition.api.ProtoContainer.ProtoContainerBuilder;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ProtoSchema {

    private final List<ProtoContainer> structs;
    private final List<ProtoEnum> enums;

    public ProtoSchema(List<ProtoContainer> structs, List<ProtoEnum> enums) {
        this.structs = structs == null ? new ArrayList<>() : structs;
        this.enums = enums == null ? new ArrayList<>() : enums;
    }

    public List<ProtoEnum> getEnums() {
        return enums;
    }

    public List<ProtoContainer> getStructs() {
        return structs;
    }

    public void print(Writer out) throws IOException {
        for (ProtoContainer value : structs) {
            value.print(out, 0);
        }
        for (ProtoEnum value : enums) {
            value.print(out, 0);
        }
    }

    public static ProtoSchemaBuilder create() {
        return new ProtoSchemaBuilder();
    }

    public static class ProtoSchemaBuilder implements Builder<ProtoSchema> {

        private ProtoSchemaBuilder() {
        }

        private final ContainerCollector containers = new ContainerCollector(new Parents());
        private final ProtoEnum.EnumCollector enums = new ProtoEnum.EnumCollector(new Parents());

        public ProtoEnum.ProtoEnumBuilder addEnum(String name) {
            return enums.add(name);
        }

        public ProtoContainerBuilder addStruct(String name) {
            return containers.addStruct(name);
        }

        @Override
        public ProtoSchema build() throws BuildException {
            return new ProtoSchema(containers.build(), enums.build());
        }
    }

}
