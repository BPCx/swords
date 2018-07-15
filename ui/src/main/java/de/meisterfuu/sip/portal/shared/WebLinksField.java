package de.meisterfuu.sip.portal.shared;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.VerticalLayout;
import de.meisterfuu.sip.portal.domain.WebLink;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by meisterfuu.
 */
public class WebLinksField extends CustomField<Set<WebLink>> {

    private Set<WebLink> webLinks;
    private VerticalLayout baseLayout;
    private VerticalLayout innerLayout;
    private Button button;

    @Override
    protected Component initContent() {
        baseLayout = new VerticalLayout();
        innerLayout = new VerticalLayout();
        button = new Button();

        baseLayout.addComponents(innerLayout, button);

        return baseLayout;
    }

    private void refreshInner(){
        innerLayout.removeAllComponents();
        for(WebLink weblink: webLinks){
            WebLinkField webLinkField = new WebLinkField();
            webLinkField.setValue(weblink);
            innerLayout.addComponent(webLinkField);
        }
    }

    @Override
    protected void doSetValue(Set<WebLink> webLinks) {
        this.webLinks = webLinks;
        refreshInner();
    }

    @Override
    public Set<WebLink> getValue() {
        return webLinks;
    }

    @Override
    public Set<WebLink> getEmptyValue() {
        return new LinkedHashSet<>();
    }

    @Override
    public boolean isEmpty() {
        return webLinks == null || webLinks.isEmpty();
    }

    @Override
    public void clear() {
        this.setValue(new LinkedHashSet<>());
    }
}
