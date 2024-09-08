package club.mcams.carpet.settings;

import carpet.settings.ParsedRule;

import net.minecraft.server.command.ServerCommandSource;

public abstract class SimpleRuleObserver<T> extends RuleObserver<T> {
    @Override
    public T validate(ServerCommandSource source, ParsedRule<T> currentRule, T newValue, String userInput) {
        onValueChange(currentRule.get(), newValue);
        return newValue;
    }
}
