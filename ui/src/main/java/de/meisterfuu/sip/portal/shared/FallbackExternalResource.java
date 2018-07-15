package de.meisterfuu.sip.portal.shared;

import com.vaadin.server.ExternalResource;

/**
 * Created by meisterfuu.
 */
public class FallbackExternalResource extends ExternalResource {

    public FallbackExternalResource(String sourceURL) {
        this(sourceURL, "");
    }

    public FallbackExternalResource(String sourceURL, String fallbackURL) {
        super(sourceURL == null ? fallbackURL : sourceURL);
    }
}
