package br.com.brasilprev.teste.javachallenge.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "purchase_order")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "delivery_address")
    private String deliveryAddress;
    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Customer customer;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
    private Set<OrderItem> items;
}
