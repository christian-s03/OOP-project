import java.util.*;
import java.util.stream.Collectors;

public class InMemoryBookingRepository implements BookingRepository {
    private final Map<String, Booking> bookingsById = new HashMap<>();

    @Override
    public void add(Booking b) {
        bookingsById.put(b.getId(), b);
    }

    @Override
    public Optional<Booking> findById(String id) {
        return Optional.ofNullable(bookingsById.get(id));
    }

    @Override
    public List<Booking> findAll() {
        return new ArrayList<>(bookingsById.values());
    }

    @Override
    public List<Booking> findByResource(Resource r) {
        return bookingsById.values().stream()
                .filter(b -> b.getUser().equals(r))
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findByUser(User u) {
        return bookingsById.values().stream()
                .filter(b -> b.getUser().equals(u))
                .collect(Collectors.toList());
    }
}
