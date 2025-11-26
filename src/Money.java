import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money implements Comparable<Money> {
    private static final String CURRENCY = "PLN";
    private static final int SCALE = 2;
    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        if (amount == null) {
            throw new NullPointerException("Amount cannot be null");
        }
        BigDecimal scaledAmount = amount.setScale(SCALE, RoundingMode.HALF_UP);
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amount = scaledAmount;
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount);
    }

    public static Money of(String amountString) {
        return new Money(new BigDecimal(amountString));
    }

    public static Money of(double amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    public Money add(Money other) {
        Objects.requireNonNull(other, "Other money cannot be null");
        return new Money(this.amount.add(other.amount));
    }

    public Money subtract(Money other) {
        Objects.requireNonNull(other, "Other money cannot be null");
        BigDecimal result = this.amount.subtract(other.amount);

        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        return new Money(result);
    }

    public Money multiply(BigDecimal multiplier) {
        Objects.requireNonNull(multiplier, "Multiplier cannot be null");
        return new Money(this.amount.multiply(multiplier));
    }

    public Money multiply(double multiplier) {
        return multiply(BigDecimal.valueOf(multiplier));
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return CURRENCY;
    }

    @Override
    public int compareTo(Money other) {
        return this.amount.compareTo(other.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        return amount.equals(money.amount);
    }
    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
    @Override
    public String toString() {
        return amount.toPlainString() + " " + CURRENCY;
    }
}
