package admiral_plasma.poetry.api;

import admiral_plasma.definition.api.ProtoEnum;
import admiral_plasma.poetry.java.simple.ClassTopology;

@FunctionalInterface
public interface EnumGeneratorFactory {

    EnumGenerator create(CodeContext context, ProtoEnum captnEnum, ClassTopology parent);

}
