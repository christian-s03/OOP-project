import java.util.Set;

public class TEST7 {
    public static void main(String[] args) {

        UserRepository userRepo = new InMemoryUserRepository();
        ResourceRepository resourceRepo = new InMemoryResourceRepository();
        BookingRepository bookingRepo = new InMemoryBookingRepository();


        PricingPolicy pricingPolicy = new PricePolicy();
        Discountable discount = new NoDiscount();

        BookingService bookingService = new BookingService(userRepo, resourceRepo, bookingRepo, pricingPolicy, discount);

        Room salaAlfa = new Room("Sala Alfa", Money.of(80), 12, Set.of("Projektor"));
        Room salaBeta = new Room("Sala Beta", Money.of(60), 8, Set.of("Projektor"));
        resourceRepo.add(salaAlfa);
        resourceRepo.add(salaBeta);

        CompanyUser acme = new CompanyUser("biuro@acme.pl", "ACME Sp. z o.o.", "5211234567");
        CompanyUser betaUser = new CompanyUser("kontakt@beta.com", "Beta Sp. z o.o.", "5219876543");
        userRepo.add(acme);
        userRepo.add(betaUser);

        bookingService.book(acme, salaAlfa,
                new FFDateTime(2025, 9, 15, 10, 0),
                new FFDateTime(2025, 9, 15, 12, 0));
        bookingService.book(betaUser, salaBeta,
                new FFDateTime(2025, 9, 16, 9, 0),
                new FFDateTime(2025, 9, 16, 11, 0));
        bookingService.book(acme, salaAlfa,
                new FFDateTime(2025, 9, 18, 14, 0),
                new FFDateTime(2025, 9, 18, 16, 0));

        System.out.println("===== REPORT: UTILIZATION =====");
        for (Resource r : resourceRepo.findAll()) {
            double totalMinutes = 0;
            double bookedMinutes = 0;
            FFDateTime periodStart = new FFDateTime(2025, 9, 15, 0, 0);
            FFDateTime periodEnd   = new FFDateTime(2025, 9, 20, 0, 0);
            totalMinutes = periodEnd.minutesUntil(periodStart) * -1; // liczba minut w okresie
            for (Booking b : bookingService.list(null, r, null)) {
                FFDateTime bStart = b.getStart();
                FFDateTime bEnd   = b.getEnd();
                FFDateTime effectiveStart = bStart.isBefore(periodStart) ? periodStart : bStart;
                FFDateTime effectiveEnd   = bEnd.isAfter(periodEnd) ? periodEnd : bEnd;
                bookedMinutes += effectiveEnd.minutesUntil(effectiveStart) * -1;
            }
            double utilization = bookedMinutes / totalMinutes * 100.0;
            System.out.printf("%s: %.2f%% booked%n", r.getName(), utilization);
        }

        System.out.println("===== REPORT: REVENUE =====");
        FFDateTime revStart = new FFDateTime(2025, 9, 1, 0, 0);
        FFDateTime revEnd   = new FFDateTime(2025, 9, 30, 23, 59);
        Money totalRevenue = Money.of(0);
        for (Booking b : bookingRepo.findAll()) {
            if (!b.getStart().isBefore(revStart) && !b.getEnd().isAfter(revEnd)) {
                totalRevenue = totalRevenue.add(b.getCalculatedPrice());
            }
        }
        System.out.println("Total revenue: " + totalRevenue);

        for (Resource r : resourceRepo.findAll()) {
            Money revenuePerResource = Money.of(0);
            for (Booking b : bookingService.list(null, r, null)) {
                if (!b.getStart().isBefore(revStart) && !b.getEnd().isAfter(revEnd)) {
                    revenuePerResource = revenuePerResource.add(b.getCalculatedPrice());
                }
            }
            System.out.println(r.getName() + " revenue: " + revenuePerResource);
        }
    }
}
