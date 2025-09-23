import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookingService {
    private final UserRepository userRepo;
    private final ResourceRepository resourceRepo;
    private final BookingRepository bookingRepo;
    private final PricingPolicy pricingPolicy;
    private final Discountable discount;

    private int counter = 0;

    public BookingService(UserRepository userRepo,
                          ResourceRepository resourceRepo,
                          BookingRepository bookingRepo,
                          PricingPolicy pricingPolicy,
                          Discountable discount) {
        this.userRepo = userRepo;
        this.resourceRepo = resourceRepo;
        this.bookingRepo = bookingRepo;
        this.pricingPolicy = pricingPolicy;
        this.discount = discount;
    }

    public Booking book(User u, Resource r, FFDateTime start, FFDateTime end) {
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("End must be before start");
        }
        checkCollisions(r, start, end);

        Booking booking = new Booking(null, u, r, start, end, null);

        Money base = pricingPolicy.price(booking);
        Money finalPrice = discount.applyDiscount(base, u);
        booking.setCalculatedPrice(finalPrice);

        booking.setId(generateId(start));

        booking.changeStatus(BookingStatus.Pending);

        bookingRepo.add(booking);

        return booking;
    }

    public Booking book(User u, Resource r, FFDateTime start, int durationMinutes) {
        FFDateTime end = start.plusMinutes(durationMinutes);
        return book(u, r, end, start);
    }

    public void confirm(String bookingId) {
        Booking b = getBookingOrThrow(bookingId);
        b.changeStatus(BookingStatus.Confirmed);
    }

    public void cancel(String bookingId) {
        Booking b = getBookingOrThrow(bookingId);
        b.changeStatus(BookingStatus.Cancelled);
    }

    public void complete(String bookingId) {
        Booking b = getBookingOrThrow(bookingId);
        b.changeStatus(BookingStatus.Completed);
    }

    public List<Booking> list(User u, Resource r, BookingStatus status) {
        return bookingRepo.findAll().stream()
                .filter(b -> u == null || b.getUser().equals(u))
                .filter(b -> r == null || b.getResource().equals(r))
                .filter(b -> status == null || b.getStatus() == status)
                .collect(Collectors.toList());
    }

    private Booking getBookingOrThrow(String bookingId) {
        Optional<Booking> opt = bookingRepo.findById(bookingId);
        if (opt.isEmpty()) throw new IllegalArgumentException("Booking not found");
        return opt.get();
    }

    private String generateId(FFDateTime start) {
        counter++;

        String datePart = String.format("%04d%02d%02d", start.year, start.month, start.day);
        return "BK- " + datePart + "-" + counter;
    }

    private void checkCollisions(Resource r, FFDateTime start, FFDateTime end) {
        List<Booking> existing = bookingRepo.findByResource(r);

        if (r instanceof Room || r instanceof Desk) {
            for (Booking b : existing) {
                if (b.getStatus() == BookingStatus.Pending || b.getStatus() == BookingStatus.Confirmed) {
                    if (overlaps(b.getStart(), b.getEnd(), start, end)) {
                        throw new IllegalStateException("Resource already booked");
                    }
                }
            }
        } else if (r instanceof Device) {
            Device d = (Device) r;
            int overlapsCount = 0;
            for (Booking b : existing) {
                if (b.getStatus() == BookingStatus.Pending || b.getStatus() == BookingStatus.Confirmed) {
                    if (overlaps(b.getStart(), b.getEnd(), start, end)) {
                        overlapsCount++;
                    }
                }
            }
            if (overlapsCount > d.getQuantity()) {
                throw new IllegalStateException("Resource already booked");
            }
        }
    }

    private boolean overlaps(FFDateTime s1, FFDateTime e1, FFDateTime s2, FFDateTime e2) {
        return s1.isBefore(e2) && s2.isAfter(e1);
    }
}