package admiral_plasma.definition.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * The purpose of this Builder is to collect builders that build the same type
 * and builds them on demand in the same order they were added.
 *
 * @param <T> The result type.
 */
public class ListBuilder<T> {

    private final CompletableFuture<List<T>> first = new CompletableFuture<>();
    private CompletableFuture<List<T>> last;

    /**
     * Executes the builder and adds the result to the results list.
     *
     * @param <T> the type of the result of the builder.
     * @param builder The builder.
     * @param results The result list.
     * @return the result list for easy chaining.
     */
    private List<T> buildAndCollect(Builder<T> builder, List<T> results) {

        try {
            results.add(builder.build());
        } catch (BuildException ex) {
            throw new TunnelException(ex);
        }
        return results;
    }

    /**
     * Adds a new builder to the chain.
     *
     * @param builder the new Builder.
     */
    public synchronized void add(Builder<T> builder) {
        this.last = getLast().thenApply(list -> buildAndCollect(builder, list));
    }

    /**
     * Builds the added builder in order and returns the results as List.
     *
     * @return
     * @throws BuildException
     */
    public synchronized List<T> build() throws BuildException {
        // complete the first future in the chain.
        first.complete(new ArrayList<>());
        try {
            // get the result from the last future in the chain or throw the exception caused by any of them.
            return getLast().get();
        } catch (InterruptedException | ExecutionException ex) {
            throw new BuildException(ex);
        } catch (TunnelException t) {
            throw t.getCause();
        }
    }

    private CompletableFuture<List<T>> getLast() {
        if (last == null) {
            return first;
        }
        return last;
    }

    private static class TunnelException extends RuntimeException {

        public TunnelException(BuildException cause) {
            super(cause);
        }

        @Override
        public TunnelException getCause() {
            return (TunnelException) super.getCause();
        }

    }

}
