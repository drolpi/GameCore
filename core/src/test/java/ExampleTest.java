import com.google.inject.AbstractModule;
import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Scopes;
import org.junit.Test;

import java.util.Map;

public class ExampleTest {

    @Test
    public void testExample() {
        Injector first = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(A.class).to(B.class);
                bind(B.class).in(Scopes.SINGLETON);
            }
        });

        B b = first.getInstance(B.class);

        for (Map.Entry<Key<?>, Binding<?>> entry : first.getAllBindings().entrySet()) {
            System.out.println(entry.getValue().toString());
        }

        Injector child = first.createChildInjector();
        Injector child2 = first.createChildInjector();
        Injector injector = child.getInstance(Injector.class);
        Injector injector2 = child2.getInstance(Injector.class);
        System.out.println(child == injector);
        System.out.println(child2 == injector2);
        System.out.println(child.getInstance(B.class) == (b));
    }

    static class A {

    }

    static class B extends A {

    }
}
