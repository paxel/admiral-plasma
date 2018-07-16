package admiral_plasma.poetry.api;

import admiral_plasma.poetry.java.simple.ClassTopology;

public interface GroupGeneratorFactory {

    GroupGenerator create(String name, CodeContext context, ClassTopology parent);

}
