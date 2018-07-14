# Admiral Plasma vs. Captn Proto

https://capnproto.org/ is a protocol specification language with some nice gimmix and compiler that creates code for a lot of languages.

It makes communication between different applications fast and easy.

CapnProto mainly gets its speed by not parsing the binary data, but decorating the binary with parsing getters (at least the java implementation I know does this)

As a result of this, you have to be careful how you use the deserialized object: 

* you have to check if data exists before you can access it. 
* you have objects tightly coupled to the captn proto classes

etc.

Also some features are not supported in Java (afaik) e.g. the whole rpc stuff.

If I'm wrong, apologizes!

The goal of admiral plasma is to: 

* generate a easy usable pojo abstraction layer generated from a capn proto description file.
* a deserialization layer that can be used like the old files (blazing fast, but clunky) or converted to the pojo layer.
* Factories that allow easy composition of the defined classes.
* a serialization layer
* rpc interface

lets see how far I come :D



##Current Status:

* The generated innerclasses are not correctly named
* the getter and class syntax is not java style
* no factory classes are generated
* no serialization
* no deserialization
* no validation


```

struct Node {
   value @0 :Float32 = unset;
   otherValue @1 :DemoEnum;

   struct Sub {
      Some @0 :Text = hey;

      struct evenDeeper {
         last @0 :Exit;
      }
   }

   union  {

      group hello_kitty {
         tiger @2 :Text;
         bear @3 :Int16;
         lion @4 :UInt8;
      }

      group barbies {
         maria @5 :Text;
         siglinde @6 :Int16;
         marietta @7 :UInt8;
      }
   }
}

enum DemoEnum {
   alpha @0;
   beta @1;
   gamma @2;
}

```

creates

```java
package org.afk;

public enum DemoEnum {
  ALPHA,

  BETA,

  GAMMA
}
```

and


```java
package org.afk;

import admiral_plasma.poetry.support.Uint8;
import java.lang.Float;
import java.lang.Short;
import java.lang.String;

public class Node {
  private final Float value;

  private final DemoEnum otherValue;

  private final org.afk.Sub sub;

  private final org.afk.Union union;

  public Node(Float value, DemoEnum otherValue, org.afk.Sub sub, org.afk.Union union) {
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

  public org.afk.Sub getSub() {
    return this.sub;
  }

  public org.afk.Union getUnion() {
    return this.union;
  }

  public static class Sub {
    private final String some;

    private final org.afk.EvenDeeper evenDeeper;

    public Sub(String some, org.afk.EvenDeeper evenDeeper) {
      this.some = some;
      this.evenDeeper = evenDeeper;
    }

    public String getSome() {
      return this.some;
    }

    public org.afk.EvenDeeper getEvenDeeper() {
      return this.evenDeeper;
    }

    public static class EvenDeeper {
      private final Exit last;

      public EvenDeeper(Exit last) {
        this.last = last;
      }

      public Exit getLast() {
        return this.last;
      }
    }
  }

  public class Union {
    private final org.afk.HelloKittyGroup helloKittyGroup;

    private final org.afk.BarbiesGroup barbiesGroup;

    public static Union(org.afk.HelloKittyGroup helloKittyGroup,
        org.afk.BarbiesGroup barbiesGroup) {
      this.helloKittyGroup = helloKittyGroup;
      this.barbiesGroup = barbiesGroup;
    }

    public org.afk.HelloKittyGroup getHelloKittyGroup() {
      return this.helloKittyGroup;
    }

    public org.afk.BarbiesGroup getBarbiesGroup() {
      return this.barbiesGroup;
    }

    public class HelloKittyGroup {
      private final String tiger;

      private final Short bear;

      private final Uint8 lion;

      public static HelloKittyGroup(String tiger, Short bear, Uint8 lion) {
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

      public static BarbiesGroup(String maria, Short siglinde, Uint8 marietta) {
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

´´´