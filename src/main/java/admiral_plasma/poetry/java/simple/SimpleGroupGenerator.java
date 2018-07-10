package admiral_plasma.poetry.java.simple;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;

import admiral_plasma.definition.api.CaptnProtoContainer;
import admiral_plasma.poetry.api.CodeContext;
import admiral_plasma.poetry.api.GroupGenerator;

public class SimpleGroupGenerator extends SimpleContainerGenerator implements GroupGenerator {

	public SimpleGroupGenerator(CodeContext context, CaptnProtoContainer captainContainer) {
		super(context, captainContainer, captainContainer.getName() + "Group",
				Modifier.PUBLIC, Modifier.STATIC);
	}

}
