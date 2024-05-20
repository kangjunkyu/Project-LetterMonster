package com.lemon.backend.global.badWord;

import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

public class BadWordFilterUtil extends HashSet<String> implements BadWords {
    private String substituteValue = "*";
    String[] delimiters = { " ", ",", ".", "!", "?", "@", "1", "2", "3", "4","5", "6",
            "7", "8" ,"9", "0", "^", "&", "*", "%", "$", "#", "(", ")", "-", "=", "_",
            "{", "}", "[", "'\'", "|" ,"/", "]", "~", "`", "ㅣ", "ㅏ", "ㅗ", "ㅅ"};

    public BadWordFilterUtil() {
        addAll(List.of(koreaBadWords));
    }

    public BadWordFilterUtil(String substituteValue) {
        this.substituteValue = substituteValue;
        addAll(List.of(koreaBadWords));
    }

    private String buildPatternText() {
        StringBuilder delimiterBuilder = new StringBuilder("[");
        for (String delimiter : delimiters) {
            delimiterBuilder.append(Pattern.quote(delimiter));
        }
        delimiterBuilder.append("]*");
        return delimiterBuilder.toString();
    }

    public String change(String text) {
        String[] badWords = stream().filter(text::contains).toArray(String[]::new);
        for (String v : badWords) {
            String sub = this.substituteValue.repeat(v.length());
            text = text.replace(v, sub);
        }
        return text;
    }

    public boolean check(String text) {
        return stream().anyMatch(text::contains);
    }

    public boolean checkBadWord(String text) {
        String patternText = buildPatternText();
        return stream().anyMatch(badWord -> {
            String pattern = String.join(patternText, badWord.split(""));
            return Pattern.compile(pattern).matcher(text).find();
        });
    }
}
