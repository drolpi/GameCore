package de.drolpi.gamecore.components.localization;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public interface MessageFormatTranslationProvider extends TranslationProvider<MessageFormat, String, Locale> {

    static Builder builder() {
        return new MessageFormatTranslationProviderImpl.BuilderImpl();
    }

    interface Builder {

        Builder addBundle(ResourceBundle bundle);

        MessageFormatTranslationProvider build();

    }
}
