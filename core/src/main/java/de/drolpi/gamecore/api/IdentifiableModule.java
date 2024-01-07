package de.drolpi.gamecore.api;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

public class IdentifiableModule<T, U extends T> extends AbstractModule {

    private final Class<T> type;
    private final Class<U> abstractType;
    private final Class<? extends U> realType;

    public IdentifiableModule(Class<T> type, Class<U> abstractType, Class<? extends U> realType) {
        this.type = type;
        this.abstractType = abstractType;
        this.realType = realType;
    }

    @Override
    protected void configure() {
        this.bind(this.type).to(this.realType).in(Scopes.SINGLETON);
        this.bind(this.abstractType).to(this.realType).in(Scopes.SINGLETON);
        this.bind(this.realType).in(Scopes.SINGLETON);
        this.bind(IdentifiableModule.class).annotatedWith(Names.named(this.type.getSimpleName())).toInstance(this);
    }
}
