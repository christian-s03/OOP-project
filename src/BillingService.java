public class BillingService implements Billable {
    private int counter = 0;

    @Override
    public Invoice toInvoice(Booking booking) {
        counter++;
        java.time.LocalDate now = java.time.LocalDate.now();
        FFDateTime issueDate = new FFDateTime(
                now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth(),
                0,
                0);

        String invoiceNumber = String.format("INV-%04d%02d%02d-%d",
                issueDate.year,
                issueDate.month,
                issueDate.day,
                counter);

        Invoice invoice = new Invoice(
                invoiceNumber,
                issueDate,
                booking.getUser(),
                booking.getCalculatedPrice(),
                "Booking for resource: " + booking.getResource());
        return invoice;
    }
}