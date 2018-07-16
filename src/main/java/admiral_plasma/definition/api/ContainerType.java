package admiral_plasma.definition.api;

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
