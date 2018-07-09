package admiral_plasma.definition.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import admiral_plasma.definition.api.CaptnProtoContainer;
import admiral_plasma.definition.api.ContainerType;

/**
 *
 */
public class ContainerCollector implements Builder<List<CaptnProtoContainer>> {

	private final CompletableFuture<List<CaptnProtoContainer>> first = new CompletableFuture<>();
	private CompletableFuture<List<CaptnProtoContainer>> last;
	private final Parents parents;

	public ContainerCollector(Parents parents) {
		super();
		this.parents = parents;
	}

	public CaptnProtoContainerBuilder addGroup(String name, IdGenerator idGenerator) {
		final CaptnProtoContainerBuilder builder = new CaptnProtoContainerBuilder(name, ContainerType.GROUP,
				idGenerator, parents);
		synchronized (builder) {
			this.last = getLast().thenApply(new ChainBuilder<>(builder)::addBuild);
		}
		return builder;
	}

	public CaptnProtoContainerBuilder addStruct(String name) {
		final CaptnProtoContainerBuilder builder = new CaptnProtoContainerBuilder(name, ContainerType.STRUCT,
				new IdGenerator(), parents);
		synchronized (builder) {
			this.last = getLast().thenApply(new ChainBuilder<>(builder)::addBuild);
		}
		return builder;
	}

	public CaptnProtoContainerBuilder addUnion(String name, IdGenerator IdGenerator) {
		final CaptnProtoContainerBuilder builder = new CaptnProtoContainerBuilder(name, ContainerType.UNION,
				IdGenerator, parents);
		synchronized (builder) {
			this.last = getLast().thenApply(new ChainBuilder<>(builder)::addBuild);
		}
		return builder;
	}

	@Override
	public List<CaptnProtoContainer> build() throws InterruptedException, ExecutionException {
		first.complete(new ArrayList<>());
		return getLast().get();
	}

	private CompletableFuture<List<CaptnProtoContainer>> getLast() {
		if (last == null) {
			return first;
		}
		return last;
	}
}
