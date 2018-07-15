package de.meisterfuu.sip.portal.core;

import com.vaadin.cdi.CDIView;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;

import java.io.File;


@CDIView(value = ErrorView.VIEW_ID)
public class ErrorView extends AbstractView {

    public static final String VIEW_ID = "error";

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public Component buildContent() {
        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/404doge.png"));
        Image image = new Image("", resource);

        return image;
    }

    @Override
    public String getViewId() {
        return VIEW_ID;
    }
}
