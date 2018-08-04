package admiral_plasma.definition.builder;

import java.util.ArrayList;
import java.util.List;

import admiral_plasma.definition.api.ProtoEnum;

public class ProtoEnumBuilder implements Builder<ProtoEnum> {

    private final String name;
    private final List<String> entries = new ArrayList<>();
    private final Parents parents;

    public ProtoEnumBuilder(String name, Parents parents) {
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
