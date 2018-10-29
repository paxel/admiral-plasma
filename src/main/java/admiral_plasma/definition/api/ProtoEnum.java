package admiral_plasma.definition.api;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ProtoEnum implements IndentedPrinter {

    private final List<String> entries;

    private final String name;

    private final Parents parents;

    public ProtoEnum(String name, List<String> entries, Parents parents) {
        this.entries = entries;
        this.name = name;
        this.parents = parents;
    }

    public List<String> getEntries() {
        return entries;
    }

    public String getId() {
        return parents + name;
    }

    public String getName() {
        return name;
    }

    public String getUniqeName() {
        return (parents + name).replace('.', '_');
    }

    @Override
    public void print(Writer out, int indent) throws IOException {
        out.append('\n');
        printIndent(out, indent);
        out.append("enum " + name + " {\n");
        int i = 0;
        for (String entry : entries) {
            printIndent(out, indent + INDENT_SIZE);
            out.append(entry + " @" + (i++) + ";\n");
        }
        printIndent(out, indent);
        out.append("}\n");
    }

    public static ProtoEnumBuilder create(String name, Parents parents) {
        return new ProtoEnumBuilder(name, parents);
    }

    public static class ProtoEnumBuilder implements Builder<ProtoEnum> {

        private final String name;
        private final List<String> entries = new ArrayList<>();
        private final Parents parents;

        private ProtoEnumBuilder(String name, Parents parents) {
            this.name = name;
            this.parents = parents;
        }

        public ProtoEnumBuilder add(String e) {
            entries.add(e);
            return this;
        }

        @Override
        public ProtoEnum build() {
            return new ProtoEnum(name, entries, parents);
        }

    }

    public static class EnumCollector implements Builder<List<ProtoEnum>> {

        private final ListBuilder<ProtoEnum> enums = new ListBuilder<>();
        private final Parents parents;

        public EnumCollector(Parents parents) {
            super();
            this.parents = parents;
        }

        public ProtoEnumBuilder add(String name) {
            final ProtoEnumBuilder builder = ProtoEnum.create(name, parents);
            enums.add(builder);
            return builder;
        }

        @Override
        public List<ProtoEnum> build() throws BuildException {
            return enums.build();
        }
    }

}
