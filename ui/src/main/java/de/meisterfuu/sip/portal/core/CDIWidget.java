package de.meisterfuu.sip.portal.core;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import de.meisterfuu.sip.portal.debug.DebugView;
import de.meisterfuu.sip.portal.views.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.inject.Inject;


public class CDIWidget extends VerticalLayout {

    private CssLayout contentArea = new CssLayout();
    private HorizontalLayout menuLayout = new HorizontalLayout();
    private MenuBar menuBarLeft = new MenuBar();
    private MenuBar menuBarRight = new MenuBar();

    @Inject
    public CDIWidget() {
        init();
    }

    private void init() {
        setSizeFull();
        this.setMargin(false);
        this.setSpacing(true);
        menuLayout.setMargin(false);
        menuLayout.setSpacing(false);

        menuLayout.setWidth(100, Unit.PERCENTAGE);

        menuBarLeft.addStyleNames("borderless", "no-focus");
        menuBarLeft.addItem("Home", VaadinIcons.HOME, menuItem -> UI.getCurrent().getNavigator().navigateTo(WelcomeView.VIEW_ID));
        menuBarLeft.addItem("Events", VaadinIcons.CALENDAR, menuItem -> UI.getCurrent().getNavigator().navigateTo(LanPartyListView.VIEW_ID));
        menuBarLeft.addItem("Games", VaadinIcons.GAMEPAD, menuItem -> UI.getCurrent().getNavigator().navigateTo(GamesView.VIEW_ID));
        menuBarLeft.addItem("Members", VaadinIcons.USERS, menuItem -> UI.getCurrent().getNavigator().navigateTo(MembersView.VIEW_ID));
        menuBarLeft.addItem("Recipes", VaadinIcons.LIST, menuItem -> UI.getCurrent().getNavigator().navigateTo(RecipeListView.VIEW_ID));
        menuBarLeft.addItem("Debug", VaadinIcons.BUG, menuItem -> UI.getCurrent().getNavigator().navigateTo(DebugView.VIEW_ID));
        menuLayout.addComponent(menuBarLeft);

        menuBarRight.addStyleNames("borderless", "no-focus");
        menuBarRight.addItem("Profil", VaadinIcons.USER_CARD, menuItem -> UI.getCurrent().getNavigator().navigateTo(DebugView.VIEW_ID));
        menuBarRight.addItem("Logout", VaadinIcons.EXIT, menuItem -> {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
            VaadinService.getCurrentRequest().getWrappedSession().invalidate();
            UI.getCurrent().getSession().close();
            Page.getCurrent().setLocation(Page.getCurrent().getLocation().getPath());
        });
        menuLayout.addComponent(menuBarRight);

        menuLayout.setExpandRatio(menuBarLeft, 1);

        menuLayout.addStyleName("topmenu");

        this.addComponent(menuLayout);

        contentArea.setSizeFull();
        this.addStyleName("forcescrollable");
//        contentArea.addStyleName("space-bottom");
        this.addComponentsAndExpand(contentArea);
    }
     
    public ComponentContainer getContentArea() {
        return contentArea;
    }

}
