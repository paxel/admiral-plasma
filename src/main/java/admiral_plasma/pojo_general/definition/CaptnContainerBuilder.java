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
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author axel
 */
public class CaptnContainerBuilder {

    public static enum ContainerType {
        STRUCT, GROUP, UNION
    }
    private final AtomicInteger id = new AtomicInteger();
    private final String name;

    private final CompletableFuture<List<CaptnContainer>> structs = new CompletableFuture<>();
    private final CompletableFuture<List<CaptnEnum>> enums = new CompletableFuture<>();
    private final CompletableFuture<List<CaptnValue>> values = new CompletableFuture<>();

    CaptnContainerBuilder(String name, ContainerType type) {
        this.name = name;
    }

    public CaptnContainerBuilder addStruct(String name) {
        final CaptnContainerBuilder builder = new CaptnContainerBuilder(name, feqw);
        this.structs.thenApply(
                list -> list.add(builder.build())
        );
        return builder;
    }

    public CaptnEnumBuilder addEnum(String name) {
        final CaptnEnumBuilder builder = new CaptnEnumBuilder(name);
        this.enums.thenApply(
                list -> list.add(builder.build())
        );
        return builder;
    }

    public CaptnValueBuilder addValue(String name) {
        final CaptnValueBuilder builder = new CaptnValueBuilder(name, id.getAndIncrement());
        this.values.thenApply(
                list -> list.add(builder.build())
        );
        return builder;
    }

    CaptnContainer build() {
        values.complete(new ArrayList<>());
        structs.complete(new ArrayList<>());
        enums.complete(new ArrayList<>());
        try {
            return new CaptnContainer(name, values.get(), structs.get(), enums.get());
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }

}
