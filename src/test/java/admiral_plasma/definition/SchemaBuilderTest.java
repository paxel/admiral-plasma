package admiral_plasma.definition;

import admiral_plasma.definition.api.BuildinType;
import admiral_plasma.definition.api.CaptnProtoSchema;
import admiral_plasma.definition.builder.CapnProtoSchemaBuilder;
import admiral_plasma.definition.builder.CaptnProtoContainerBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.concurrent.ExecutionException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

public class SchemaBuilderTest {

    private String NL = "\n";

    @Test
    public void testSomeMethod() throws IOException, InterruptedException, ExecutionException {

        CapnProtoSchemaBuilder builder = new CapnProtoSchemaBuilder();
        {
            final CaptnProtoContainerBuilder node = builder.addStruct("Node");
            node.addValue("value").setType(BuildinType.FLOAT_32).setDefaultValue("unset");
            node.addValue("otherValue").setType("DemoEnum");
            {
                final CaptnProtoContainerBuilder sub = node.addStruct("Sub");
                sub.addValue("Some").setType(BuildinType.TEXT).setDefaultValue("hey");
                {
                    final CaptnProtoContainerBuilder deeper = sub.addStruct("evenDeeper");
                    deeper.addValue("last").setType("Exit");
                }
            }
            node.addEnum("DemoEnum").add("alpha").add("beta").add("gamma");
            {
                final CaptnProtoContainerBuilder bodyUnion = node.addBodyUnion();
                {
                    final CaptnProtoContainerBuilder helloKitty = bodyUnion.addGroup("hello_kitty");
                    helloKitty.addValue("tiger").setType(BuildinType.TEXT);
                    helloKitty.addValue("bear").setType(BuildinType.INT_16);
                    helloKitty.addValue("lion").setType(BuildinType.UINT_8);
                }
                {
                    final CaptnProtoContainerBuilder barby = bodyUnion.addGroup("barbies");
                    barby.addValue("maria").setType(BuildinType.TEXT);
                    barby.addValue("siglinde").setType(BuildinType.INT_16);
                    barby.addValue("marietta").setType(BuildinType.UINT_8);
                }

            }

            final CaptnProtoSchema schema = builder.build();
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);

            schema.print(printWriter);
            printWriter.flush();

            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("example.capn").getFile());
            final String expected = new String(Files.readAllBytes(file.toPath()));
            final String value = new String(byteArrayOutputStream.toByteArray());
            assertThat(value, is(expected));

        }

    }
}
