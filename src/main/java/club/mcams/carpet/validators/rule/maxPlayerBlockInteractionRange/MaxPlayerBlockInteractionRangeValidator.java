package club.mcams.carpet.validators.rule.maxPlayerBlockInteractionRange;

import carpet.settings.ParsedRule;
import carpet.settings.Validator;

import club.mcams.carpet.translations.Translator;

import net.minecraft.server.command.ServerCommandSource;

public class MaxPlayerBlockInteractionRangeValidator extends Validator<Double> {
    private static final Translator translator = new Translator("validator.maxPlayerBlockInteractionRange");

    @Override
    public Double validate(ServerCommandSource serverCommandSource, ParsedRule<Double> parsedRule, Double aDouble, String s) {
        return ((aDouble >= 0.0D && aDouble <= 512.0D) || aDouble == -1.0D) ? aDouble : null;
    }

    @Override
    public String description() {
        return translator.tr("value_range").getString();
    }
}
