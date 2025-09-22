import java.math.BigDecimal;

public class StudentDiscount implements Discountable {
    @Override
    public Money applyDiscount(Money base, User user) {
        if (user instanceof IndividualUser) {
            IndividualUser iu = (IndividualUser) user;
            if (iu.isStudent()) {
                BigDecimal discounted = base.getAmount().multiply(BigDecimal.valueOf(0.8));
                return Money.of(discounted);
            }
        }
        return base;
    }
}
