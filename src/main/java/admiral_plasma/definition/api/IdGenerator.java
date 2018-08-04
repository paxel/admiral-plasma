package admiral_plasma.definition.api;

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
