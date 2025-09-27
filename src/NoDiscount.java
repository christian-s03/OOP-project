public class NoDiscount implements Discountable {
    @Override
    public Money applyDiscount(Money money, User user) {
        return money;
    }
}