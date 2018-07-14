package admiral_plasma.poetry.api;

import admiral_plasma.definition.api.CaptnProtoContainer;
import admiral_plasma.poetry.java.simple.ClassTopology;

@FunctionalInterface
public interface StructGeneratorFactory {

	StructGenerator create(CodeContext contex, CaptnProtoContainer container, ClassTopology parent);

}
