package club.mcams.carpet.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTools {
    //state.getBlock.toString(); | Block{minecraft:bedrock} -> minecraft:bedrock
    public static String getBlockRegisterName(String sourceName) {
        String regex = "\\{(.*?)}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sourceName);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return sourceName;
    }
}
