package de.meisterfuu.sip.portal.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by meisterfuu.
 */
@Entity
public class Member {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "locale")
    private String locale;

    @Column(name = "passwordSalt")
    private String passwordSalt;
    @Column(name = "passwordHash")
    private String passwordHash;
    @Column(name = "role")
    private Role role;

    @Column(name = "comment", length = 10000)
    private String comment;

    @Column(name = "avatarURL", length = 500)
    private String avatarPictureURL;

    @Column(name = "rank", length = 500)
    private String rank;

    @Column(name = "joinDate")
    private LocalDate joinDate;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address = new Address();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "member_games")
    private Set<Game> games = new HashSet<>();

    @ManyToMany(mappedBy = "members", cascade = CascadeType.ALL)
    private Set<LanParty> lanPartys = new HashSet<>();


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

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public Set<LanParty> getLanPartys() {
        return lanPartys;
    }

    public void setLanPartys(Set<LanParty> lanPartys) {
        this.lanPartys = lanPartys;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAvatarPictureURL() {
        return avatarPictureURL;
    }

    public void setAvatarPictureURL(String avatarPictureURL) {
        this.avatarPictureURL = avatarPictureURL;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return id == member.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getName();
    }

}
