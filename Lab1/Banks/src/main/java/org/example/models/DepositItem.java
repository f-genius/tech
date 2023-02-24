package org.example.models;

/**
 * class for working with interest for deposits (defines upper and lower bounds and percentage)
 */
public class DepositItem {
    private long percent;
    private long upperLimit;
    private long lowerLimit;

    public DepositItem(long _percent, long _upperLimit, long _lowerLimit) {
        if (_lowerLimit >= _upperLimit)
            throw new IllegalArgumentException("Lower limit should be less than upper limit");
        if (_lowerLimit < 0)
            throw new IllegalArgumentException("Lower limit can't be negative");
        if (_percent < 0)
            throw new IllegalArgumentException("The percent can't be negative");
        percent = _percent;
        upperLimit = _upperLimit;
        lowerLimit = _lowerLimit;
    }

    /**
     * returns a percentage
     *
     * @return percentage
     */
    public long getPercent() {
        return percent;
    }

    /**
     * returns an upper limit
     *
     * @return upper limit
     */
    public long getUpperLimit() {
        return upperLimit;
    }

    /**
     * returns a lower limit
     *
     * @return lower limit
     */
    public long getLowerLimit() {
        return lowerLimit;
    }
}
