package admiral_plasma.definition.builder;

import java.util.ArrayList;
import java.util.List;

import admiral_plasma.definition.api.CaptnProtoEnum;

public class CaptnProtoEnumBuilder implements Builder<CaptnProtoEnum> {

    private final String name;
    private final List<String> entries = new ArrayList<>();
    private final Parents parents;

    public CaptnProtoEnumBuilder(String name, Parents parents) {
        this.name = name;
        this.parents = parents;
    }

    public CaptnProtoEnumBuilder add(String e) {
        entries.add(e);
        return this;
    }

    @Override
    public CaptnProtoEnum build() {
        return new CaptnProtoEnum(name, entries, parents);
    }

}
