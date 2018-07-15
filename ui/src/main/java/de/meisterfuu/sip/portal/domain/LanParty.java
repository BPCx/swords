package de.meisterfuu.sip.portal.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by meisterfuu.
 */
@Entity
public class LanParty {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name", length = 500)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "member_lan")
    private Set<Member> members = new HashSet<>();

    @Column(name = "startDate")
    private LocalDate startDate = LocalDate.now();
    @Column(name = "endDate")
    private LocalDate endDate = LocalDate.now();

    @Column(name = "description", length = 10000)
    private String description;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address = new Address();

    @Column(name = "location", length = 500)
    private String location;

    @ElementCollection
    private Set<WebLink> webLinks = new HashSet<>();

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

    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<WebLink> getWebLinks() {
        return webLinks;
    }

    public void setWebLinks(Set<WebLink> webLinks) {
        this.webLinks = webLinks;
    }

    public String getCoverPictureURL() {
        return coverPictureURL;
    }

    public void setCoverPictureURL(String coverPictureURL) {
        this.coverPictureURL = coverPictureURL;
    }
}
