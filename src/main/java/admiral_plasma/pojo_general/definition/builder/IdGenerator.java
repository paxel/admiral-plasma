package admiral_plasma.pojo_general.definition.builder;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class IdGenerator {

    private final AtomicInteger gen = new AtomicInteger();

    public int next() {
        return gen.getAndIncrement();
    }
}
