/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admiral_plasma.pojo_general.definition;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.concurrent.ExecutionException;
import org.junit.Test;

/**
 *
 * @author axel
 */
public class SchemaBuilderTest {
    
    @Test
    public void testSomeMethod() throws IOException, InterruptedException, ExecutionException {
        
        SchemaBuilder builder = new SchemaBuilder();
        final CaptnStructBuilder node = builder.addStruct("Node");
        {
            node.addValue("value").setType(BuildinType.FLOAT_32).setDefaultValue("unset");
            node.addValue("otherValue").setType("DemoEnum");
            final CaptnStructBuilder sub = node.addStruct("Sub");
            {
                sub.addValue("Some").setType(BuildinType.TEXT).setDefaultValue("hey");
                final CaptnStructBuilder deeper = sub.addStruct("evenDeeper");
                {
                    deeper.addValue("last").setType("Exit");
                }
            }
            node.addEnum("DemoEnum").add("alpha").add("beta").add("gamma");
        }
        
        final Schema schema = builder.build();
        final PrintWriter printWriter = new PrintWriter(out);
        
        schema.print(printWriter);
        printWriter.flush();
    }
    
}
