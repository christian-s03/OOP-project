import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportingService {
    private final BookingRepository bookingRepo;

    public ReportingService(BookingRepository bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public Map<Resource, Double> utilization(FFDateTime from, FFDateTime to) {
        List<Booking> bookings = bookingRepo.findAll();

        Map<Resource, Double> bookedMinutes = new HashMap<>();
        for (Booking booking : bookings) {
            if (booking.getStatus() == BookingStatus.Confirmed || booking.getStatus() == BookingStatus.Completed) {
                FFDateTime start = max(booking.getStart(), from);
                FFDateTime end = min(booking.getEnd(), to);
                if (!end.isAfter(start)) continue;

                int duration = end.toEpochMinutes() - start.toEpochMinutes();
                bookedMinutes.put(booking.getResource(),
                        bookedMinutes.getOrDefault(booking.getResource(), 0.0) + duration);
            }
        }
        long totalMinutes = to.toEpochMinutes() - from.toEpochMinutes();
        Map<Resource, Double> result = new HashMap<>();

        for (Resource r : bookedMinutes.keySet()) {
            double utilization = ((double) bookedMinutes.get(r) / totalMinutes) * 100.0;
            utilization = new BigDecimal(utilization).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            result.put(r, utilization);
        }
        return result;
    }

    public Map<String, Money> revenueByResource(FFDateTime from, FFDateTime to) {
        List<Booking> bookings = bookingRepo.findAll();

        Map<String, Money> revenue = new HashMap<>();

        for (Booking b : bookings) {
            if (b.getPayment() != null && b.getPayment().getStatus() == PaymentStatus.CAPTURED) {
                FFDateTime start = max(b.getStart(), from);
                FFDateTime end = min(b.getEnd(), to);
                if (!end.isAfter(start)) continue;

                Money current = revenue.getOrDefault(b.getResource().getName(), Money.of(0));
                current = current.add(b.getCalculatedPrice());
                revenue.put(b.getResource().getName(), current);
            }
        }

        return revenue;
    }

    public Money totalRevenue(FFDateTime from, FFDateTime to) {
        return revenueByResource(from, to).values().stream()
                .reduce(Money.of(0), Money::add);
    }

    private FFDateTime max(FFDateTime a, FFDateTime b) {
        return a.isAfter(b) ? a : b;
    }

    private FFDateTime min(FFDateTime a, FFDateTime b) {
        return a.isBefore(b) ? a : b;
    }
}

