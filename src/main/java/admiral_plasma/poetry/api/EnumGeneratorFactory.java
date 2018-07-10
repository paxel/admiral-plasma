package admiral_plasma.poetry.api;

import admiral_plasma.definition.api.CaptnProtoEnum;

@FunctionalInterface
public interface EnumGeneratorFactory {

	EnumGenerator create(CodeContext context, CaptnProtoEnum captnEnum);

}
