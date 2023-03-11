package org.example.models;

import org.apache.commons.lang3.StringUtils;
import org.example.services.Passport;

/**
 * Realization of the passport of a citizen of Russia
 */
public class RussianPassport implements Passport {
    private String series;
    private String number;

    /**
     * Creating a new object with specific values
     * @param series passport series
     * @param number passport number
     * @throws IllegalArgumentException if there are more or less digits in the number or series
     * @throws NumberFormatException in case the series or number contains non-digits
     */
    public RussianPassport(String series, String number) {
        if (series.length() != 4)
            throw new IllegalArgumentException("The length for passport's series is invalid");
        if (number.length() != 6)
            throw new IllegalArgumentException("The length for passport's number is invalid");
        if (!StringUtils.isNumeric(series))
            throw new NumberFormatException("The series is not a number");
        if (!StringUtils.isNumeric(number))
            throw new NumberFormatException("The number for passport is not a number");
        this.series = series;
        this.number = number;
    }

    /**
     * Returns the correct full passport number
     * @return passport number (series + number, 10 digits in total)
     */
    public String getNumber() {
        return series + number;
    }
}
