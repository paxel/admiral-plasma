package admiral_plasma.definition.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import admiral_plasma.definition.api.ProtoValue;

/**
 *
 */
public class ValueCollector implements Builder<List<ProtoValue>> {

    private final CompletableFuture<List<ProtoValue>> first = new CompletableFuture<>();
    private CompletableFuture<List<ProtoValue>> last;

    public ProtoValueBuilder add(String name, IdGenerator generator) {
        final ProtoValueBuilder builder = new ProtoValueBuilder(name, generator);
        synchronized (builder) {
            this.last = getLast().thenApply(new ChainBuilder<>(builder)::addBuild);
        }
        return builder;
    }

    @Override
    public List<ProtoValue> build() throws InterruptedException, ExecutionException {
        first.complete(new ArrayList<>());
        return getLast().get();
    }

    private CompletableFuture<List<ProtoValue>> getLast() {
        if (last == null) {
            return first;
        }
        return last;
    }
}
