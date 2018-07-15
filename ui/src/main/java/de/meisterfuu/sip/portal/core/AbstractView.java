package de.meisterfuu.sip.portal.core;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;


public abstract class AbstractView extends CustomComponent implements View {


    private HorizontalLayout topBar;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        setSizeFull();

        //TopBar
        topBar = new HorizontalLayout();
        topBar.setSizeFull();


        //MainLayout
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();

        Label breadcrumb = new Label("<h3>" + getTitle() + "</h3>", ContentMode.HTML);
        breadcrumb.addStyleName("breadcrumb");

        load(mainLayout, breadcrumb);
    }

    public abstract String getTitle();

    public abstract Component buildContent();

    public abstract String getViewId();

    private void load(VerticalLayout mainLayout,  Label breadcrumb) {
        try {
            Component content = buildContent();

            breadcrumb.setWidth(100, Unit.PERCENTAGE);
            topBar.addComponentAsFirst(breadcrumb);
            topBar.setExpandRatio(breadcrumb, 1);
            mainLayout.addComponentAsFirst(topBar);
            topBar.setHeight(40, Unit.PIXELS);


            mainLayout.addComponent(content);
            mainLayout.setExpandRatio(content,1);
            mainLayout.setMargin(new MarginInfo(false, true, true, true));
            mainLayout.setSizeFull();
            setCompositionRoot(mainLayout);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
