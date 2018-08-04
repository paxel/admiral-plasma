package admiral_plasma.definition.builder;

import admiral_plasma.definition.api.BuildinType;
import admiral_plasma.definition.api.ProtoValue;

public class ProtoValueBuilder implements Builder<ProtoValue> {

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

    public ProtoValueBuilder(String name, IdGenerator generator) {
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
