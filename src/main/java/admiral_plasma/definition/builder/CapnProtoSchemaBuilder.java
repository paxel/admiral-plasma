package admiral_plasma.definition.builder;

import java.util.concurrent.ExecutionException;

import admiral_plasma.definition.api.CaptnProtoSchema;

public class CapnProtoSchemaBuilder implements Builder<CaptnProtoSchema> {

	private final ContainerCollector containers = new ContainerCollector(new Parents());
	private final EnumCollector enums = new EnumCollector(new Parents());

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
