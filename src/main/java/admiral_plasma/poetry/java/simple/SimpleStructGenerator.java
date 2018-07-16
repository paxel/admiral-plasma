package admiral_plasma.poetry.java.simple;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;

import admiral_plasma.definition.api.CaptnProtoContainer;
import admiral_plasma.poetry.api.CodeContext;
import admiral_plasma.poetry.api.StructGenerator;

public class SimpleStructGenerator extends SimpleContainerGenerator implements StructGenerator {

    public SimpleStructGenerator(String name, CodeContext context, ClassTopology parent) {
        super(context, parent.add(name));
    }

    @Override
    public void addStruct(StructGenerator structGenerator) throws IOException {
        super.addStruct(structGenerator);
        structGenerator.setSubStruct();
    }

    @Override
    public void setSubStruct() {
        getLater().run(() -> getReader().addModifiers(Modifier.STATIC));
    }
}
