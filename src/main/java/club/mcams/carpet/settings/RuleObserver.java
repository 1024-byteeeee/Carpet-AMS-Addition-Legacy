package club.mcams.carpet.settings;

import carpet.settings.ParsedRule;
import carpet.settings.Validator;

import net.minecraft.server.command.ServerCommandSource;

public abstract class RuleObserver<T> extends Validator<T> {
    @Override
    public T validate(ServerCommandSource source, ParsedRule<T> currentRule, T newValue, String userInput) {
        if (currentRule.get() != newValue) {
            onValueChange(currentRule.get(), newValue);
        }
        return newValue;
    }

    abstract public void onValueChange(T oldValue, T newValue);
}
