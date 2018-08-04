package admiral_plasma.definition.api;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChainBuilder<T> {

    private final Builder<T> builder;

    public ChainBuilder(Builder<T> builder) {
        this.builder = builder;
    }

    public List<T> addBuild(List<T> t) {

        try {
            t.add(builder.build());
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
        return t;
    }

}
