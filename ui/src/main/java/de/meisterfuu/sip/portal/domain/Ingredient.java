package de.meisterfuu.sip.portal.domain;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
public class Ingredient implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "count")
    private float count;

    @Column(name = "unit")
    @Enumerated(EnumType.STRING)
    private Units unit;


    public enum Units {
        PIECES,
        GRAM,
        KILOGRAM,
        LITRE,
        MILLILITLRE
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getCount() {
        return count;
    }

    public void setCount(Float count) {
        this.count = count;
    }

    public void setCount(double count) {
        this.count = (float)count;
    }

    public Units getUnit() {
        return unit;
    }

    public void setUnit(Units unit) {
        this.unit = unit;
    }

}
