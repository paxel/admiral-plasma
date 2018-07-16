package admiral_plasma.poetry.api;

import java.nio.file.Path;

public class CodeProperties {

    private String packageName;
    private Path targetDir;
    private FileGeneratorFactory fileGeneratorFactory;
    private StructGeneratorFactory structGeneratorFactory;
    private EnumGeneratorFactory enumGeneratorFactory;
    private GroupGeneratorFactory groupGeneratorFactory;
    private UnionGeneratorFactory unionGeneratorFactory;

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

    public FileGeneratorFactory getFileGeneratorFactory() {
        return fileGeneratorFactory;
    }

    public CodeProperties setFileGeneratorFactory(FileGeneratorFactory enumFileGeneratorFactory) {
        this.fileGeneratorFactory = enumFileGeneratorFactory;
        return this;
    }

    public StructGeneratorFactory getStructGeneratorFactory() {
        return structGeneratorFactory;
    }

    public CodeProperties setStructGeneratorFactory(StructGeneratorFactory structFileGeneratorFactory) {
        this.structGeneratorFactory = structFileGeneratorFactory;
        return this;
    }

    public EnumGeneratorFactory getEnumGeneratorFactory() {
        return enumGeneratorFactory;
    }

    public CodeProperties setEnumGeneratorFactory(EnumGeneratorFactory enumGeneratorFactory) {
        this.enumGeneratorFactory = enumGeneratorFactory;
        return this;
    }

    public GroupGeneratorFactory getGroupGeneratorFactory() {
        return groupGeneratorFactory;
    }

    public UnionGeneratorFactory getUnionGeneratorFactory() {
        return unionGeneratorFactory;
    }

    public CodeProperties setGroupGeneratorFactory(GroupGeneratorFactory groupGeneratorFactory) {
        this.groupGeneratorFactory = groupGeneratorFactory;
        return this;
    }

    public CodeProperties setUnionGeneratorFactory(UnionGeneratorFactory unionGeneratorFactory) {
        this.unionGeneratorFactory = unionGeneratorFactory;
        return this;
    }

}
