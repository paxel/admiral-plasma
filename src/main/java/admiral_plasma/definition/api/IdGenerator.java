package admiral_plasma.definition.api;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A simple ID generator, that counts up from 0.
 */
public class IdGenerator {

    private final AtomicInteger gen = new AtomicInteger();

    /**
     * Retrieve the next ID.
     *
     * @return the next ID.
     */
    public int next() {
        return gen.getAndIncrement();
    }
}
