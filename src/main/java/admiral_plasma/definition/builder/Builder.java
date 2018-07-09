package admiral_plasma.definition.builder;

import java.util.concurrent.ExecutionException;

public interface Builder<T> {

	T build() throws InterruptedException, ExecutionException;
}
