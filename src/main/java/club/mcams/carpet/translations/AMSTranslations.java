package club.mcams.carpet.translations;

import carpet.CarpetSettings;

import club.mcams.carpet.AmsServer;
import club.mcams.carpet.utils.FileUtil;
import club.mcams.carpet.utils.Messenger;

import com.esotericsoftware.yamlbeans.YamlReader;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.BaseText;
import net.minecraft.text.TranslatableText;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

public class AMSTranslations {
    private static final String LANG_DIR = String.format("assets/%s/lang", TranslationConstants.TRANSLATION_NAMESPACE);
    public static final Map<String, Map<String, String>> translations = Maps.newLinkedHashMap();
    public static final Set<String> languages = Sets.newHashSet();

    public static void loadTranslations() {
        try {
            List<String> availableLanguages = getAvailableLanguages();
            availableLanguages.forEach(language -> {
                try {
                    Map<String, String> translation = loadTranslationForLanguage(language);
                    translations.put(language, translation);
                    languages.add(language);
                } catch (IOException e) {
                    AmsServer.LOGGER.warn("Failed to load translation for language: " + language, e);
                }
            });
        } catch (IOException e) {
            AmsServer.LOGGER.warn("Failed to get available languages", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static List<String> getAvailableLanguages() throws IOException {
        String yamlData = FileUtil.readFile(LANG_DIR + "/meta/languages.yml");
        try (Reader reader = new StringReader(yamlData)) {
            Map<String, Object> yamlMap = (Map<String, Object>) new YamlReader(reader).read();
            return (List<String>) yamlMap.getOrDefault("languages", new ArrayList<>());
        }
    }

    @SuppressWarnings("unchecked")
    private static Map<String, String> loadTranslationForLanguage(String language) throws IOException {
        String path = LANG_DIR + "/" + language + ".yml";
        String data = FileUtil.readFile(path);
        try (Reader reader = new StringReader(data)) {
            Map<String, Object> yaml = (Map<String, Object>) new YamlReader(reader).read();
            Map<String, String> translation = new LinkedHashMap<>();
            buildTranslationMap(translation, yaml, "");
            return translation;
        }
    }

    @SuppressWarnings("unchecked")
    private static void buildTranslationMap(Map<String, String> translation, Map<String, Object> yaml, String prefix) {
        yaml.forEach((key, value) -> {
            String fullKey = prefix.isEmpty() ? key : prefix + "." + key;
            if (value instanceof String) {
                translation.put(fullKey, (String) value);
            } else if (value instanceof Map) {
                buildTranslationMap(translation, (Map<String, Object>) value, fullKey);
            } else {
                throw new IllegalArgumentException("Unknown type " + value.getClass() + " for key " + fullKey);
            }
        });
    }

    public static String getServerLanguage() {
        //#if MC<11500
        //$$ return TranslationConstants.DEFAULT_LANGUAGE;
        //#else
        return CarpetSettings.language.equalsIgnoreCase("none") ? TranslationConstants.DEFAULT_LANGUAGE : CarpetSettings.language;
        //#endif
    }

    @NotNull
    public static Map<String, String> getTranslation(String lang) {
        return translations.getOrDefault(lang, Collections.emptyMap());
    }

    @Nullable
    public static String translateKeyToFormattedString(String lang, String key) {
        return getTranslation(lang).get(key);
    }

    public static BaseText translate(BaseText text, ServerPlayerEntity player) {
        if (player instanceof ServerPlayerEntityWithClientLanguage) {
            String lang = ((ServerPlayerEntityWithClientLanguage) player).getClientLanguage$AMS().toLowerCase();
            return translate(text, lang);
        }
        return text;
    }

    public static BaseText translate(BaseText text, String lang) {
        return translate(text, lang, false);
    }

    @SuppressWarnings("PatternVariableCanBeUsed")
    public static BaseText translate(BaseText text, String lang, boolean suppressWarnings) {
        if (!(text instanceof TranslatableText)) {
            return text;
        }
        TranslatableText translatableText = (TranslatableText) text;
        String translationKey = translatableText.getKey();
        if (!translationKey.startsWith(TranslationConstants.TRANSLATION_KEY_PREFIX)) {
            return text;
        }
        String formattedString = translateKeyToFormattedString(lang, translationKey);
        if (formattedString == null) {
            translateKeyToFormattedString(TranslationConstants.DEFAULT_LANGUAGE, translationKey);
        }
        if (formattedString != null) {
            text = updateTextWithTranslation(text, formattedString);
        } else if (!suppressWarnings) {
            AmsServer.LOGGER.warn("Unknown translation key {}", translationKey);
        }
        return text;
    }

    private static BaseText updateTextWithTranslation(BaseText originalText, String formattedString) {
        BaseText newText = Messenger.s(formattedString);
        newText.getSiblings().addAll(originalText.getSiblings());
        newText.setStyle(originalText.getStyle());
        return newText;
    }
}