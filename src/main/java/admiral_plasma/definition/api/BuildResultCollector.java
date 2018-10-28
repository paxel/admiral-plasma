package admiral_plasma.definition.api;

import java.util.List;

/**
 * The purpose of this function is to execute a builder and attach the result to
 * a given List.
 *
 * @author axel
 * @param <T>
 */
public class BuildResultCollector {

    /**
     * Executes the builder and adds the result to the results list.
     *
     * @param <T> the type of the result of the builder.
     * @param builder The builder.
     * @param results The result list.
     * @return the result list for easy chaining.
     */
    public static <T> List<T> buildAndCollect(Builder<T> builder, List<T> results) {

        try {
            results.add(builder.build());
        } catch (BuildException ex) {
            throw new RuntimeException(ex);
        }
        return results;
    }

}
