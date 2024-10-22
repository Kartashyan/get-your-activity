package com.getyourguide.demo.domain;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity {
    private Long id;
    private String title;
    private int price;
    private String currency;
    private double rating;
    private boolean specialOffer;
    private Supplier supplier;

    @JsonProperty("supplierId")
    private void unpackSupplier(Long supplierId) {
        this.supplier = new Supplier();
        this.supplier.setId(supplierId);
    }
}