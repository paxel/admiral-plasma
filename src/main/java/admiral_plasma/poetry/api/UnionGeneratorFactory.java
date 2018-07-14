package admiral_plasma.poetry.api;

import admiral_plasma.definition.api.CaptnProtoContainer;
import admiral_plasma.poetry.java.simple.ClassTopology;

public interface UnionGeneratorFactory {

	UnionGenerator create(CodeContext context, CaptnProtoContainer captainContainer, ClassTopology parent);

}
