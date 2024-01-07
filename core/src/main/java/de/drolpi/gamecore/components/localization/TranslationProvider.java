package de.drolpi.gamecore.components.localization;

import java.util.Arrays;
import java.util.List;

public interface TranslationProvider<T, U, V> {

    T single(U key, V context, Object... params);

    T[] array(U key, V context, Object... params);

    default List<T> list(U context,V key, Object... params) {
        return Arrays.asList(this.array(context, key, params));
    }
}
