package admiral_plasma.definition.builder;

import admiral_plasma.definition.api.CaptnProtoEnum;
import admiral_plasma.definition.builder.CaptnProtoEnumBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 *
 */
public class EnumCollector implements Builder<List<CaptnProtoEnum>> {

	private final CompletableFuture<List<CaptnProtoEnum>> first = new CompletableFuture<>();
	private CompletableFuture<List<CaptnProtoEnum>> last;
	private final Parents parents;

	public EnumCollector(Parents parents) {
		super();
		this.parents = parents;
	}

	public CaptnProtoEnumBuilder add(String name) {
		final CaptnProtoEnumBuilder builder = new CaptnProtoEnumBuilder(name, parents);
		synchronized (builder) {
			this.last = getLast().thenApply(new ChainBuilder<>(builder)::addBuild);
		}
		return builder;
	}

	private CompletableFuture<List<CaptnProtoEnum>> getLast() {
		if (last == null) {
			return first;
		}
		return last;
	}

	public List<CaptnProtoEnum> build() throws InterruptedException, ExecutionException {
		first.complete(new ArrayList<>());
		return getLast().get();
	}
}
