package de.meisterfuu.sip.portal.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by meisterfuu.
 */
@Entity
public class Game {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Genre> genres = new HashSet<>();

    @Column(name = "platform")
    private Platform platform;

    @Column(name = "player")
    private int player;

    @Column(name = "comment", length = 10000)
    private String comment;

    @Column(name = "coverURL", length = 500)
    private String coverPictureURL;

    @Column(name = "logoURL", length = 500)
    private String logoPictureURL;

    @ElementCollection
    private Set<WebLink> webLinks = new HashSet<>();

    @ManyToMany(mappedBy = "games", fetch = FetchType.LAZY)
    private Set<Member> members = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<WebLink> getWebLinks() {
        return webLinks;
    }

    public void setWebLinks(Set<WebLink> webLinks) {
        this.webLinks = webLinks;
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

    public String getCoverPictureURL() {
        return coverPictureURL;
    }

    public void setCoverPictureURL(String coverPictureURL) {
        this.coverPictureURL = coverPictureURL;
    }

    public String getLogoPictureURL() {
        return logoPictureURL;
    }

    public void setLogoPictureURL(String logoPictureURL) {
        this.logoPictureURL = logoPictureURL;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
