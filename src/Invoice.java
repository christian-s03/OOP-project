public class Invoice {
    private String invoiceNumber;
    private FFDateTime issueDate;
    private User buyer;
    private Money total;
    private String itemDescription;

    public Invoice(String invoiceNumber, FFDateTime issueDate, User buyer, Money total, String itemDescription) {
        this.invoiceNumber = invoiceNumber;
        this.issueDate = issueDate;
        this.buyer = buyer;
        this.total = total;
        this.itemDescription = itemDescription;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public FFDateTime getIssueDate() {
        return issueDate;
    }

    public User getBuyer() {
        return buyer;
    }

    public Money getTotal() {
        return total;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    @Override
    public String toString() {
        return "Invoice [invoiceNumber=" + invoiceNumber + ", issueDate=" + issueDate +
                ", buyer=" + buyer + ", total=" + total + ", itemDescription=" + itemDescription + "]";
    }
}
