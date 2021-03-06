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

* no list support
* no optionals
* no ifExists 
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
import java.lang.IllegalStateException;
import java.lang.Short;
import java.lang.String;

public class Node {
  private final Float value;

  private final DemoEnum otherValue;

  private final Sub sub;

  private final Union union;

  private Node(Float value, DemoEnum otherValue, Sub sub, Union union) {
    this.value = value;
    this.otherValue = otherValue;
    this.sub = sub;
    this.union = union;
  }

  public static Builder create() {
    return new Builder();
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

  public static class Builder {
    private Float value;

    private DemoEnum otherValue;

    private Sub sub;

    private Union union;

    public Float getValue() {
      return this.value;
    }

    public Builder setValue(Float value) {
      this.value=value;
      return this;
    }

    public DemoEnum getOtherValue() {
      return this.otherValue;
    }

    public Builder setOtherValue(DemoEnum otherValue) {
      this.otherValue=otherValue;
      return this;
    }

    public Sub getSub() {
      return this.sub;
    }

    public Builder setSub(Sub sub) {
      this.sub=sub;
      return this;
    }

    public Union getUnion() {
      return this.union;
    }

    public Builder setUnion(Union union) {
      this.union=union;
      return this;
    }

    public Node build() {
      return new Node(this.value, this.otherValue, this.sub, this.union);
    }
  }

  public static class Sub {
    private final String some;

    private final EvenDeeper evenDeeper;

    private Sub(String some, EvenDeeper evenDeeper) {
      this.some = some;
      this.evenDeeper = evenDeeper;
    }

    public static Builder create() {
      return new Builder();
    }

    public String getSome() {
      return this.some;
    }

    public EvenDeeper getEvenDeeper() {
      return this.evenDeeper;
    }

    public static class Builder {
      private String some;

      private EvenDeeper evenDeeper;

      public String getSome() {
        return this.some;
      }

      public Builder setSome(String some) {
        this.some=some;
        return this;
      }

      public EvenDeeper getEvenDeeper() {
        return this.evenDeeper;
      }

      public Builder setEvenDeeper(EvenDeeper evenDeeper) {
        this.evenDeeper=evenDeeper;
        return this;
      }

      public Sub build() {
        return new Sub(this.some, this.evenDeeper);
      }
    }

    public static class EvenDeeper {
      private final Exit last;

      private EvenDeeper(Exit last) {
        this.last = last;
      }

      public static Builder create() {
        return new Builder();
      }

      public Exit getLast() {
        return this.last;
      }

      public static class Builder {
        private Exit last;

        public Exit getLast() {
          return this.last;
        }

        public Builder setLast(Exit last) {
          this.last=last;
          return this;
        }

        public EvenDeeper build() {
          return new EvenDeeper(this.last);
        }
      }

      public enum Exit {
        OK,

        ERR,

        NO_CHEESE
      }
    }
  }

  public static class Union {
    private final HelloKittyGroup helloKittyGroup;

    private final BarbiesGroup barbiesGroup;

    private Union(HelloKittyGroup helloKittyGroup, BarbiesGroup barbiesGroup) {
      this.helloKittyGroup = helloKittyGroup;
      this.barbiesGroup = barbiesGroup;
    }

    public static Builder create() {
      return new Builder();
    }

    public HelloKittyGroup getHelloKittyGroup() {
      return this.helloKittyGroup;
    }

    public BarbiesGroup getBarbiesGroup() {
      return this.barbiesGroup;
    }

    public static class Builder {
      private boolean unionSet;

      private HelloKittyGroup helloKittyGroup;

      private BarbiesGroup barbiesGroup;

      private synchronized void toggleUnionSet() {
        if (this.unionSet)  {
          throw new IllegalStateException("A Union can have only one value.");
        }
        this.unionSet=true;
      }

      public HelloKittyGroup getHelloKittyGroup() {
        return this.helloKittyGroup;
      }

      public Builder setHelloKittyGroup(HelloKittyGroup helloKittyGroup) {
        toggleUnionSet();
        this.helloKittyGroup=helloKittyGroup;
        return this;
      }

      public BarbiesGroup getBarbiesGroup() {
        return this.barbiesGroup;
      }

      public Builder setBarbiesGroup(BarbiesGroup barbiesGroup) {
        toggleUnionSet();
        this.barbiesGroup=barbiesGroup;
        return this;
      }

      public Union build() {
        return new Union(this.helloKittyGroup, this.barbiesGroup);
      }
    }

    public static class HelloKittyGroup {
      private final String tiger;

      private final Short bear;

      private final Uint8 lion;

      private HelloKittyGroup(String tiger, Short bear, Uint8 lion) {
        this.tiger = tiger;
        this.bear = bear;
        this.lion = lion;
      }

      public static Builder create() {
        return new Builder();
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

      public static class Builder {
        private String tiger;

        private Short bear;

        private Uint8 lion;

        public String getTiger() {
          return this.tiger;
        }

        public Builder setTiger(String tiger) {
          this.tiger=tiger;
          return this;
        }

        public Short getBear() {
          return this.bear;
        }

        public Builder setBear(Short bear) {
          this.bear=bear;
          return this;
        }

        public Uint8 getLion() {
          return this.lion;
        }

        public Builder setLion(Uint8 lion) {
          this.lion=lion;
          return this;
        }

        public HelloKittyGroup build() {
          return new HelloKittyGroup(this.tiger, this.bear, this.lion);
        }
      }
    }

    public static class BarbiesGroup {
      private final String maria;

      private final Short siglinde;

      private final Uint8 marietta;

      private BarbiesGroup(String maria, Short siglinde, Uint8 marietta) {
        this.maria = maria;
        this.siglinde = siglinde;
        this.marietta = marietta;
      }

      public static Builder create() {
        return new Builder();
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

      public static class Builder {
        private String maria;

        private Short siglinde;

        private Uint8 marietta;

        public String getMaria() {
          return this.maria;
        }

        public Builder setMaria(String maria) {
          this.maria=maria;
          return this;
        }

        public Short getSiglinde() {
          return this.siglinde;
        }

        public Builder setSiglinde(Short siglinde) {
          this.siglinde=siglinde;
          return this;
        }

        public Uint8 getMarietta() {
          return this.marietta;
        }

        public Builder setMarietta(Uint8 marietta) {
          this.marietta=marietta;
          return this;
        }

        public BarbiesGroup build() {
          return new BarbiesGroup(this.maria, this.siglinde, this.marietta);
        }
      }
    }
  }
}
´´´