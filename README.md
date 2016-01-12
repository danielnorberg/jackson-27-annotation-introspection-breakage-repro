Jackson 2.7.x Annotation Introspection Breakage Repro
=====================================================

A repro of a breaking (?) annotation introspection change in jackson 2.7.x.


* Jackson 2.7.0:

```
mvn -Djackson.version=2.7.0 compile exec:java -Dexec.mainClass=test.Repro
```

```
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building jackson-27-annotation-introspection-breakage-repro 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO]
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ jackson-27-annotation-introspection-breakage-repro ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /Users/dano/projects/jackson-27-annotation-introspection-breakage-repro/src/main/resources
[INFO]
[INFO] --- maven-compiler-plugin:3.3:compile (default-compile) @ jackson-27-annotation-introspection-breakage-repro ---
[INFO] Nothing to compile - all classes are up to date
[INFO]
[INFO] >>> exec-maven-plugin:1.2.1:java (default-cli) > validate @ jackson-27-annotation-introspection-breakage-repro >>>
[INFO]
[INFO] <<< exec-maven-plugin:1.2.1:java (default-cli) < validate @ jackson-27-annotation-introspection-breakage-repro <<<
[INFO]
[INFO] --- exec-maven-plugin:1.2.1:java (default-cli) @ jackson-27-annotation-introspection-breakage-repro ---
[WARNING]
java.lang.reflect.InvocationTargetException
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at org.codehaus.mojo.exec.ExecJavaMojo$1.run(ExecJavaMojo.java:297)
	at java.lang.Thread.run(Thread.java:745)
Caused by: com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field "foo_bar" (class test.Repro$FoobarImpl), not marked as ignorable (one known property: "fooBar"])
 at [Source: {"foo_bar":"baz"}; line: 1, column: 18] (through reference chain: test.FoobarImpl["foo_bar"])
	at com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException.from(UnrecognizedPropertyException.java:62)
	at com.fasterxml.jackson.databind.DeserializationContext.reportUnknownProperty(DeserializationContext.java:855)
	at com.fasterxml.jackson.databind.deser.std.StdDeserializer.handleUnknownProperty(StdDeserializer.java:1083)
	at com.fasterxml.jackson.databind.deser.BeanDeserializerBase.handleUnknownProperty(BeanDeserializerBase.java:1389)
	at com.fasterxml.jackson.databind.deser.BeanDeserializerBase.handleUnknownProperties(BeanDeserializerBase.java:1343)
	at com.fasterxml.jackson.databind.deser.BeanDeserializer._deserializeUsingPropertyBased(BeanDeserializer.java:455)
	at com.fasterxml.jackson.databind.deser.BeanDeserializerBase.deserializeFromObjectUsingNonDefault(BeanDeserializerBase.java:1123)
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserializeFromObject(BeanDeserializer.java:298)
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:133)
	at com.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:3788)
	at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:2779)
	at test.Repro.main(Repro.java:109)
	... 6 more
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.257 s
[INFO] Finished at: 2016-01-11T20:44:10-05:00
[INFO] Final Memory: 14M/220M
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.codehaus.mojo:exec-maven-plugin:1.2.1:java (default-cli) on project jackson-27-annotation-introspection-breakage-repro: An exception occured while executing the Java class. null: InvocationTargetException: Unrecognized field "foo_bar" (class test.Repro$FoobarImpl), not marked as ignorable (one known property: "fooBar"])
[ERROR] at [Source: {"foo_bar":"baz"}; line: 1, column: 18] (through reference chain: test.FoobarImpl["foo_bar"])
[ERROR] -> [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoExecutionException
```

* Jackson 2.6.4:

```
mvn -Djackson.version=2.6.4 compile exec:java -Dexec.mainClass=test.Repro
```
```
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building jackson-27-annotation-introspection-breakage-repro 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO]
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ jackson-27-annotation-introspection-breakage-repro ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /Users/dano/projects/jackson-27-annotation-introspection-breakage-repro/src/main/resources
[INFO]
[INFO] --- maven-compiler-plugin:3.3:compile (default-compile) @ jackson-27-annotation-introspection-breakage-repro ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /Users/dano/projects/jackson-27-annotation-introspection-breakage-repro/target/classes
[INFO]
[INFO] >>> exec-maven-plugin:1.2.1:java (default-cli) > validate @ jackson-27-annotation-introspection-breakage-repro >>>
[INFO]
[INFO] <<< exec-maven-plugin:1.2.1:java (default-cli) < validate @ jackson-27-annotation-introspection-breakage-repro <<<
[INFO]
[INFO] --- exec-maven-plugin:1.2.1:java (default-cli) @ jackson-27-annotation-introspection-breakage-repro ---
baz
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 2.061 s
[INFO] Finished at: 2016-01-11T20:43:38-05:00
[INFO] Final Memory: 18M/172M
[INFO] ------------------------------------------------------------------------
```