import java.math.BigDecimal;
import java.math.RoundingMode;

public class StudentDiscount implements Discountable {
    @Override
    public Money applyDiscount(Money base, User user) {
        if (user instanceof StudentUser) {
            BigDecimal discounted = base.getAmount().multiply(BigDecimal.valueOf(0.8));
            return new Money(discounted.setScale(2, RoundingMode.HALF_UP));
        }
        return base;
    }
}