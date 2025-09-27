import java.util.Set;

public class TEST5 {
    public static void main(String[] args) {

        UserRepository userRepo = new InMemoryUserRepository();
        ResourceRepository resourceRepo = new InMemoryResourceRepository();
        BookingRepository bookingRepo = new InMemoryBookingRepository();

        PricingPolicy pricingPolicy = new StandardPricing();
        Discountable discount = (money, user) -> money;

        BookingService bookingService = new BookingService(
                userRepo, resourceRepo, bookingRepo, pricingPolicy, discount);

        Room salaOmega = new Room("Sala Omega", Money.of(80), 10, Set.of("TV"));
        resourceRepo.add(salaOmega);

        CompanyUser acme = new CompanyUser("biuro@acme.pl", "ACME Sp. z o.o.", "5211234567");
        userRepo.add(acme);

        Wallet wallet = new Wallet("WALLET-1234", "Laura Nowak");

        FFDateTime start = new FFDateTime(2025, 9, 20, 10, 0);
        FFDateTime end   = new FFDateTime(2025, 9, 20, 12, 0);

        Booking booking = bookingService.book(acme, salaOmega, start, end);
        System.out.println("Book " + acme.getEmail() + " " + salaOmega.getName() + " " +
                start + " " + end + " → " + booking.getStatus() +
                ", price " + booking.getCalculatedPrice());

        bookingService.confirm(booking.getId());
        System.out.println("Confirm = " + booking.getStatus());

        PaymentService paymentService = new PaymentService(bookingRepo);
        Payment walletPayment = paymentService.pay(booking.getId(), wallet);
        System.out.println("Payment captured method=WALLET " + wallet.getWalletId());

        bookingService.cancel(booking.getId());
    }
}