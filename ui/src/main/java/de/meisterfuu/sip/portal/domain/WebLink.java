package de.meisterfuu.sip.portal.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by meisterfuu.
 */
@Embeddable
public class WebLink implements Serializable {

    @Column(name = "type")
    private String type;

    @Column(name = "url")
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
