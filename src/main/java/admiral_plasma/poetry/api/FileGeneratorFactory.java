package admiral_plasma.poetry.api;

@FunctionalInterface
public interface FileGeneratorFactory {

	FileGenerator create(CodeContext contex);

}
