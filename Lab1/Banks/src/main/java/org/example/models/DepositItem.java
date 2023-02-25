package org.example.models;

/**
 * class for working with interest for deposits (defines upper and lower bounds and percentage)
 */
public class DepositItem {
    private long percent;
    private long upperLimit;
    private long lowerLimit;

    public DepositItem(long percent, long upperLimit, long lowerLimit) {
        if (lowerLimit >= upperLimit)
            throw new IllegalArgumentException("Lower limit should be less than upper limit");
        if (lowerLimit < 0)
            throw new IllegalArgumentException("Lower limit can't be negative");
        if (percent < 0)
            throw new IllegalArgumentException("The percent can't be negative");
        this.percent = percent;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
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
