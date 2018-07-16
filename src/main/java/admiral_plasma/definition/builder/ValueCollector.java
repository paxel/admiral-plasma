package admiral_plasma.definition.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import admiral_plasma.definition.api.CaptnProtoValue;

/**
 *
 */
public class ValueCollector implements Builder<List<CaptnProtoValue>> {

    private final CompletableFuture<List<CaptnProtoValue>> first = new CompletableFuture<>();
    private CompletableFuture<List<CaptnProtoValue>> last;

    public CaptnProtoValueBuilder add(String name, IdGenerator generator) {
        final CaptnProtoValueBuilder builder = new CaptnProtoValueBuilder(name, generator);
        synchronized (builder) {
            this.last = getLast().thenApply(new ChainBuilder<>(builder)::addBuild);
        }
        return builder;
    }

    @Override
    public List<CaptnProtoValue> build() throws InterruptedException, ExecutionException {
        first.complete(new ArrayList<>());
        return getLast().get();
    }

    private CompletableFuture<List<CaptnProtoValue>> getLast() {
        if (last == null) {
            return first;
        }
        return last;
    }
}
