/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admiral_plasma.pojo_general.definition.builder;

import admiral_plasma.pojo_general.definition.api.ContainerType;
import admiral_plasma.pojo_general.definition.api.CaptnProtoContainer;
import java.util.concurrent.ExecutionException;

public class CaptnProtoContainerBuilder implements Builder<CaptnProtoContainer> {

    private final ContainerType type;
    private final String name;
    private final ContainerCollector containers = new ContainerCollector();
    private final EnumCollector enums = new EnumCollector();
    private final ValueCollector values = new ValueCollector();
    private final IdGenerator idGenerator;

    public CaptnProtoContainerBuilder(String name, ContainerType type, IdGenerator generator) {
        this.name = name;
        this.type = type;
        this.idGenerator = generator;
    }

    public CaptnProtoValueBuilder addValue(String name) {
        return values.add(name, idGenerator);
    }

    public CaptnProtoContainerBuilder addStruct(String name) {
        return containers.addStruct(name);
    }

    public CaptnProtoContainerBuilder addGroup(String name) {
        return containers.addGroup(name, idGenerator);
    }

    public CaptnProtoContainerBuilder addUnion(String name) {
        return containers.addUnion(name, idGenerator);
    }

    public CaptnProtoContainerBuilder addBodyUnion() {
        return containers.addUnion("", idGenerator);
    }

    public CaptnProtoEnumBuilder addEnum(String name) {
        return enums.add(name);
    }

    @Override
    public CaptnProtoContainer build() {
        try {
            return new CaptnProtoContainer(name, type, values.build(), containers.build(), enums.build());
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }

    }
}
