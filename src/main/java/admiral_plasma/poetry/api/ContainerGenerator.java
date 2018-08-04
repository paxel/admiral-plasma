package admiral_plasma.poetry.api;

import java.io.IOException;

import com.squareup.javapoet.ClassName;

import admiral_plasma.definition.api.ProtoValue;
import admiral_plasma.poetry.java.simple.ClassTopology;

public interface ContainerGenerator {

    <T> T generate() throws IOException;

    void addEnum(EnumGenerator enumGenerator) throws IOException;

    void addUnion(UnionGenerator captainContainer) throws IOException;

    void addStruct(StructGenerator childGenerator) throws IOException;

    void addGroup(GroupGenerator captainContainer) throws IOException;

    void addValue(ProtoValue captainValue);

    String getName();

    ClassName getClassName();

    ClassTopology getClassTopology();

}
