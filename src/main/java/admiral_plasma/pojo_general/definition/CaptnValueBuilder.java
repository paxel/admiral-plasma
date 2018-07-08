/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admiral_plasma.pojo_general.definition;

/**
 *
 * @author axel
 */
public class CaptnValueBuilder {

    private final String name;
    private final int id;
    private String type;
    private String defaultValue;

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public CaptnValueBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public CaptnValueBuilder setType(BuildinType type) {
        this.type = type.getName();
        return this;
    }

    CaptnValueBuilder(String name, int id) {
        this.name = name;
        this.id = id;
    }

    CaptnValue build() {
        return new CaptnValue(name, type, id, defaultValue);
    }

}
