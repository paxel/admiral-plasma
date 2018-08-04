package admiral_plasma.definition.api;

import java.util.concurrent.ExecutionException;

public interface Builder<T> {

    T build() throws InterruptedException, ExecutionException;
}
