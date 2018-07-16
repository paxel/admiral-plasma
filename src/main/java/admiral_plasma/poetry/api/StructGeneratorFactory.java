package admiral_plasma.poetry.api;

import admiral_plasma.poetry.java.simple.ClassTopology;

@FunctionalInterface
public interface StructGeneratorFactory {

    StructGenerator create(String name, CodeContext contex, ClassTopology parent);

}
