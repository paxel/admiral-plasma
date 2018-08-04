package admiral_plasma.definition.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import admiral_plasma.definition.api.ProtoEnum;

/**
 *
 */
public class EnumCollector implements Builder<List<ProtoEnum>> {

    private final CompletableFuture<List<ProtoEnum>> first = new CompletableFuture<>();
    private CompletableFuture<List<ProtoEnum>> last;
    private final Parents parents;

    public EnumCollector(Parents parents) {
        super();
        this.parents = parents;
    }

    public ProtoEnumBuilder add(String name) {
        final ProtoEnumBuilder builder = new ProtoEnumBuilder(name, parents);
        synchronized (builder) {
            this.last = getLast().thenApply(new ChainBuilder<>(builder)::addBuild);
        }
        return builder;
    }

    @Override
    public List<ProtoEnum> build() throws InterruptedException, ExecutionException {
        first.complete(new ArrayList<>());
        return getLast().get();
    }

    private CompletableFuture<List<ProtoEnum>> getLast() {
        if (last == null) {
            return first;
        }
        return last;
    }
}
