public class Device extends Resource {
    private int quantity;
    public Device(String name, Money customHourlyRate, int quantity) {
        super(name, customHourlyRate);
        this.quantity = quantity;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @Override
    protected Money baseRatePerHour() {
        return Money.of(10.0);
    }
    @Override
    public String describe() {
        return "Device: " + getName() + ", Quantity: " + quantity;
    }
}
