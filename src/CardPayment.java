public class CardPayment extends Payment {
    private Card card;

    public CardPayment(Card card, Money amount, String paymentId) {
        super(amount, paymentId);
        this.card = card;
    }
    @Override
    public void capture(){
        System.out.println("Capturing Card: " + card + ", amount: " + amount);
        this.status = PaymentStatus.CAPTURED;
    }
    @Override
    public void refund(){
        if(status == PaymentStatus.CAPTURED){
            System.out.println("Refunding Card: " + card.getCardNumber() + ", amount: " + amount);
            this.status = PaymentStatus.REFUNDED;
        } else {
            throw new IllegalStateException("Cannot refund before capture");
        }
    }
    public Card getCard() {
        return card;
    }
}
