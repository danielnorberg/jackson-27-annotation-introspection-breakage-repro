package test;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.util.ClassUtil;

import java.util.List;

public class ClassUtilTest {

  interface Foo {

  }

  static class Bar implements Foo {

  }

  public static void main(final String... args) {

    // This prints [interface test.ClassUtilTest$Foo]

    final List<Class<?>> superTypes = ClassUtil.findSuperTypes(Bar.class, null);
    System.out.println(superTypes);

    // ... but the below produces empty lists

    final SimpleType barType = SimpleType.construct(Bar.class);
    System.out.println(barType.getInterfaces());

    final List<JavaType> newSuperTypes = ClassUtil.findSuperTypes(barType, null, false);
    System.out.println(newSuperTypes);
  }
}
