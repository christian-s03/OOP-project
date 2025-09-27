import java.util.Set;

public class TEST2 {
    public static void main(String[] args) {

        UserRepository userRepo = new InMemoryUserRepository();
        ResourceRepository resourceRepo = new InMemoryResourceRepository();
        BookingRepository bookingRepo = new InMemoryBookingRepository();


        PricingPolicy pricingPolicy = new PricePolicy();
        Discountable discount = (money, user) -> money;

        BookingService bookingService = new BookingService(userRepo, resourceRepo, bookingRepo, pricingPolicy, discount);

        Room salaAlfa = new Room("Sala Alfa", Money.of(80), 12, Set.of("Projektor"));
        resourceRepo.add(salaAlfa);
        Room salaBeta = new Room("Sala Beta", Money.of(60), 8, Set.of("Projektor"));
        resourceRepo.add(salaBeta);
        CompanyUser acme = new CompanyUser("biuro@acme.pl", "ACME Sp. z o.o.", "5211234567");
        userRepo.add(acme);

        FFDateTime start1 = new FFDateTime(2025, 9, 15, 10, 0);
        FFDateTime end1 = new FFDateTime(2025, 9, 15, 12, 0);
        Booking booking1 = bookingService.book(acme, salaAlfa, start1, end1);
        bookingService.confirm(booking1.getId());
        System.out.println("BOOK " + acme.getEmail() + " " + salaAlfa.getName() + " " +
                start1 + " " + end1 + " → " + booking1.getStatus() +
                ", price " + booking1.getCalculatedPrice());
        System.out.println("Confirm = " + booking1.getStatus());

        FFDateTime collisionStart  = new FFDateTime(2025, 9, 15, 11, 0);
        FFDateTime collisionEnd = new FFDateTime(2025, 9, 15, 13, 0);

        try {
            bookingService.book(acme, salaBeta, collisionStart , collisionEnd);
        } catch (IllegalStateException e) {
            System.out.println("ERROR: resource not available");
        }

        try {
            Booking booking2 = bookingService.book(acme, salaBeta, collisionStart , collisionEnd);
            System.out.println("Book " + acme.getEmail() + " " + salaBeta.getName() + " " +
                    collisionStart  + " " + collisionEnd + " → " + booking2.getCalculatedPrice());
        } catch (IllegalStateException e) {
            System.out.println("ERROR: resource not available");
        }
    }
}
