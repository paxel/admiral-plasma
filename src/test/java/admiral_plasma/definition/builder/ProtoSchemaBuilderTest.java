package admiral_plasma.definition.builder;

import admiral_plasma.definition.TestSchema;
import admiral_plasma.definition.api.BuildException;
import admiral_plasma.definition.api.ProtoSchema;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

public class ProtoSchemaBuilderTest {

    private String NL = "\n";

    @Test
    public void testSomeMethod() throws IOException, BuildException {

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);
        ProtoSchema schema = TestSchema.get();
        schema.print(printWriter);
        printWriter.flush();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("example.capn").getFile());
        final String expected = new String(Files.readAllBytes(file.toPath()));
        final String value = new String(byteArrayOutputStream.toByteArray());
        //System.out.println(value);
        assertThat(value, is(expected));

    }

}
