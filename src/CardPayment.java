public class CardPayment extends Payment {
    private String last4;

    public CardPayment(Money amount, String paymentId, String last4) {
        super(amount, paymentId);
        this.last4 = last4;
    }
    @Override
    public void capture(){
        if (status != PaymentStatus.INITIATED) {
            throw new IllegalStateException("Payment is not initiated");
        }
        status = PaymentStatus.CAPTURED;
    }

    @Override
    public void refund() {
        if (status != PaymentStatus.CAPTURED) {
            throw new IllegalStateException("Payment is not captured");
        }
        status = PaymentStatus.REFUNDED;
    }
}
