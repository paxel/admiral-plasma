package admiral_plasma.definition.api;

public enum BuildinType {
    VOID("Void"), BOOL("Bool"), INT_8("Int8"), INT_16("Int16"), INT_32("Int32"), INT_64("Int64"), UINT_8("UInt8"),
    UINT_16("UInt16"), UINT_32("UInt32"), UINT_64("UInt64"), FLOAT_32("Float32"), FLOAT_64("Float64"), TEXT("Text"),
    DATA("Data"), LIST("List");
    private final String syntax;

    private BuildinType(String name) {
        this.syntax = name;
    }

    public String getSyntax() {
        return syntax;
    }

    public static BuildinType findSyntax(String type) {
        BuildinType[] values = values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].syntax.equals(type)) {
                return values[i];
            }
        }

        return null;

    }

}
