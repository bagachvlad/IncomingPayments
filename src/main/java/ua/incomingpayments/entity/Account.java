package ua.incomingpayments.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "accounts")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "transaction_generator")
    @SequenceGenerator(name = "transaction_generator" , sequenceName = "transaction_seq" , allocationSize = 150)
    @Column( updatable = false ,
            nullable = false ,
            columnDefinition = "INTEGER")
    private Integer id;

    @Column(
            nullable = false ,
            columnDefinition = "numeric(10, 2)")
    private BigDecimal amount;

    public Account() {
    }

    public Account(Integer id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return getId().equals(account.getId()) && getAmount().equals(account.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAmount());
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
