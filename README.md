== Captn Proto ==

https://capnproto.org/[] is a protocol specification language with some nice giix and compiler for a lot of languages.

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

