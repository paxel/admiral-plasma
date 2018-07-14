package org.afk;

import admiral_plasma.poetry.support.Uint8;
import java.lang.Float;
import java.lang.Short;
import java.lang.String;

public class Node {
  private final Float value;

  private final DemoEnum otherValue;

  private final Sub sub;

  private final Union union;

  Node(Float value, DemoEnum otherValue, Sub sub, Union union) {
    this.value = value;
    this.otherValue = otherValue;
    this.sub = sub;
    this.union = union;
  }

  public Float getValue() {
    return this.value;
  }

  public DemoEnum getOtherValue() {
    return this.otherValue;
  }

  public Sub getSub() {
    return this.sub;
  }

  public Union getUnion() {
    return this.union;
  }

  public static class Sub {
    private final String some;

    private final EvenDeeper evenDeeper;

    Sub(String some, EvenDeeper evenDeeper) {
      this.some = some;
      this.evenDeeper = evenDeeper;
    }

    public String getSome() {
      return this.some;
    }

    public EvenDeeper getEvenDeeper() {
      return this.evenDeeper;
    }

    public static class EvenDeeper {
      private final Exit last;

      EvenDeeper(Exit last) {
        this.last = last;
      }

      public Exit getLast() {
        return this.last;
      }
    }
  }

  public class Union {
    private final HelloKittyGroup helloKittyGroup;

    private final BarbiesGroup barbiesGroup;

    static Union(HelloKittyGroup helloKittyGroup, BarbiesGroup barbiesGroup) {
      this.helloKittyGroup = helloKittyGroup;
      this.barbiesGroup = barbiesGroup;
    }

    public HelloKittyGroup getHelloKittyGroup() {
      return this.helloKittyGroup;
    }

    public BarbiesGroup getBarbiesGroup() {
      return this.barbiesGroup;
    }

    public class HelloKittyGroup {
      private final String tiger;

      private final Short bear;

      private final Uint8 lion;

      static HelloKittyGroup(String tiger, Short bear, Uint8 lion) {
        this.tiger = tiger;
        this.bear = bear;
        this.lion = lion;
      }

      public String getTiger() {
        return this.tiger;
      }

      public Short getBear() {
        return this.bear;
      }

      public Uint8 getLion() {
        return this.lion;
      }
    }

    public class BarbiesGroup {
      private final String maria;

      private final Short siglinde;

      private final Uint8 marietta;

      static BarbiesGroup(String maria, Short siglinde, Uint8 marietta) {
        this.maria = maria;
        this.siglinde = siglinde;
        this.marietta = marietta;
      }

      public String getMaria() {
        return this.maria;
      }

      public Short getSiglinde() {
        return this.siglinde;
      }

      public Uint8 getMarietta() {
        return this.marietta;
      }
    }
  }
}
