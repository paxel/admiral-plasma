package admiral_plasma.definition.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import admiral_plasma.definition.api.ProtoContainer;
import admiral_plasma.definition.api.ContainerType;

/**
 *
 */
public class ContainerCollector implements Builder<List<ProtoContainer>> {

    private final CompletableFuture<List<ProtoContainer>> first = new CompletableFuture<>();
    private CompletableFuture<List<ProtoContainer>> last;
    private final Parents parents;

    public ContainerCollector(Parents parents) {
        super();
        this.parents = parents;
    }

    public ProtoContainerBuilder addGroup(String name, IdGenerator idGenerator) {
        final ProtoContainerBuilder builder = new ProtoContainerBuilder(name, ContainerType.GROUP,
                idGenerator, parents);
        synchronized (builder) {
            this.last = getLast().thenApply(new ChainBuilder<>(builder)::addBuild);
        }
        return builder;
    }

    public ProtoContainerBuilder addStruct(String name) {
        final ProtoContainerBuilder builder = new ProtoContainerBuilder(name, ContainerType.STRUCT,
                new IdGenerator(), parents);
        synchronized (builder) {
            this.last = getLast().thenApply(new ChainBuilder<>(builder)::addBuild);
        }
        return builder;
    }

    public ProtoContainerBuilder addUnion(String name, IdGenerator IdGenerator) {
        final ProtoContainerBuilder builder = new ProtoContainerBuilder(name, ContainerType.UNION,
                IdGenerator, parents);
        synchronized (builder) {
            this.last = getLast().thenApply(new ChainBuilder<>(builder)::addBuild);
        }
        return builder;
    }

    @Override
    public List<ProtoContainer> build() throws InterruptedException, ExecutionException {
        first.complete(new ArrayList<>());
        return getLast().get();
    }

    private CompletableFuture<List<ProtoContainer>> getLast() {
        if (last == null) {
            return first;
        }
        return last;
    }
}
