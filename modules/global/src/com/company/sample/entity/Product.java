package com.company.sample.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "SAMPLE_PRODUCT")
@Entity(name = "sample$Product")
public class Product extends StandardEntity {
    private static final long serialVersionUID = 1678628019947704165L;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @NotNull
    @Column(name = "PRICE", nullable = false)
    protected BigDecimal price;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }


}