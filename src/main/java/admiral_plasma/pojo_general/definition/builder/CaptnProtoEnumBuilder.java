package admiral_plasma.pojo_general.definition.builder;

import admiral_plasma.pojo_general.definition.api.CaptnProtoEnum;
import java.util.ArrayList;
import java.util.List;

public class CaptnProtoEnumBuilder implements Builder<CaptnProtoEnum> {

    private final String name;
    private final List<String> entries = new ArrayList<>();

    public CaptnProtoEnumBuilder(String name) {
        this.name = name;
    }

    public CaptnProtoEnumBuilder add(String e) {
        entries.add(e);
        return this;
    }

    @Override
    public CaptnProtoEnum build() {
        return new CaptnProtoEnum(name, entries);
    }

}
