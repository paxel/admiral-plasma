package admiral_plasma.definition;

import java.util.concurrent.ExecutionException;

import admiral_plasma.definition.api.BuildinType;
import admiral_plasma.definition.api.CaptnProtoSchema;
import admiral_plasma.definition.builder.CapnProtoSchemaBuilder;
import admiral_plasma.definition.builder.CaptnProtoContainerBuilder;

public class TestSchema {

    public static CaptnProtoSchema get() throws InterruptedException, ExecutionException {
        CapnProtoSchemaBuilder builder = new CapnProtoSchemaBuilder();
        {
            final CaptnProtoContainerBuilder node = builder.addStruct("Node");
            node.addValue("value").setType(BuildinType.FLOAT_32);
            node.addValue("otherValue").setType("DemoEnum");
            {
                final CaptnProtoContainerBuilder sub = node.addStruct("Sub");
                sub.addValue("SOME").setType(BuildinType.TEXT).setDefaultValue("hey").setConstant(true);
                {
                    final CaptnProtoContainerBuilder deeper = sub.addStruct("evenDeeper");
                    deeper.addValue("last").setType("Exit");
                    deeper.addEnum("Exit").add("OK").add("ERR").add("NO_CHEESE");
                }
                final CaptnProtoContainerBuilder bodyUnion = node.addBodyUnion();
                {
                    final CaptnProtoContainerBuilder helloKitty = bodyUnion.addGroup("hello_kitty");
                    helloKitty.addValue("tiger").setType(BuildinType.TEXT);
                    helloKitty.addValue("bear").setType(BuildinType.INT_16).setDefaultValue("8");
                    helloKitty.addValue("lion").setType(BuildinType.UINT_8);
                }
                {
                    final CaptnProtoContainerBuilder barby = bodyUnion.addGroup("barbies");
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
