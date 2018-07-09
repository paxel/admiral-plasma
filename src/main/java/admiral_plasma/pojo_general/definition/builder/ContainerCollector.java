package admiral_plasma.pojo_general.definition.builder;

import admiral_plasma.pojo_general.definition.api.ContainerType;
import admiral_plasma.pojo_general.definition.builder.CaptnProtoContainerBuilder;
import admiral_plasma.pojo_general.definition.api.CaptnProtoContainer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 *
 */
public class ContainerCollector implements Builder<List<CaptnProtoContainer>> {

    private final CompletableFuture<List<CaptnProtoContainer>> first = new CompletableFuture<>();
    private CompletableFuture<List<CaptnProtoContainer>> last;

    public CaptnProtoContainerBuilder addStruct(String name) {
        final CaptnProtoContainerBuilder builder = new CaptnProtoContainerBuilder(name, ContainerType.STRUCT, new IdGenerator());
        synchronized (builder) {
            this.last = getLast().thenApply(new ChainBuilder<>(builder)::addBuild);
        }
        return builder;
    }

    public CaptnProtoContainerBuilder addGroup(String name, IdGenerator idGenerator) {
        final CaptnProtoContainerBuilder builder = new CaptnProtoContainerBuilder(name, ContainerType.GROUP, idGenerator);
        synchronized (builder) {
            this.last = getLast().thenApply(new ChainBuilder<>(builder)::addBuild);
        }
        return builder;
    }

    public CaptnProtoContainerBuilder addUnion(String name, IdGenerator IdGenerator) {
        final CaptnProtoContainerBuilder builder = new CaptnProtoContainerBuilder(name, ContainerType.UNION, IdGenerator);
        synchronized (builder) {
            this.last = getLast().thenApply(new ChainBuilder<>(builder)::addBuild);
        }
        return builder;
    }

    private CompletableFuture<List<CaptnProtoContainer>> getLast() {
        if (last == null) {
            return first;
        }
        return last;
    }

    @Override
    public List<CaptnProtoContainer> build() throws InterruptedException, ExecutionException {
        first.complete(new ArrayList<>());
        return getLast().get();
    }
}
