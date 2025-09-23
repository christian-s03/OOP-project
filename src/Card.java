public class Card {
    private String cardNumber;

    public Card(String cardNumber, String holderName) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public String toString() {
        return "Card number=" + cardNumber;
    }
}