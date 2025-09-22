public class WalletPayment extends Payment {

    private User user;

    public WalletPayment(Money amount, String paymentId, User user) {
        super(amount, paymentId);
        this.user = user;
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
