package ir.navaco.core.lra.coordinator.utils;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

public class StringUtils {

    public static final String EMPTY = "";

    public static final String NULL = null;

    public static final String ENTER = "\n";

    public static boolean isEmpty(String val) {
        return Objects.isNull(val) || val.isEmpty();
    }

    public static boolean notEmpty(String val) {
        return !isEmpty(val);
    }

    public static String[] convert(Enum[] enums) {
        return Arrays.stream(enums).map(item -> item.name()).toArray(String[]::new);
    }

    public static String camelCase(String[] words) {
        return Stream.of(words)
            .reduce((first, rest) -> first.concat(firstCharacterToUppercase(rest.toLowerCase())))
            .get();
    }

    public static String firstCharacterToUppercase(String word) {
        return String.format("%s%s", Character.toTitleCase(word.charAt(0)), word.substring(1));
    }

    public static String lowerCamelCaseUnderscoreSplit(String str) {
        String[] words = str.split("_");
        words[0] = words[0].toLowerCase();

        return camelCase(words);
    }

    public static String upperCamelCaseUnderscoreSplit(String str) {
        String[] words = str.split("_");
        words[0] = Character.toTitleCase(words[0].charAt(0)) + words[0].substring(1).toLowerCase();

        return camelCase(words);
    }

    public static <T> List<String> listToString(List<T> list, Function<T, String> mapping) {
        return list.stream().map(mapping).collect(Collectors.toList());
    }

    public static String mapToString(Map<String, String> map, String entrySeparator, String keyValueSeparator) {
        if (map == null || map.isEmpty()) {
            return "";
        }

        return map.entrySet().stream()
            .map(entry -> entry.getKey() + keyValueSeparator + entry.getValue())
            .collect(Collectors.joining(entrySeparator));
    }

    public static String mapToString(Map<String, String> map,
                                     String entrySeparator,
                                     String keyValueSeparator,
                                     String start,
                                     String end) {
        String content = mapToString(map, entrySeparator, keyValueSeparator);
        return content.isEmpty() ? "" : start + content + end;
    }

    public static byte[] getUtf8(String content) {
        return isEmpty(content) ? new byte[0] : content.getBytes(Charset.forName("UTF-8"));
    }
}