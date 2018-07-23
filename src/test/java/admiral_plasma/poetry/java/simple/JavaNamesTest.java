package admiral_plasma.poetry.java.simple;

import static org.hamcrest.CoreMatchers.is;
import org.junit.Assert;
import org.junit.Test;

public class JavaNamesTest {

    @Test
    public void testConstant() {
        Assert.assertThat(JavaNames.toConstantName("ALPHA"), is("ALPHA"));
        Assert.assertThat(JavaNames.toConstantName("alpha"), is("ALPHA"));
        Assert.assertThat(JavaNames.toConstantName("ALPHA_BETA"), is("ALPHA_BETA"));
        Assert.assertThat(JavaNames.toConstantName("ALPHA-BETA"), is("ALPHA_BETA"));
        Assert.assertThat(JavaNames.toConstantName("alphaBeta"), is("ALPHA_BETA"));
    }

    @Test
    public void testVariable() {
        Assert.assertThat(JavaNames.toVariableName("alpha"), is("alpha"));
        Assert.assertThat(JavaNames.toVariableName("Alpha"), is("alpha"));
        Assert.assertThat(JavaNames.toVariableName("alpha_beta"), is("alphaBeta"));
        Assert.assertThat(JavaNames.toVariableName("alpha-beta"), is("alphaBeta"));
        Assert.assertThat(JavaNames.toVariableName("AlphaBeta"), is("alphaBeta"));
    }

    @Test
    public void testClassName() {
        Assert.assertThat(JavaNames.toClassName("alpha"), is("Alpha"));
        Assert.assertThat(JavaNames.toClassName("Alpha"), is("Alpha"));
        Assert.assertThat(JavaNames.toClassName("alpha_beta"), is("AlphaBeta"));
        Assert.assertThat(JavaNames.toClassName("alpha-beta"), is("AlphaBeta"));
        Assert.assertThat(JavaNames.toClassName("AlphaBeta"), is("AlphaBeta"));
    }

}
