public class TEST6 {
    public static void main(String[] args) {
        UserRepository userRepo = new InMemoryUserRepository();
        ResourceRepository resourceRepo = new InMemoryResourceRepository();
        BookingRepository bookingRepo = new InMemoryBookingRepository();

        PricingPolicy pricingPolicy = new StandardPricing();
        Discountable discount = new NoDiscount();

        BookingService bookingService = new BookingService(
                userRepo, resourceRepo, bookingRepo, pricingPolicy, discount);

        CompanyUser acme = new CompanyUser("biuro@acme.pl", "ACME Sp. z o.o.", "5211234567");
        userRepo.add(acme);

        Device projektor = new Device("Projektor-1", Money.of(100), 2);
        resourceRepo.add(projektor);

        FFDateTime start = new FFDateTime(2025, 9, 20, 10, 0);
        FFDateTime end = new FFDateTime(2025, 9, 20, 12, 0);

        Booking b1 = bookingService.book(acme, projektor, start, end);
        System.out.println("Book " + projektor.getName() + " → " + b1.getCalculatedPrice());

        try {
            Booking b2 = bookingService.book(acme, projektor, start, end);
            System.out.println("Book " + projektor.getName() + " → " + b2.getCalculatedPrice());
        } catch (IllegalStateException e) {
            System.out.println("ERROR: resource not available");
        }
    }
}
