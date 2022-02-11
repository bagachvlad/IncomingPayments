package ua.incomingpayments.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_generator")
    @SequenceGenerator(name = "transaction_generator", sequenceName = "transaction_seq", allocationSize = 150)
    @Column(updatable = false,
            nullable = false,
            columnDefinition = "INTEGER")
    private Integer id;

    @Column(
            name = "request_id",
            nullable = false,
            columnDefinition = "UUID"
    )
    private UUID requestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_from")
    private Account accountFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_to")
    private Account accountTo;

    @Column(
            nullable = false,
            columnDefinition = "numeric(10, 2)")
    private BigDecimal amount;

    @Column(
            columnDefinition = "text")
    private String initiator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
    private Status status;

    @Transient
    private String description;

    public Transaction() {
    }

    public Transaction(Integer id, UUID requestId, Account accountFrom, Account accountTo, BigDecimal amount, String initiator, Status status) {
        this.id = id;
        this.requestId = requestId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.initiator = initiator;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return getId().equals(that.getId()) && Objects.equals(getRequestId(), that.getRequestId()) && getAccountFrom().equals(that.getAccountFrom()) && getAccountTo().equals(that.getAccountTo()) && getAmount().equals(that.getAmount()) && getInitiator().equals(that.getInitiator()) && getStatus().equals(that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRequestId(), getAccountFrom(), getAccountTo(), getAmount(), getInitiator(), getStatus());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", requestId=" + requestId +
                ", accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                ", amount=" + amount +
                ", initiator='" + initiator + '\'' +
                ", status=" + status +
                '}';
    }
}
