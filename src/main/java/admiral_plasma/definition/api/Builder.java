package admiral_plasma.definition.api;

/**
 * A generic builder that throws {@link BuildException} if the build is
 * impossible.
 *
 * @param <T>
 */
public interface Builder<T> {

    /**
     * Builds an instance of T.
     *
     * @return the new instance.
     * @throws BuildException in case the build is not possible.
     */
    T build() throws BuildException;
}
