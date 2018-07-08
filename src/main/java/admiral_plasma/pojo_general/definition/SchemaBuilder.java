/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admiral_plasma.pojo_general.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Builds a pojo representation of a captn proto schema file
 */
public class SchemaBuilder {

    private final CompletableFuture<List<CaptnStruct>> structs = new CompletableFuture<>();
    private final CompletableFuture<List<CaptnEnum>> enums = new CompletableFuture<>();

    public CaptnStructBuilder addStruct(String name) {
        final CaptnStructBuilder structBuilder = new CaptnStructBuilder(name);
        this.structs.thenAccept(
                list -> list.add(structBuilder.build())
        );
        return structBuilder;
    }

    public CaptnEnumBuilder addEnum(String name) {
        final CaptnEnumBuilder enumBuilder = new CaptnEnumBuilder(name);
        this.enums.thenAccept(
                list -> list.add(enumBuilder.build())
        );
        return enumBuilder;
    }

    public Schema build() throws InterruptedException, ExecutionException {
        structs.complete(new ArrayList<>());
        enums.complete(new ArrayList<>());
        return new Schema(structs.get(), enums.get());
    }
}
