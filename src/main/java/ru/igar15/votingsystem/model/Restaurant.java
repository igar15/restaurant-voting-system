package ru.igar15.votingsystem.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "address"}, name = "restaurants_unique_name_address_idx")})
public class Restaurant extends AbstractNamedEntity {

    @NotBlank
    @Size(min = 10, max = 100)
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank
    @Column(name = "image_url")
    private String imageUrl;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, String address, String imageUrl) {
        super(id, name);
        this.address = address;
        this.imageUrl = imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name=" + name +
                ", address=" + address +
                ", imageUrl=" + imageUrl +
                '}';
    }
}