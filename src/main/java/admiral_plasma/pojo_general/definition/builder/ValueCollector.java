package admiral_plasma.pojo_general.definition.builder;

import admiral_plasma.pojo_general.definition.builder.CaptnProtoValueBuilder;
import admiral_plasma.pojo_general.definition.api.CaptnProtoValue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

    private CompletableFuture<List<CaptnProtoValue>> getLast() {
        if (last == null) {
            return first;
        }
        return last;
    }

    public List<CaptnProtoValue> build() throws InterruptedException, ExecutionException {
        first.complete(new ArrayList<>());
        return getLast().get();
    }
}
