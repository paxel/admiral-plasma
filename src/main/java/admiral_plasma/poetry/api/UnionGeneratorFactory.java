package admiral_plasma.poetry.api;

import admiral_plasma.poetry.java.simple.ClassTopology;

public interface UnionGeneratorFactory {

    UnionGenerator create(String name, CodeContext context, ClassTopology parent);

}
