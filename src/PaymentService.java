public class PaymentService {

    private final BookingRepository bookingRepo;

    public PaymentService(BookingRepository bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public Payment pay(String bookingId, Card card) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        Payment payment = new CardPayment(card, booking.getCalculatedPrice(), "Pay- " + bookingId);
        payment.capture();
        booking.setPayment(payment);
        return payment;
    }
    public Payment pay(String bookingId, Wallet wallet) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        Payment payment = new WalletPayment(wallet, booking.getCalculatedPrice(), "Pay- " + bookingId);
        payment.capture();
        booking.setPayment(payment);
        return payment;
    }
    public void refund(String bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        Payment payment = booking.getPayment();

        if (booking.getStatus() == BookingStatus.Cancelled && payment != null) {
            payment.refund();
            booking.setPayment(null);
        } else {
            throw new IllegalArgumentException("Booking refund not possible");
        }
    }
}