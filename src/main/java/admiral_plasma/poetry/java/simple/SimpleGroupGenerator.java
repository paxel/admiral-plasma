package admiral_plasma.poetry.java.simple;

import javax.lang.model.element.Modifier;

import admiral_plasma.poetry.api.CodeContext;
import admiral_plasma.poetry.api.GroupGenerator;

public class SimpleGroupGenerator extends SimpleContainerGenerator implements GroupGenerator {

    public SimpleGroupGenerator(String name, CodeContext context, ClassTopology parentTopology) {
        super(context, parentTopology.add(name + "Group"), Modifier.STATIC);
    }

    
}
