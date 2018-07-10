package admiral_plasma.poetry.api;

import admiral_plasma.definition.api.CaptnProtoContainer;

public interface UnionGeneratorFactory {

	UnionGenerator create(CodeContext context, CaptnProtoContainer captainContainer);

}
