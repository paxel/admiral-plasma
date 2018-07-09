package admiral_plasma.pojo_general.definition.builder;

import java.util.concurrent.ExecutionException;

public interface Builder<T> {

    T build() throws InterruptedException, ExecutionException;
}
