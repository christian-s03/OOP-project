import java.util.Set;

public class TEST4 {
    public static void main(String[] args) {

        UserRepository userRepo = new InMemoryUserRepository();
        ResourceRepository resourceRepo = new InMemoryResourceRepository();
        BookingRepository bookingRepo = new InMemoryBookingRepository();

        PricingPolicy pricingPolicy = new StandardPricing();
        Discountable discount = new StudentDiscount();

        BookingService bookingService = new BookingService(userRepo, resourceRepo, bookingRepo, pricingPolicy, discount);

        Room salaAlfa = new Room("Sala Alfa", Money.of(80), 12, Set.of("Projektor"));
        resourceRepo.add(salaAlfa);

        StudentUser anna = new StudentUser("anna@ex.com", "Anna Nowak");
        CompanyUser acme = new CompanyUser("biuro@acme.pl", "ACME Sp. z o.o.", "5211234567");

        userRepo.add(anna);
        userRepo.add(acme);

        FFDateTime start1 = new FFDateTime(2025, 9, 18, 9, 0);
        FFDateTime end1 = new FFDateTime(2025, 9, 18, 11, 0);

        Booking booking1 = bookingService.book(anna, salaAlfa, start1, end1);
        System.out.println("BOOK " + anna.getEmail() + " " + salaAlfa.getName() + " " +
                start1 + " " + end1 + " → " + booking1.getCalculatedPrice());

        FFDateTime start2 = new FFDateTime(2025, 9, 18, 12, 0);
        FFDateTime end2 = new FFDateTime(2025, 9, 18, 14, 0);

        Booking booking2 = bookingService.book(acme, salaAlfa, start2, end2);
        System.out.println("BOOK " + acme.getEmail() + " " + salaAlfa.getName() + " " +
                start2 + " " + end2 + " → " + booking2.getCalculatedPrice());
    }
}
