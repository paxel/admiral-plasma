package admiral_plasma.poetry.java.simple;

import java.io.IOException;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import admiral_plasma.poetry.api.CodeContext;
import admiral_plasma.poetry.api.EnumGenerator;
import admiral_plasma.poetry.api.FileGenerator;
import admiral_plasma.poetry.api.StructGenerator;

public class SimpleClassGenerator implements FileGenerator {

    private final CodeContext context;
    private TypeSpec typeSpec;

    public SimpleClassGenerator(CodeContext contex) {
        super();
        this.context = contex;
    }

    /**
     * Builds a top layer class or enum and writes it to a file. Package and
     * target dir are taken from the context.
     */
    public void generate() throws IOException {

        if (typeSpec != null) {
            JavaFile javaFile = JavaFile.builder(context.getPackageName(), typeSpec).build();

            javaFile.writeTo(context.getTargetDir());
        }
    }

    @Override
    public void addUnion(EnumGenerator generator) throws IOException {
        if (typeSpec != null) {
            throw new IllegalArgumentException("Multiple content in class/union not suppported");
        }
        typeSpec = generator.generate();

    }

    @Override
    public void addStruct(StructGenerator generator) throws IOException {
        if (typeSpec != null) {
            throw new IllegalArgumentException("Multiple content in class/union not suppported");
        }
        typeSpec = generator.generate();
    }

}
