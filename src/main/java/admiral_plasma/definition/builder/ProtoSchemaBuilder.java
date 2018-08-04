package admiral_plasma.definition.builder;

import java.util.concurrent.ExecutionException;

import admiral_plasma.definition.api.ProtoSchema;

public class ProtoSchemaBuilder implements Builder<ProtoSchema> {

    private final ContainerCollector containers = new ContainerCollector(new Parents());
    private final EnumCollector enums = new EnumCollector(new Parents());

    public ProtoEnumBuilder addEnum(String name) {
        return enums.add(name);
    }

    public ProtoContainerBuilder addStruct(String name) {
        return containers.addStruct(name);
    }

    @Override
    public ProtoSchema build() throws InterruptedException, ExecutionException {
        return new ProtoSchema(containers.build(), enums.build());
    }
}
