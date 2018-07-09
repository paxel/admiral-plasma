package admiral_plasma.pojo_general.definition.builder;

import admiral_plasma.pojo_general.definition.api.CaptnProtoSchema;
import java.util.concurrent.ExecutionException;

public class CapnProtoSchemaBuilder implements Builder<CaptnProtoSchema> {

    private final ContainerCollector containers = new ContainerCollector();
    private final EnumCollector enums = new EnumCollector();

    public CaptnProtoContainerBuilder addStruct(String name) {
        return containers.addStruct(name);
    }

    public CaptnProtoEnumBuilder addEnum(String name) {
        return enums.add(name);
    }

    @Override
    public CaptnProtoSchema build() throws InterruptedException, ExecutionException {
        return new CaptnProtoSchema(containers.build(), enums.build());
    }
}
