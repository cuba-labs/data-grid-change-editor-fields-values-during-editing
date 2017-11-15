package com.company.sample.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|product")
@Table(name = "SAMPLE_ORDER_ITEM")
@Entity(name = "sample$OrderItem")
public class OrderItem extends StandardEntity {
    private static final long serialVersionUID = 6309067197919791439L;

    @Column(name = "PRODUCT", nullable = false)
    protected String product;

    @NotNull
    @Column(name = "PRICE", nullable = false)
    protected BigDecimal price;

    @Column(name = "QUANTITY", nullable = false)
    protected Integer quantity;

    @Column(name = "TOTAL")
    protected BigDecimal total;

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProduct() {
        return product;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }


}