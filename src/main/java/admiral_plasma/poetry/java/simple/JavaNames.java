package admiral_plasma.poetry.java.simple;

public class JavaNames {

    public static String toClassName(String name) {

        if (name == null || name.isEmpty()) {
            return "Null";
        }
        return replaceSeperators(firstChar(name).toUpperCase() + rest(name));
    }

    private static String replaceSeperators(String string) {
        boolean upperCase = false;
        boolean lowerCase = false;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char nextChar = string.charAt(i);

            if (nextChar == '_' || nextChar == '-') // skip invalid chars. follow up will be upperCase
            {
                upperCase = true;
            } else if (upperCase == true) {
                // flag set to add UpperCase char
                result.append(Character.toUpperCase(nextChar));
                // never add twoUpperCaseAfterAnother
                lowerCase = true;
                upperCase = false;
            } else if (lowerCase == true) {
                // flag set to add lowerCase
                result.append(Character.toLowerCase(nextChar));
                // no more flags set
                lowerCase = false;
            } else {
                result.append(nextChar);
                if (Character.isUpperCase(nextChar)) {
                    // never add twoUpperCaseAfterAnother
                    lowerCase = true;
                }
            }
        }
        return result.toString();
    }

    private static String rest(String name) {
        return name.substring(1);
    }

    private static String firstChar(String name) {
        return name.substring(0, 1);
    }

    public static String toVariableName(String name) {

        if (name == null || name.isEmpty()) {
            return "null";
        }
        return replaceSeperators(firstChar(name).toLowerCase() + rest(name));
    }

    public static String toConstantName(String name) {

        if (name == null || name.isEmpty()) {
            return "NULL";
        }
        return addSeperators(name);
    }

    private static String addSeperators(String string) {
        boolean newWord = false;
        boolean lowerCase = false;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char nextChar = string.charAt(i);

            if (nextChar == '_' || nextChar == '-') {
                newWord = true;
                lowerCase = false;
            } else if (newWord == true) {
                result.append('_');
                result.append(Character.toUpperCase(nextChar));
                newWord = false;
            } else {
                if (Character.isUpperCase(nextChar) && lowerCase) {
                    // never add twoUpperCaseAfterAnother
                    result.append('_');
                    newWord = false;
                } else if (Character.isLowerCase(nextChar)) {
                    lowerCase = true;
                    newWord = false;
                }
                result.append(Character.toUpperCase(nextChar));
            }
        }
        return result.toString();
    }

}
