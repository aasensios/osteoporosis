package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that validates some properties.
 *
 * @author Alejandro Asensio
 * @version 1.0, 2019-01-16
 */
public class Validator {

    // Attributes
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final String[] DOMAIN_TYPES = {"com", "net", "org"};
    private static final String[] BAD_WORDS = {"tonto", "burro", "capullo", "zoquete", "alcornoque", "looser"};
    public static final Map<String, String> ERR_MESSAGE = new HashMap<String, String>() {
        {
            put("empty", "empty field");
            put("invalid", "invalid field");
            put("capitalized", "name must be capitalized");
            put("too_long", "too long field");
            put("invalid_email", "email is invalid");
            put("invalid_website", "website is invalid");
            put("bad_words", "comments contains some bad words");
        }
    };

    // Methods
    /**
     * Checks if the string is not empty.
     *
     * @param s String to be checked
     * @return boolean
     */
    public static boolean isNotEmpty(String s) {
        return !s.isEmpty();
    }

    /**
     * Checks if the first letter is upper case.
     *
     * @param s String to be checked
     * @return boolean
     */
    public static boolean isCapitalized(String s) {
        char firstLetter = s.charAt(0);
        return Character.isUpperCase(firstLetter);
    }

    /**
     * Checks if the string has a length less or equal than maxLength.
     *
     * @param s String to be checked
     * @param minLength maximum valid length for the string s
     * @return boolean
     */
    public static boolean hasMinimumLength(String s, int minLength) {
        return s.length() >= minLength;
    }

    /**
     * Checks if the domain is in the attribute array domains.
     *
     * @param s String to be checked
     * @return boolean
     */
    public static boolean hasValidDomainType(String s) {
        String domain = s.substring(s.length() - 3);
        return Arrays.asList(DOMAIN_TYPES).contains(domain);
    }

    /**
     * Checks if the email matches the pattern VALID_EMAIL_ADDRESS_REGEX, which
     * is 'user@domainName.domainType'.
     *
     * @param email String to be checked
     * @return boolean
     */
    public static boolean isValidEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find() && hasValidDomainType(email);
    }

    /**
     * Checks if the website matches the pattern 'site.domainType'
     *
     * @param website String to be checked
     * @return boolean
     */
    public static boolean isValidWebsite(String website) {
        //split website into site.domainType
        String site = website.split("\\.")[0]; //dot must be scaped with '\\.'
        String domainType = website.split("\\.")[1];

        //all the conditions must be true
        return (isNotEmpty(site) && hasValidDomainType(domainType));
    }

    /**
     * Checks if the text contains some bad words (listed in the attribute
     * BAD_WORDS)
     *
     * @param s String to be checked
     * @return boolean
     */
    public static boolean containsBadWords(String s) {
        return Arrays.asList(BAD_WORDS).contains(s);
    }

}
