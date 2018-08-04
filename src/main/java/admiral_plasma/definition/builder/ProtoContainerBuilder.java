/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admiral_plasma.definition.builder;

import java.util.concurrent.ExecutionException;

import admiral_plasma.definition.api.ProtoContainer;
import admiral_plasma.definition.api.ContainerType;
import admiral_plasma.definition.api.ProtoEnum.EnumCollector;
import admiral_plasma.definition.api.ProtoEnum.ProtoEnumBuilder;

public class ProtoContainerBuilder implements Builder<ProtoContainer> {

    private final ContainerType type;
    private final String name;
    private final ContainerCollector containers;
    private final EnumCollector enums;
    private final ValueCollector values = new ValueCollector();
    private final IdGenerator idGenerator;
    private Parents parents;

    public ProtoContainerBuilder(String name, ContainerType type, IdGenerator generator, Parents parents) {
        this.name = name;
        this.type = type;
        this.idGenerator = generator;
        this.parents = parents;
        Parents newParent = parents.add(name);
        this.containers = new ContainerCollector(newParent);
        this.enums = new EnumCollector(newParent);
    }

    public ProtoContainerBuilder addBodyUnion() {
        return containers.addUnion("", idGenerator);
    }

    public ProtoEnumBuilder addEnum(String name) {
        return enums.add(name);
    }

    public ProtoContainerBuilder addGroup(String name) {
        return containers.addGroup(name, idGenerator);
    }

    public ProtoContainerBuilder addStruct(String name) {
        return containers.addStruct(name);
    }

    public ProtoContainerBuilder addUnion(String name) {
        return containers.addUnion(name, idGenerator);
    }

    public ProtoValueBuilder addValue(String name) {
        return values.add(name, idGenerator);
    }

    @Override
    public ProtoContainer build() {
        try {
            return new ProtoContainer(name, type, values.build(), containers.build(), enums.build(), parents);
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }

    }
}
