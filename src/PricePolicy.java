public class PricePolicy implements PricingPolicy {

    @Override
    public Money price(Booking booking) {
        Money pricePerHour = booking.getResource().getCustomHourlyRate();
        int minutes = booking.getStart().minutesUntil(booking.getEnd());
        double hours = minutes / 60.0;
        return pricePerHour.multiply(hours);
    }
}
