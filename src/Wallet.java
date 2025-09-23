public class Wallet {
    private String walletId;

    public Wallet(String walletId, String ownerName) {
        this.walletId = walletId;
    }

    public String getWalletId() {
        return walletId;
    }

    @Override
    public String toString() {
        return "Wallet id=" + walletId;
    }
}
