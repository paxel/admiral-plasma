package admiral_plasma.definition.builder;

import admiral_plasma.definition.api.BuildinType;
import admiral_plasma.definition.api.CaptnProtoValue;

public class CaptnProtoValueBuilder implements Builder<CaptnProtoValue> {

    private final String name;
    private final IdGenerator generator;
    private String type;
    private String defaultValue;
    private boolean constant;

    public boolean isConstant() {
        return constant;
    }

    public CaptnProtoValueBuilder setConstant(boolean constant) {
        this.constant = constant;
        return this;
    }

    public CaptnProtoValueBuilder(String name, IdGenerator generator) {
        this.name = name;
        this.generator = generator;
    }

    @Override
    public CaptnProtoValue build() {
        return new CaptnProtoValue(name, type, generator.next(), defaultValue, constant);
    }

    public CaptnProtoValueBuilder setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public CaptnProtoValueBuilder setType(BuildinType type) {
        this.type = type.getSyntax();
        return this;
    }

    public CaptnProtoValueBuilder setType(String type) {
        this.type = type;
        return this;
    }

}
