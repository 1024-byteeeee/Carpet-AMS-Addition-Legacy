package club.mcams.carpet.validators.rule.commandPlayerChunkLoadController;

import carpet.settings.ParsedRule;
import carpet.settings.Validator;

import club.mcams.carpet.translations.Translator;

import net.minecraft.server.command.ServerCommandSource;

public class MaxRangeValidator extends Validator<Integer> {
    private static final Translator translator = new Translator("validator.blockChunkLoaderRangeController");

    @Override
    public Integer validate(ServerCommandSource serverCommandSource, ParsedRule<Integer> parsedRule, Integer integer, String s) {
        return integer >= 1 && integer <= 300 ? integer : null;
    }

    @Override
    public String description() {
        return translator.tr("value_range").getString();
    }
}
