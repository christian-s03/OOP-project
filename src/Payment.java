public abstract class Payment {
    protected Money amount;
    protected String paymentId;
    protected PaymentStatus status;

    public Payment(Money amount, String paymentId) {
        this.amount = amount;
        this.paymentId = paymentId;
        this.status = PaymentStatus.INITIATED;
    }

    public Money getAmount() {
        return amount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public abstract void capture();

    public abstract void refund();
}
