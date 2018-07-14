package admiral_plasma.poetry.java.simple;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;

import admiral_plasma.definition.api.CaptnProtoContainer;
import admiral_plasma.poetry.api.CodeContext;
import admiral_plasma.poetry.api.UnionGenerator;

public class SimpleUnionGenerator extends SimpleContainerGenerator implements UnionGenerator {

	public SimpleUnionGenerator(CodeContext context, CaptnProtoContainer captainContainer) {
		super(context, captainContainer,  captainContainer.getName() + "Union",
				Modifier.PUBLIC, Modifier.STATIC);
	}

}