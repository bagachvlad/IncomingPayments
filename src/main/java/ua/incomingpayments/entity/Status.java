package ua.incomingpayments.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "transaction_generator")
    @SequenceGenerator(name = "transaction_generator" , sequenceName = "transaction_seq" , allocationSize = 150)
    @Column( updatable = false ,
            nullable = false ,
            columnDefinition = "smallserial")
    private Short id;

    @Column(
            nullable = false ,
            unique = true ,
            columnDefinition = "text")
    private String value;

    public Status() {
    }

    public Status(Short id, String value) {
        this.id = id;
        this.value = value;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Status)) return false;
        Status status = (Status) o;
        return getId().equals(status.getId()) && getValue().equals(status.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getValue());
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
