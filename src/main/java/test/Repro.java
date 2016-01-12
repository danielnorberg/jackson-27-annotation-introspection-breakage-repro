package test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.AbstractTypeResolver;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.SimpleType;

import java.io.IOException;

/**
 * A repro of a breaking (?) annotation introspection change in jackson 2.7.x.
 *
 * Breaks: mvn -Djackson.version=2.7.0 compile exec:java -Dexec.mainClass=test.Repro
 *
 * Works: mvn -Djackson.version=2.6.4 compile exec:java -Dexec.mainClass=test.Repro
 *
 */
public class Repro {

  /**
   * An abstract type to deserialize.
   */
  public interface Foobar {

    @JsonProperty("foo_bar") String fooBar();
  }

  /**
   * A concrete implementation.
   */
  public static class FoobarImpl implements Foobar {

    private final String fooBar;

    public FoobarImpl(final String fooBar) {
      this.fooBar = fooBar;
    }

    public String fooBar() {
      return fooBar;
    }
  }

  /**
   * Instructs jackson to instantiate {@link FoobarImpl} when deserializing {@link Foobar}.
   */
  public static class FoobarAbstractTypeResolver extends AbstractTypeResolver {

    @Override
    public JavaType resolveAbstractType(final DeserializationConfig config, final JavaType type) {
      if (type.getRawClass() == Foobar.class) {
        return SimpleType.construct(FoobarImpl.class);
      }
      return super.resolveAbstractType(config, type);
    }
  }

  /**
   * Instructs jackson that {@link Foobar#fooBar()} and the {@code bar} constructor argument map to the {@code foobar}
   * property.
   */
  public static class FoobarAnnotationIntrospector extends NopAnnotationIntrospector {

    @Override
    public String findImplicitPropertyName(final AnnotatedMember member) {
      if (member instanceof AnnotatedParameter) {
        return "fooBar";
      }
      if (member instanceof AnnotatedMethod) {
        return "fooBar";
      }
      return null;
    }

    @Override
    public boolean hasCreatorAnnotation(final Annotated a) {
      return true;
    }
  }

  /**
   * Instructs jackson to use {@link FoobarAbstractTypeResolver} and {@link FoobarAnnotationIntrospector}.
   */
  public static class FoobarModule extends SimpleModule {

    @Override
    public void setupModule(final SetupContext context) {
      context.addAbstractTypeResolver(new FoobarAbstractTypeResolver());
      context.appendAnnotationIntrospector(new FoobarAnnotationIntrospector());
    }
  }

  /**
   * The repro.
   */
  public static void main(final String... args) throws IOException {

    final ObjectMapper mapper = new ObjectMapper()
        .registerModule(new FoobarModule());

    final Foobar foobar = mapper.readValue("{\"foo_bar\":\"baz\"}", Foobar.class);

    System.out.println(foobar.fooBar());
  }
}
