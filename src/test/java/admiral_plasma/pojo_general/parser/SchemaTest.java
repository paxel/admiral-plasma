package admiral_plasma.pojo_general.parser;

import admiral_plasma.pojo_general.definition.Schema;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;
import org.junit.Ignore;
import org.junit.Test;

public class SchemaTest {

    private final String EXAMPLE_1 = "@0xdbb9ad1f14bf0b36;  # unique file ID, generated by `capnp id`\n"
            + "\n"
            + "struct Person {\n"
            + "  name @0 :Text;\n"
            + "  birthdate @3 :Date;\n"
            + "\n"
            + "  email @1 :Text;\n"
            + "  phones @2 :List(PhoneNumber);\n"
            + "\n"
            + "  struct PhoneNumber {\n"
            + "    number @0 :Text;\n"
            + "    type @1 :Type;\n"
            + "\n"
            + "    enum Type {\n"
            + "      mobile @0;\n"
            + "      home @1;\n"
            + "      work @2;\n"
            + "    }\n"
            + "  }\n"
            + "}\n"
            + "\n"
            + "struct Date {\n"
            + "  year @0 :Int16;\n"
            + "  month @1 :UInt8;\n"
            + "  day @2 :UInt8;\n"
            + "}";
    private Schema from;

    @Ignore
    @Test
    public void test() {

        from = SchemaParser.from(EXAMPLE_1);
        assertThat(from, is(not(nullValue())));
    }

}