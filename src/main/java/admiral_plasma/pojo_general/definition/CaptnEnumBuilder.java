/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admiral_plasma.pojo_general.definition;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author axel
 */
public class CaptnEnumBuilder {

    private final String name;
    private final List<String> entries = new ArrayList<>();

    CaptnEnumBuilder(String name) {
        this.name = name;
    }

    public CaptnEnumBuilder add(String e) {
        entries.add(e);
        return this;
    }

    CaptnEnum build() {
        return new CaptnEnum(name, entries);
    }

}
