package admiral_plasma.definition.api;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ProtoValue implements IndentedPrinter {

    private final String name;
    private final String type;
    private final int id;
    private final String defaultValue;
    private final boolean constant;

    public ProtoValue(String name, String type, int id, String defaultValue, boolean constant) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.defaultValue = defaultValue;
        this.constant = constant;
    }

    public String getName() {
        return name;
    }

    public boolean isConstant() {
        return constant;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public void print(Writer out, int indent) throws IOException {
        printIndent(out, indent);
        if (constant) {
            out.append("const ");
        }
        out.append(name);
        out.append(" @" + id + " :");
        out.append(type);
        if (defaultValue != null) {
            out.append(" = " + defaultValue);
        }
        out.append(";\n");
    }

    public static ProtoValueBuilder create(String name, IdGenerator generator) {
        return new ProtoValueBuilder(name, generator);
    }

    public static class ProtoValueBuilder implements Builder<ProtoValue> {

        private final String name;
        private final IdGenerator generator;
        private String type;
        private String defaultValue;
        private boolean constant;

        public boolean isConstant() {
            return constant;
        }

        public ProtoValueBuilder setConstant(boolean constant) {
            this.constant = constant;
            return this;
        }

        private ProtoValueBuilder(String name, IdGenerator generator) {
            this.name = name;
            this.generator = generator;
        }

        @Override
        public ProtoValue build() {
            return new ProtoValue(name, type, generator.next(), defaultValue, constant);
        }

        public ProtoValueBuilder setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public ProtoValueBuilder setType(BuildinType type) {
            this.type = type.getSyntax();
            return this;
        }

        public ProtoValueBuilder setType(String type) {
            this.type = type;
            return this;
        }

    }

    public static class ValueCollector implements Builder<List<ProtoValue>> {

        private final CompletableFuture<List<ProtoValue>> first = new CompletableFuture<>();
        private CompletableFuture<List<ProtoValue>> last;

        public ProtoValueBuilder add(String name, IdGenerator generator) {
            final ProtoValueBuilder builder = new ProtoValueBuilder(name, generator);
            synchronized (builder) {
                this.last = getLast().thenApply(new ChainBuilder<>(builder)::addBuild);
            }
            return builder;
        }

        @Override
        public List<ProtoValue> build() throws InterruptedException, ExecutionException {
            first.complete(new ArrayList<>());
            return getLast().get();
        }

        private CompletableFuture<List<ProtoValue>> getLast() {
            if (last == null) {
                return first;
            }
            return last;
        }
    }

    public static enum BuildinType {
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

}
