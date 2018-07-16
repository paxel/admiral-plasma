package admiral_plasma.poetry.java.simple;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;

import admiral_plasma.definition.api.CaptnProtoContainer;
import admiral_plasma.definition.api.CaptnProtoValue;
import admiral_plasma.poetry.api.CodeContext;
import admiral_plasma.poetry.api.EnumGenerator;
import admiral_plasma.poetry.api.GroupGenerator;
import admiral_plasma.poetry.api.StructGenerator;
import admiral_plasma.poetry.api.UnionGenerator;
import java.io.IOException;

public class SimpleUnionGenerator extends SimpleContainerGenerator implements UnionGenerator {

    public SimpleUnionGenerator(String name, CodeContext context, ClassTopology nestedClass) {
        super(context, nestedClass.add(name + "Union"), Modifier.STATIC);
    }

    @Override
    public void addGroup(GroupGenerator groupGenerator) throws IOException {
        super.addGroup(groupGenerator); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addStruct(StructGenerator structGenerator) throws IOException {
        super.addStruct(structGenerator); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addUnion(UnionGenerator unionGenerator) throws IOException {
        super.addUnion(unionGenerator); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addValue(CaptnProtoValue captainValue) {
        super.addValue(captainValue); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addEnum(EnumGenerator enumGenerator) throws IOException {
        super.addEnum(enumGenerator); //To change body of generated methods, choose Tools | Templates.
    }

    
}
