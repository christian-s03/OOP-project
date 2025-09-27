import java.util.Set;

public class TEST1 {
    public static void main(String[] args) {

        UserRepository userRepo = new InMemoryUserRepository();
        ResourceRepository resourceRepo = new InMemoryResourceRepository();
        BookingRepository bookingRepo = new InMemoryBookingRepository();


        PricingPolicy pricingPolicy = new PricePolicy();
        Discountable discount = (money, user) -> money;


        BookingService bookingService = new BookingService(
                userRepo, resourceRepo, bookingRepo, pricingPolicy, discount);

        Room salaAlfa = new Room("Sala Alfa", Money.of(80), 12, Set.of("Projektor"));
        resourceRepo.add(salaAlfa);

        CompanyUser acme = new CompanyUser("biuro@acme.pl", "ACME Sp. z o.o.", "5211234567");
        userRepo.add(acme);

        FFDateTime start1 = new FFDateTime(2025, 9, 15, 10, 0);
        FFDateTime end1 = new FFDateTime(2025, 9, 15, 12, 0);

        Booking booking1 = bookingService.book(acme, salaAlfa, start1, end1);
        System.out.println("BOOK " + acme.getEmail() + " \"" + salaAlfa.getName() + "\" " +
                start1 + " " + end1 + " → " + booking1.getStatus() +
                ", price " + booking1.getCalculatedPrice());

        bookingService.confirm(booking1.getId());
        System.out.println("Confirm = " + booking1.getStatus());

        PaymentService paymentService = new PaymentService(bookingRepo);
        Card card = new Card("4242", "ACME Sp. z o.o.ACME Sp. z o.o.");
        Payment payment = paymentService.pay(booking1.getId(), card);
        System.out.println("Payment captured method = CARD " + card);

        System.out.println("Invoice total = " + booking1.getCalculatedPrice() + " buyer = " + acme.getCompanyName());

        FFDateTime start2 = new FFDateTime(2025, 9, 16, 9, 0);
        int durationMinutes = 90;
        Booking booking2 = bookingService.book(acme, salaAlfa, start2, durationMinutes);
        System.out.println("Book " + acme.getEmail() + " " + salaAlfa.getName() + " " +
                start2 + " " + durationMinutes + " → " + booking2.getCalculatedPrice());

    }
}
