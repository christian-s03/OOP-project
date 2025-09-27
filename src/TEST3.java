public class TEST3 {
    public static void main(String[] args) {

        UserRepository userRepo = new InMemoryUserRepository();
        ResourceRepository resourceRepo = new InMemoryResourceRepository();
        BookingRepository bookingRepo = new InMemoryBookingRepository();

        IndividualUser anna = new IndividualUser("anna@ex.com", "Anna Nowak");
        userRepo.add(anna);

        Desk hot1 = new Desk("Hot-1", Money.of(25), Desk.DeskType.HOT);
        resourceRepo.add(hot1);

        PricingPolicy pricingPolicy = new HappyHoursPricing();
        Discountable discount = (money, user) -> money;

        BookingService bookingService = new BookingService(userRepo, resourceRepo, bookingRepo, pricingPolicy, discount);

        FFDateTime start = new FFDateTime(2025, 9, 17, 14, 0);
        FFDateTime end = new FFDateTime(2025, 9, 17, 16, 0);

        Booking booking = bookingService.book(anna, hot1, start, end);

        System.out.println("Book " + anna.getEmail() + " " + hot1.getName() + " " +
                start + " " + end + " → " + booking.getCalculatedPrice());
    }
}
