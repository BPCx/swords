package de.meisterfuu.sip.portal.views;

import com.vaadin.cdi.CDIView;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import de.meisterfuu.sip.portal.core.AbstractView;


@CDIView(value = WelcomeView.VIEW_ID)
public class WelcomeView extends AbstractView {

    public static final String VIEW_ID = "home";

    @Override
    public String getViewId() {
        return VIEW_ID;
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public Component buildContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
//        Label txtHello = new Label(l.get(Keys.WELCOME));
//        txtHello.setSizeUndefined();
//        txtHello.addStyleName("h1");
//        txtHello.setId("welcome-label");
//        layout.addComponent(txtHello);
//        layout.setComponentAlignment(txtHello, Alignment.MIDDLE_CENTER);
        return layout;
    }
}
