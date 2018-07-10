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
  alpha,

  beta,

  gamma
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

  private final org.afk.Sub Sub;

  private final org.afk.Union Union;

  public Node(Float value, DemoEnum otherValue, org.afk.Sub Sub, org.afk.Union Union) {
    this.value = value;
    this.otherValue = otherValue;
    this.Sub = Sub;
    this.Union = Union;
  }

  public Float get_value() {
    return this.value;
  }

  public DemoEnum get_otherValue() {
    return this.otherValue;
  }

  public org.afk.Sub get_Sub() {
    return this.Sub;
  }

  public org.afk.Union get_Union() {
    return this.Union;
  }

  public static class Sub {
    private final String Some;

    private final org.afk.evenDeeper evenDeeper;

    public Sub(String Some, org.afk.evenDeeper evenDeeper) {
      this.Some = Some;
      this.evenDeeper = evenDeeper;
    }

    public String get_Some() {
      return this.Some;
    }

    public org.afk.evenDeeper get_evenDeeper() {
      return this.evenDeeper;
    }

    public static class evenDeeper {
      private final Exit last;

      public evenDeeper(Exit last) {
        this.last = last;
      }

      public Exit get_last() {
        return this.last;
      }
    }
  }

  public class Union {
    private final org.afk.hello_kittyGroup hello_kittyGroup;

    private final org.afk.barbiesGroup barbiesGroup;

    public static Union(org.afk.hello_kittyGroup hello_kittyGroup,
        org.afk.barbiesGroup barbiesGroup) {
      this.hello_kittyGroup = hello_kittyGroup;
      this.barbiesGroup = barbiesGroup;
    }

    public org.afk.hello_kittyGroup get_hello_kittyGroup() {
      return this.hello_kittyGroup;
    }

    public org.afk.barbiesGroup get_barbiesGroup() {
      return this.barbiesGroup;
    }

    public class hello_kittyGroup {
      private final String tiger;

      private final Short bear;

      private final Uint8 lion;

      public static hello_kittyGroup(String tiger, Short bear, Uint8 lion) {
        this.tiger = tiger;
        this.bear = bear;
        this.lion = lion;
      }

      public String get_tiger() {
        return this.tiger;
      }

      public Short get_bear() {
        return this.bear;
      }

      public Uint8 get_lion() {
        return this.lion;
      }
    }

    public class barbiesGroup {
      private final String maria;

      private final Short siglinde;

      private final Uint8 marietta;

      public static barbiesGroup(String maria, Short siglinde, Uint8 marietta) {
        this.maria = maria;
        this.siglinde = siglinde;
        this.marietta = marietta;
      }

      public String get_maria() {
        return this.maria;
      }

      public Short get_siglinde() {
        return this.siglinde;
      }

      public Uint8 get_marietta() {
        return this.marietta;
      }
    }
  }
}
´´´