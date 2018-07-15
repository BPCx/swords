package de.meisterfuu.sip.portal.core;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.cdi.CDINavigator;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.UIScoped;
import com.vaadin.cdi.server.VaadinCDIServlet;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

/**
 *
 */
@Push(transport = Transport.WEBSOCKET_XHR)
@Theme("sip")
@CDIUI("")
@UIScoped
@PushStateNavigation()
public class PortalUI extends UI implements ViewChangeListener {

    @Inject
    private CDINavigator cdiNavigator;

    @Inject
    private LoginWidget loginLayout;

    private CDIWidget mainWidget;

    public PortalUI ui;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        ui = this;

//        this.getLoadingIndicatorConfiguration().setFirstDelay(0);
//        this.getLoadingIndicatorConfiguration().setSecondDelay(1);
//        this.getLoadingIndicatorConfiguration().setThirdDelay(2);

//        Config conf = configService.getConfig("portal.properties");
//        String proxyHost = conf.get("proxyHost");
//        String proxyPort = conf.get("proxyPort");
//        System.setProperty("http.proxyHost", proxyHost != null ? proxyHost : "");
//        System.setProperty("http.proxyPort", proxyPort != null ? proxyPort : "");

        getLoadingIndicatorConfiguration().setSecondDelay(Integer.MAX_VALUE - 1);
        getLoadingIndicatorConfiguration().setThirdDelay(Integer.MAX_VALUE);

        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);


        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            setContent(loginLayout);
        } else {
            setPortal();
        }

    }

    private void setPortal() {
        if (mainWidget != null) {
            if (getContent() != mainWidget) {
                setContent(mainWidget);
            }
        } else {
            onLoggedIn();
            setContent(mainWidget);
        }
    }

    @Override
    public boolean beforeViewChange(ViewChangeListener.ViewChangeEvent event) {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated() && event.getViewName().equals("")) {
            return false;
        }

        if (!currentUser.isAuthenticated() && !event.getViewName().equals("")) {
            setContent(loginLayout);
            return false;
        }

        if (currentUser.isAuthenticated()) {
            setPortal();
        }
        return true;
    }

    public void onLoggedIn() {

        mainWidget = CDI.current().select(CDIWidget.class).get();
        mainWidget.addStyleName("forcescrollable");

        cdiNavigator.init(this, mainWidget.getContentArea());
        setNavigator(cdiNavigator);

        cdiNavigator.addViewChangeListener(this);
        cdiNavigator.setErrorView(ErrorView.class);
    }

    @Override
    public void afterViewChange(ViewChangeListener.ViewChangeEvent event) {
    }


    /**
     * Returns an instance of current PortalUI with all additional methods and
     * fields.
     *
     * @return
     */
    public static PortalUI getPortalUI() {
        return (PortalUI) getCurrent();
    }


    @WebServlet(urlPatterns = "/*", name = "PortalUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = PortalUI.class, productionMode = false)
    public static class PortalUIServlet extends VaadinCDIServlet {
    }


}


