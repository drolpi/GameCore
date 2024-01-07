package de.drolpi.gamecore.components.localization.adventure;

import de.drolpi.gamecore.components.localization.MessageFormatTranslationProvider;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.translation.Translator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("ClassCanBeRecord")
public class MiniMessageTranslator implements Translator {

    private final MessageFormatTranslationProvider translationProvider;

    public MiniMessageTranslator(MessageFormatTranslationProvider translationProvider) {
        this.translationProvider = translationProvider;
    }

    @Override
    public @NotNull Key name() {
        return Key.key("MiniMessageTranslator");
    }

    @Override
    public @Nullable MessageFormat translate(@NotNull String key, @NotNull Locale locale) {
        return this.translationProvider.single(key, locale);
    }

    @Override
    public @Nullable Component translate(@NotNull TranslatableComponent component, @NotNull Locale locale) {
        return this.translate(component, locale, new TagResolver[0]);
    }

    public @Nullable Component translate(@NotNull TranslatableComponent component, @NotNull Locale locale, TagResolver... resolvers) {
        MessageFormat format = this.translate(component.key(), locale);

        if (format == null) {
            return Component.text(component.key());
        }

        //TODO:
        String value = format.format(null, new StringBuffer(), null).toString();

        List<TagResolver> resolverList = new ArrayList<>(Arrays.asList(resolvers));
        resolverList.add(TagResolver.resolver("key", (argumentQueue, context) -> {
            Tag.Argument key = argumentQueue.peek();
            if (key == null) {
                return null;
            }

            Component result = this.translate(Component.translatable(key.toString()), locale, resolvers);
            if (result == null) {
                return null;
            }

            return Tag.inserting(result);
        }));

        return MiniMessage.miniMessage().deserialize(value, resolverList.toArray(TagResolver[]::new));
    }
}
