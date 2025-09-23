public class WalletPayment extends Payment {
    private Wallet wallet;

    public WalletPayment(Wallet wallet, Money amount, String paymentId) {
        super(amount, paymentId);
        this.wallet = wallet;
    }

    @Override
    public void capture() {
        System.out.println("Capturing payment from wallet " + wallet.getWalletId() + " amount: " + amount);
        this.status = PaymentStatus.CAPTURED;
    }

    @Override
    public void refund() {
        if (status == PaymentStatus.CAPTURED) {
            System.out.println("Refunding wallet " + wallet.getWalletId() + " amount: " + amount);
            this.status = PaymentStatus.REFUNDED;
        } else {
            throw new IllegalStateException("Cannot refund before capture");
        }
    }

    public Wallet getWallet() {
        return wallet;
    }
}
