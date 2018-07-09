package admiral_plasma.poetry;

import java.nio.file.Path;

public class CodeProperties {

	private String packageName;
	private Path targetDir;

	public CodeProperties setPackageName(String packageName) {
		this.packageName = packageName;
		return this;
	}

	public CodeProperties setTargetDir(Path targetDir) {
		this.targetDir = targetDir;
		return this;
	}

	public String getPackageName() {
		return packageName;
	}

	public Path getTargetDir() {
		return targetDir;
	}

}
