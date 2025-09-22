public class NoDiscount implements Discountable {
    @Override
    public Money applyDiscount(Money base, User user) {
        return base;
    }
}
