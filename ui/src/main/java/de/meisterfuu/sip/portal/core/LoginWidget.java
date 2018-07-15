package de.meisterfuu.sip.portal.core;

import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import de.meisterfuu.sip.portal.shared.Notifications;
import de.meisterfuu.sip.portal.views.WelcomeView;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.annotation.PostConstruct;


public class LoginWidget extends VerticalLayout {

    private LoginForm loginForm;

    public LoginWidget() {
    }

    @PostConstruct
    private void initUI() {

        loginForm = new LoginForm();
        loginForm.setLoginButtonCaption("Login");
        loginForm.addLoginListener(loginEvent -> doLogin(loginEvent));

        final Panel loginPanel = new Panel("<strong>" + "Login" + "</strong> ");

        addComponent(loginPanel);
        loginPanel.setWidth("400px");

        final VerticalLayout loginLayout = new VerticalLayout();
        loginLayout.setSpacing(true);
        loginLayout.setMargin(true);
        loginLayout.setStyleName("loginForm");
        loginLayout.addComponent(loginForm);

        loginPanel.setContent(loginLayout);
        setComponentAlignment(loginPanel, Alignment.TOP_CENTER);


        setSpacing(true);

        setSizeFull();

    }

    private void doLogin(LoginForm.LoginEvent loginEvent) {

        Subject securitySubject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken();
        usernamePasswordToken.setUsername(loginEvent.getLoginParameter("username"));
        usernamePasswordToken.setPassword(loginEvent.getLoginParameter("password").toCharArray());
        try{
            securitySubject.login(usernamePasswordToken);
            PortalUI.getPortalUI().onLoggedIn();
            String path = Page.getCurrent().getLocation().getPath();
            if (path != null && !path.isEmpty() && !path.equals("/")) {
                getUI().getNavigator().navigateTo(path.startsWith("/") ? path.substring(1) : path);
            } else {
                getUI().getNavigator().navigateTo(WelcomeView.VIEW_ID);
            }
        } catch (AuthenticationException authEx){
            Notifications.showError("Login gescheitert", "");
        }
    }


}
