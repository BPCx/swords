package de.meisterfuu.sip.portal.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by meisterfuu.
 */
@Entity
public class Recipe implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name", length = 500)
    private String name;

    @Column(name = "description", length = 10000)
    private String description;

    @ElementCollection
    private Set<WebLink> webLinks = new HashSet<>();

    @ElementCollection
    private List<Ingredient> ingredients = new ArrayList<>();

    @Column(name = "coverURL", length = 500)
    private String coverPictureURL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<WebLink> getWebLinks() {
        return webLinks;
    }

    public void setWebLinks(Set<WebLink> webLinks) {
        this.webLinks = webLinks;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getCoverPictureURL() {
        return coverPictureURL;
    }

    public void setCoverPictureURL(String coverPictureURL) {
        this.coverPictureURL = coverPictureURL;
    }
}
