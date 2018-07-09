package admiral_plasma.pojo_general.definition.builder;

import admiral_plasma.pojo_general.definition.builder.CaptnProtoEnumBuilder;
import admiral_plasma.pojo_general.definition.api.CaptnProtoEnum;
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

    public CaptnProtoEnumBuilder add(String name) {
        final CaptnProtoEnumBuilder builder = new CaptnProtoEnumBuilder(name);
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
