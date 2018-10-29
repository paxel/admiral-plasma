package admiral_plasma.definition.api;

import admiral_plasma.definition.api.ProtoContainer.ContainerCollector;
import admiral_plasma.definition.api.ProtoContainer.ProtoContainerBuilder;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * This represents an immutable CapnProtoSchema.
 */
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

    /**
     * Creates a new builder for a {@link ProtoSchema}.
     *
     * @return a new builder.
     */
    public static ProtoSchemaBuilder create() {
        return new ProtoSchemaBuilder();
    }

    /**
     * This builder creates a new {@link ProtoSchema}.
     */
    public static class ProtoSchemaBuilder implements Builder<ProtoSchema> {

        private ProtoSchemaBuilder() {
        }

        private final ContainerCollector containers = new ContainerCollector(new Parents());
        private final ProtoEnum.EnumCollector enums = new ProtoEnum.EnumCollector(new Parents());

        /**
         * Adds and returns a new {@link ProtoEnum.ProtoEnumBuilder}.
         *
         * @param name The name of the enum.
         * @return the new builder.
         */
        public ProtoEnum.ProtoEnumBuilder addEnum(String name) {
            return enums.add(name);
        }

        /**
         * Adds and returns a new {@link ProtoContainerBuilder}.
         *
         * @param name The name of the container.
         * @return the new builder.
         */
        public ProtoContainerBuilder addStruct(String name) {
            return containers.addStruct(name);
        }

        @Override
        public ProtoSchema build() throws BuildException {
            return new ProtoSchema(containers.build(), enums.build());
        }
    }

}
