package de.drolpi.gamecore.components.localization;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public final class MessageFormatTranslationProviderImpl implements MessageFormatTranslationProvider {

    private final Map<Locale, ResourceBundle> bundles;

    public MessageFormatTranslationProviderImpl(BuilderImpl builder) {
        this.bundles = builder.bundles;
    }

    @Override
    public MessageFormat single(String key, Locale context, Object... params) {
        MessageFormat[] formats = this.translate(key, context);

        return formats[0];
    }

    @Override
    public MessageFormat[] array(String key, Locale context, Object... params) {
        return this.translate(key, context);
    }

    private MessageFormat[] translate(String key, Locale context) {
        ResourceBundle bundle = this.bundles.get(context);
        return (MessageFormat[]) bundle.getObject(key);
    }

    public static class BuilderImpl implements Builder {

        private final Map<Locale, ResourceBundle> bundles = new HashMap<>();

        @Override
        public Builder addBundle(ResourceBundle bundle) {
            this.bundles.put(bundle.getLocale(), bundle);
            return this;
        }

        @Override
        public MessageFormatTranslationProvider build() {
            return new MessageFormatTranslationProviderImpl(this);
        }
    }
}
