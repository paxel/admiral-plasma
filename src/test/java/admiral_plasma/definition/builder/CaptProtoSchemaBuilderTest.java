package admiral_plasma.definition.builder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import admiral_plasma.definition.TestSchema;
import admiral_plasma.definition.api.CaptnProtoSchema;

public class CaptProtoSchemaBuilderTest {

	private String NL = "\n";

	@Test
	public void testSomeMethod() throws IOException, InterruptedException, ExecutionException {

		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		final PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);
		CaptnProtoSchema schema = TestSchema.get();
		schema.print(printWriter);
		printWriter.flush();

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("example.capn").getFile());
		final String expected = new String(Files.readAllBytes(file.toPath()));
		final String value = new String(byteArrayOutputStream.toByteArray());
		assertThat(value, is(expected));

	}

}
