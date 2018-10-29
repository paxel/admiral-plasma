/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admiral_plasma.definition.api;

import java.util.List;
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author axel
 */
public class ListBuilderTest {

    private ListBuilder<String> builder;
    private List<String> result;

    @Test
    public void testSingleAdd() throws BuildException {
        givenBuilder();
        builder.add(() -> "alpha");

        whenBuilt();
        assertThat(toCsv(result), is("alpha"));
    }

    @Test
    public void testRepeatedBuild() throws BuildException {
        givenBuilder();
        builder.add(() -> "alpha");

        whenBuilt();
        assertThat(toCsv(result), is("alpha"));

        whenBuilt();
        assertThat(toCsv(result), is("alpha"));
    }

    @Test
    public void testModifiedBuilds() throws BuildException {
        givenBuilder();
        builder.add(() -> "alpha");

        whenBuilt();
        assertThat(toCsv(result), is("alpha"));

        builder.add(() -> "changed");
        whenBuilt();
        assertThat(toCsv(result), is("alpha,changed"));
    }

    private void whenBuilt() throws BuildException {
        result = builder.build();
    }

    @Test
    public void testMulitAdd() throws BuildException {
        givenBuilder();
        builder.add(() -> "alpha");
        builder.add(() -> "beta");
        builder.add(() -> "gamma");
        builder.add(() -> "delta");

        whenBuilt();
        assertThat(toCsv(result), is("alpha,beta,gamma,delta"));
    }

    private void givenBuilder() {
        builder = new ListBuilder<>();
    }

    private static String toCsv(final List<String> build) {
        return build.stream().collect(Collectors.joining(","));
    }

}
