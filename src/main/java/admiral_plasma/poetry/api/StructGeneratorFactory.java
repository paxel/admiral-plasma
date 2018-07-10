package admiral_plasma.poetry.api;

import admiral_plasma.definition.api.CaptnProtoContainer;

@FunctionalInterface
public interface StructGeneratorFactory {

	StructGenerator create(CodeContext contex, CaptnProtoContainer container);

}
