public class Booking {
    private String id;
    private User user;
    private Resource resource;
    private FFDateTime start;
    private FFDateTime end;
    private BookingStatus status;
    private Money calculatedPrice;
    private Payment payment;

    public Booking(String id, User user, Resource resource,
                   FFDateTime start, FFDateTime end, Money calculatedPrice) {
        if (end.compareTo(start) <= 0) {
            throw new IllegalArgumentException("End must be after start time");
        }
        this.id = id;
        this.user = user;
        this.resource = resource;
        this.start = start;
        this.end = end;
        this.calculatedPrice = calculatedPrice;
        this.status = BookingStatus.Pending;
        this.payment = null;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public FFDateTime getStart() {
        return start;
    }

    public void setStart(FFDateTime start) {
        if (end != null && end.compareTo(start) <= 0) {
            throw new IllegalArgumentException("End must be before end");
        }
    }

    public FFDateTime getEnd() {
        return end;
    }

    public void setEnd(FFDateTime end) {
        if (start != null && end.compareTo(start) <= 0) {
            throw new IllegalArgumentException("Start must be after start");
        }
        this.end = end;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public Money getCalculatedPrice(Money calculatedPrice) {
        return calculatedPrice;
    }

    public void setCalculatedPrice(Money calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public int durationMinutes() {
        return (int) (end.toEpochMinutes() - start.toEpochMinutes());
    }

    public void changeStatus(BookingStatus newStatus) {
        if (!isStatusTransitionAllowed(this.status, newStatus)) {
            throw new IllegalStateException(
                    "Transition from " + this.status + " to " + newStatus + " not allowed");
        }
        this.status = newStatus;
    }

    private boolean isStatusTransitionAllowed(BookingStatus current, BookingStatus next) {
        switch (current) {
            case Pending:
                return (next == BookingStatus.Confirmed || next == BookingStatus.Cancelled);
            case Confirmed:
                return (next == BookingStatus.Completed || next == BookingStatus.Cancelled);
            case Completed:
            case Cancelled:
                return false;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "Booking [id= " + id + ", user= " + user + ", resource= " + resource + ", " +
                "start= " + start + ", end= " + end + ", status= " + status +
                ", calculatedPrice= " + calculatedPrice + ", payment= " + payment + "]";
    }
}
