package admiral_plasma.poetry.api;

import java.io.IOException;

public interface FileGenerator {

	void generate() throws IOException;

	void addUnion(EnumGenerator generator) throws IOException;

	void addStruct(StructGenerator generator) throws IOException;
}
