package de.meisterfuu.sip.portal.shared;

import com.vaadin.data.Binder;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import de.meisterfuu.sip.portal.domain.WebLink;

/**
 * Created by meisterfuu.
 */
public class WebLinkField extends CustomField<WebLink> {

    private HorizontalLayout baseLayout;
    private TextField txType;
    private TextField txURL;
    private  Binder<WebLink> binder;
    @Override
    protected Component initContent() {
        baseLayout = new HorizontalLayout();
        txType = new TextField();
        txURL = new TextField();

        binder = new Binder<>();
        binder.bind(txType, WebLink::getType, WebLink::setType);
        binder.bind(txURL, WebLink::getUrl, WebLink::setUrl);

        baseLayout.addComponents(txType, txURL);

        return baseLayout;
    }

    @Override
    protected void doSetValue(WebLink webLink) {
        binder.setBean(webLink);
    }

    @Override
    public WebLink getValue() {
        return (txURL.isEmpty() && txType.isEmpty()) ? null : binder.getBean();
    }

    @Override
    public WebLink getEmptyValue() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return (txURL.isEmpty() && txType.isEmpty());
    }

    @Override
    public void clear() {
        doSetValue(new WebLink());
    }
}
