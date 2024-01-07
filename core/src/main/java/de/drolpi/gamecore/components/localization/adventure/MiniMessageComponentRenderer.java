package de.drolpi.gamecore.components.localization.adventure;

import de.drolpi.gamecore.components.localization.MessageFormatTranslationProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.renderer.TranslatableComponentRenderer;
import net.kyori.adventure.util.TriState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.function.Supplier;

public class MiniMessageComponentRenderer extends TranslatableComponentRenderer<Locale> {

    private final MiniMessageTranslator translator;

    public MiniMessageComponentRenderer(MessageFormatTranslationProvider translationProvider) {
        this.translator = new MiniMessageTranslator(translationProvider);
    }

    public @NotNull Component render(@NotNull Component component, @NotNull Locale context, TagResolver... resolvers) {
        if (component instanceof TranslatableComponent translatableComponent) {
            return this.renderTranslatable(translatableComponent, context, resolvers);
        }

        return super.render(component, context);
    }

    @Override
    protected @Nullable MessageFormat translate(final @NotNull String key, final @NotNull Locale context) {
        return translator.translate(key, context);
    }

    @Override
    protected @NotNull Component renderTranslatable(final @NotNull TranslatableComponent component, final @NotNull Locale context) {
        return this.renderTranslatable(component, context, () -> translator.translate(component, context));
    }

    private @NotNull Component renderTranslatable(final @NotNull TranslatableComponent component, final @NotNull Locale context, TagResolver... resolvers) {
        return this.renderTranslatable(component, context, () -> translator.translate(component, context, resolvers));
    }

    private @NotNull Component renderTranslatable(final @NotNull TranslatableComponent component, final @NotNull Locale context, Supplier<Component> translate) {
        final TriState anyTranslations = translator.hasAnyTranslations();
        if (anyTranslations == TriState.TRUE || anyTranslations == TriState.NOT_SET) {
            final @Nullable Component translated = translate.get();
            if (translated != null) return translated;
            return super.renderTranslatable(component, context);
        }
        return component;
    }

}
