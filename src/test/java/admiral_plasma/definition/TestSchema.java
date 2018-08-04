package admiral_plasma.definition;

import java.util.concurrent.ExecutionException;

import admiral_plasma.definition.api.BuildinType;
import admiral_plasma.definition.api.ProtoSchema;
import admiral_plasma.definition.builder.ProtoSchemaBuilder;
import admiral_plasma.definition.builder.ProtoContainerBuilder;

public class TestSchema {

    public static ProtoSchema get() throws InterruptedException, ExecutionException {
        ProtoSchemaBuilder builder = new ProtoSchemaBuilder();
        {
            final ProtoContainerBuilder node = builder.addStruct("Node");
            node.addValue("value").setType(BuildinType.FLOAT_32);
            node.addValue("otherValue").setType("DemoEnum");
            {
                final ProtoContainerBuilder sub = node.addStruct("Sub");
                sub.addValue("SOME").setType(BuildinType.TEXT).setDefaultValue("hey").setConstant(true);
                {
                    final ProtoContainerBuilder deeper = sub.addStruct("evenDeeper");
                    deeper.addValue("last").setType("Exit");
                    deeper.addEnum("Exit").add("OK").add("ERR").add("NO_CHEESE");
                }
                final ProtoContainerBuilder bodyUnion = node.addBodyUnion();
                {
                    final ProtoContainerBuilder helloKitty = bodyUnion.addGroup("hello_kitty");
                    helloKitty.addValue("tiger").setType(BuildinType.TEXT);
                    helloKitty.addValue("bear").setType(BuildinType.INT_16).setDefaultValue("8");
                    helloKitty.addValue("lion").setType(BuildinType.UINT_8);
                }
                {
                    final ProtoContainerBuilder barby = bodyUnion.addGroup("barbies");
                    barby.addValue("maria").setType(BuildinType.TEXT);
                    barby.addValue("siglinde").setType(BuildinType.INT_16);
                    barby.addValue("marietta").setType(BuildinType.UINT_8);
                }

            }
            builder.addEnum("DemoEnum").add("alpha").add("beta").add("gamma");

            return builder.build();
        }
    }
}
