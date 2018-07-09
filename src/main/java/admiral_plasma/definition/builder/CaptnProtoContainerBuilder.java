/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admiral_plasma.definition.builder;

import java.util.concurrent.ExecutionException;

import admiral_plasma.definition.api.CaptnProtoContainer;
import admiral_plasma.definition.api.ContainerType;

public class CaptnProtoContainerBuilder implements Builder<CaptnProtoContainer> {

	private final ContainerType type;
	private final String name;
	private final ContainerCollector containers;
	private final EnumCollector enums;
	private final ValueCollector values = new ValueCollector();
	private final IdGenerator idGenerator;
	private Parents parents;

	public CaptnProtoContainerBuilder(String name, ContainerType type, IdGenerator generator, Parents parents) {
		this.name = name;
		this.type = type;
		this.idGenerator = generator;
		this.parents = parents;
		Parents newParent = parents.add(name);
		this.containers = new ContainerCollector(newParent);
		this.enums = new EnumCollector(newParent);
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
			return new CaptnProtoContainer(name, type, values.build(), containers.build(), enums.build(), parents);
		} catch (InterruptedException | ExecutionException ex) {
			throw new RuntimeException(ex);
		}

	}
}
