package admiral_plasma.poetry;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.squareup.javapoet.ClassName;

import admiral_plasma.definition.api.BuildinType;
import admiral_plasma.definition.api.CaptnProtoValue;
import admiral_plasma.poetry.support.Blob;
import admiral_plasma.poetry.support.Uint16;
import admiral_plasma.poetry.support.Uint32;
import admiral_plasma.poetry.support.Uint64;
import admiral_plasma.poetry.support.Uint8;

public class CodeContext {

	private final CodeProperties properties;
	private final Map<String, ClassName> override;

	public CodeContext(CodeProperties properties) {
		this.properties = properties;

		// future feature: define Classes that should be used for types.
		this.override = new HashMap<>();
	}

	public void add(String id, EnumClassGenerator enumClass) {
		// TODO Auto-generated method stub

	}

	public void add(String id, StructClassGenerator structClass) {
		// TODO Auto-generated method stub

	}

	public String getPackageName() {
		return properties.getPackageName();
	}

	public Path getTargetDir() {
		return properties.getTargetDir();
	}

	public ClassName getClassName(CaptnProtoValue entry) {
		if (override.containsKey(entry.getType()))
			return override.get(entry.getType());
		BuildinType builtin = BuildinType.findSyntax(entry.getType());
		if (builtin != null) {
			switch (builtin) {
			case FLOAT_32:
				return ClassName.get(Float.class);
			case FLOAT_64:
				return ClassName.get(Double.class);
			case BOOL:
				return ClassName.get(Boolean.class);
			case DATA:
				return ClassName.get(Blob.class);
			case INT_16:
				return ClassName.get(Short.class);
			case INT_32:
				return ClassName.get(Integer.class);
			case INT_64:
				return ClassName.get(Long.class);
			case INT_8:
				return ClassName.get(Byte.class);
			case LIST:
				return ClassName.get(ArrayList.class);
			case TEXT:
				return ClassName.get(String.class);
			case UINT_16:
				return ClassName.get(Uint16.class);
			case UINT_32:
				return ClassName.get(Uint32.class);
			case UINT_64:
				return ClassName.get(Uint64.class);
			case UINT_8:
				return ClassName.get(Uint8.class);
			case VOID:
				return ClassName.get(Void.class);
			default:
				break;
			}
		}
		return ClassName.bestGuess(entry.getType());

	}
}
