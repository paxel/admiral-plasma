package admiral_plasma.poetry;

import admiral_plasma.definition.TestSchema;
import admiral_plasma.definition.api.BuildException;
import admiral_plasma.definition.api.ProtoSchema;
import admiral_plasma.poetry.api.CodeFactory;
import admiral_plasma.poetry.api.CodeProperties;
import admiral_plasma.poetry.java.simple.SimpleClassGenerator;
import admiral_plasma.poetry.java.simple.SimpleEnumGenerator;
import admiral_plasma.poetry.java.simple.SimpleGroupGenerator;
import admiral_plasma.poetry.java.simple.SimpleStructGenerator;
import admiral_plasma.poetry.java.simple.SimpleUnionGenerator;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import org.junit.Test;

public class CodeFactoryTest {

    private ProtoSchema schema;
    private CodeProperties properties;

    @Test
    public void test() throws IOException, BuildException {
        givenSchema();
        givenProperties();
        new CodeFactory().creatCode(schema, properties);
    }

    private void givenSchema() throws BuildException {
        this.schema = TestSchema.get();

    }

    private void givenProperties() {
        this.properties = new CodeProperties().setPackageName("org.afk").setTargetDir(Paths.get("target/generated-sources/test"))
                .setFileGeneratorFactory(SimpleClassGenerator::new)
                .setStructGeneratorFactory(SimpleStructGenerator::new)
                .setGroupGeneratorFactory(SimpleGroupGenerator::new)
                .setUnionGeneratorFactory(SimpleUnionGenerator::new)
                .setEnumGeneratorFactory(SimpleEnumGenerator::new);
    }

}
