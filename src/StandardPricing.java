import java.math.BigDecimal;
import java.math.RoundingMode;

public class StandardPricing implements PricingPolicy {
    public Money price(Booking booking) {
        int minutes = booking.durationMinutes();
        Money hourlyRate = booking.getResource().hourlyRate();
        BigDecimal perMinute = hourlyRate.getAmount()
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
        BigDecimal total = perMinute.multiply(BigDecimal.valueOf(minutes))
                .setScale(2, RoundingMode.HALF_UP);
        return Money.of(total);
    }
}
