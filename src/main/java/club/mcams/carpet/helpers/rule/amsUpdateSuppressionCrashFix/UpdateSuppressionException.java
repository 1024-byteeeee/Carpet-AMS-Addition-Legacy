package club.mcams.carpet.helpers.rule.amsUpdateSuppressionCrashFix;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class UpdateSuppressionException {
    private static final List<Predicate<Throwable>> exceptionPredicates = new ArrayList<>();

    public static boolean isUpdateSuppression(Throwable throwable) {
        return exceptionPredicates.stream().anyMatch(predicate -> predicate.test(throwable));
    }

    static {
        exceptionPredicates.add(throwable -> throwable instanceof ClassCastException);
        exceptionPredicates.add(throwable -> throwable instanceof StackOverflowError);
        exceptionPredicates.add(throwable -> throwable instanceof OutOfMemoryError);
        exceptionPredicates.add(throwable -> throwable instanceof IllegalArgumentException);
    }
}
