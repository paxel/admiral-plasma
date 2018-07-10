package admiral_plasma.poetry.api;

import java.io.IOException;

import com.squareup.javapoet.ClassName;

import admiral_plasma.definition.api.CaptnProtoEnum;
import admiral_plasma.definition.api.CaptnProtoValue;

public interface ContainerGenerator {

	<T> T generate() throws IOException;

	void addEnum(CaptnProtoEnum captainEnum) throws IOException;

	void addUnion(UnionGenerator captainContainer) throws IOException;

	void addStruct(StructGenerator childGenerator) throws IOException;

	void addGroup(GroupGenerator captainContainer) throws IOException;

	void addValue(CaptnProtoValue captainValue);

	String getName();

	ClassName getClassName();

}
