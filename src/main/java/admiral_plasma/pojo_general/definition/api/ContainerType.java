package admiral_plasma.pojo_general.definition.api;

public enum ContainerType {
    STRUCT("struct"), GROUP("group"), UNION("union");
    private final String syntax;

    private ContainerType(String syntax) {
        this.syntax = syntax;

    }

    public String getSyntax() {
        return syntax;
    }

}
