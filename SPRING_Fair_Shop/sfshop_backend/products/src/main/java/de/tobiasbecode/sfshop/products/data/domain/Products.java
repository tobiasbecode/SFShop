package de.tobiasbecode.sfshop.products.data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Component
@Table(name = "products")
@NoArgsConstructor
public class Products {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="uuid")
    private byte[] uuid;

    @Column(name="name")
    private String name;

    @Column(name="price")
    private double price;

    @Column(name="category")
    private String category;

    @Lob
    @Column(name="description")
    private String description;

    @Column(name="offer")
    private boolean offer;

    @Column(name="shippingcosts")
    private double shippingCosts;

    @Column(name="inventory")
    private int inventory;

    @Column(name="imagePath")
    private String imagePath;
}
