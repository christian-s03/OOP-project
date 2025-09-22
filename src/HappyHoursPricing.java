import java.math.BigDecimal;

public class HappyHoursPricing implements PricingPolicy {
    @Override
    public Money price(Booking booking) {
        StandardPricing standardPricing = new StandardPricing();
        Money basePrice = standardPricing.price(booking);

        int startHour = booking.getStart().hour;
        if (startHour >= 14 && startHour < 16) {
            BigDecimal discounted = basePrice.getAmount().multiply(BigDecimal.valueOf(0.7));
            return new Money(discounted);
        } else {
            return basePrice;
        }
    }
}
