package de.meisterfuu.sip.portal.views;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.ui.*;
import de.meisterfuu.sip.portal.core.AbstractView;
import de.meisterfuu.sip.portal.core.ErrorView;
import de.meisterfuu.sip.portal.domain.Member;
import de.meisterfuu.sip.portal.facades.GameFacade;
import de.meisterfuu.sip.portal.facades.MemberFacade;
import de.meisterfuu.sip.portal.shared.FallbackExternalResource;
import de.meisterfuu.sip.portal.shared.MapPanel;
import de.meisterfuu.sip.portal.shared.TitleDescriptionPanel;

import javax.inject.Inject;


@CDIView(value = MemberView.VIEW_ID)
public class MemberView extends AbstractView {

    public static final String VIEW_ID = "member";
    private Member member;
    private boolean create = false;

    @Inject
    MemberFacade memberFacade;

    @Inject
    GameFacade gameFacade;

    @Override
    public String getViewId() {
        return VIEW_ID;
    }

    @Override
    public String getTitle() {
        if(member != null && member.getName() != null){
            return member.getName();
        }
        return "Missing Member";
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
            String[] paths = Page.getCurrent().getLocation().getPath().split("/");
            String id = paths[paths.length-1];
            if(id.equals(VIEW_ID)){
                id = null;
            }
            if (id != null && !id.isEmpty()) {
                member = memberFacade.find(Integer.valueOf(id));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        if(member == null){
            UI.getCurrent().getNavigator().navigateTo(ErrorView.VIEW_ID);
        }
        super.enter(event);
    }

    @Override
    public Component buildContent() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(false);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth(100, Unit.PERCENTAGE);
        horizontalLayout.setMargin(false);

        Image logo = new Image();
        logo.addStyleName("roundimage");
        logo.addStyleName("profilepicture");
        logo.setSource(new FallbackExternalResource(member.getAvatarPictureURL()));
        horizontalLayout.addComponent(logo);
//        http://cdn.akamai.steamstatic.com/steam/apps/431240/capsule_sm_120.jpg

        TitleDescriptionPanel titleDescriptionPanel = new TitleDescriptionPanel(member.getName(), member.getComment());
        titleDescriptionPanel.setWidth(100, Unit.PERCENTAGE);
        horizontalLayout.addComponentsAndExpand(titleDescriptionPanel);


        verticalLayout.addComponents(horizontalLayout);

        GoogleMap map = MapPanel.buildMap(member.getAddress(), member.getName());
        if(map != null){
            Panel mapPanel = new Panel();
            mapPanel.setContent(map);
            verticalLayout.addComponent(mapPanel);
        }


        return verticalLayout;
    }


}
