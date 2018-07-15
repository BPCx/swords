package de.meisterfuu.sip.portal.shared;

import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;


/**
 * Created by meisterfuu.
 */
public class TitleDescriptionPanel extends Panel {

    public TitleDescriptionPanel(String title, String description){
        VerticalLayout descLayout = new VerticalLayout();

        if(title != null){
            Label titleLabel = new Label();
            titleLabel.setValue(title);
            titleLabel.setSizeFull();
            descLayout.addComponent(titleLabel);
        }

        Label label = new Label();
        label.setValue(description);
        label.setContentMode(ContentMode.HTML);
        label.setSizeFull();
        descLayout.addComponents(label);

        descLayout.setMargin(true);
        descLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);

        this.setContent(descLayout);
        this.setWidth(100, Sizeable.Unit.PERCENTAGE);
    }

}
