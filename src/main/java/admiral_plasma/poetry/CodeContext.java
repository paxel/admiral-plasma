package admiral_plasma.poetry;

import java.nio.file.Path;

public class CodeContext {

	private CodeProperties properties;

	public CodeContext(CodeProperties properties) {
		this.properties = properties;

	}

	public void add(String id, EnumClassGenerator enumClass) {
		// TODO Auto-generated method stub

	}

	public void add(String id, StructClass structClass) {
		// TODO Auto-generated method stub

	}

	public String getPackageName() {
		return properties.getPackageName();
	}

	public Path getTargetDir() {
		return properties.getTargetDir();
	}
}
