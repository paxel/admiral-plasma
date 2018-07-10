package admiral_plasma.poetry.api;

import admiral_plasma.definition.api.CaptnProtoContainer;

public interface GroupGeneratorFactory {

	GroupGenerator create(CodeContext context, CaptnProtoContainer captainContainer);

}
