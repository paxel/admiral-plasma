package admiral_plasma.poetry;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import admiral_plasma.definition.TestSchema;
import admiral_plasma.definition.api.CaptnProtoSchema;

public class CodeFactoryTest {

	private CaptnProtoSchema schema;
	private CodeProperties properties;

	@Test
	public void test() throws IOException, InterruptedException, ExecutionException {
		givenSchema();
		givenProperties();
		new CodeFactory().creatCode(schema, properties);
	}

	private void givenSchema() throws InterruptedException, ExecutionException {
		this.schema = TestSchema.get();

	}

	private void givenProperties() {
		this.properties = new CodeProperties().setPackageName("org.afk").setTargetDir(Paths.get("src/generated/java"));
	}

}
