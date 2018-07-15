package de.meisterfuu.sip.portal.views;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.ui.*;
import de.meisterfuu.sip.portal.core.AbstractView;
import de.meisterfuu.sip.portal.core.ErrorView;
import de.meisterfuu.sip.portal.domain.LanParty;
import de.meisterfuu.sip.portal.facades.LanPartyFacade;
import de.meisterfuu.sip.portal.shared.LanPartyStatsPanel;
import de.meisterfuu.sip.portal.shared.MapPanel;
import de.meisterfuu.sip.portal.shared.TitleDescriptionPanel;

import javax.inject.Inject;


@CDIView(value = LanPartyView.VIEW_ID)
public class LanPartyView extends AbstractView {

    public static final String VIEW_ID = "lanparty";
    private LanParty lanParty;

    @Inject
    LanPartyFacade lanPartyFacade;

    @Override
    public String getViewId() {
        return VIEW_ID;
    }

    @Override
    public String getTitle() {
        if(lanParty != null && lanParty.getName() != null){
            return lanParty.getName();
        }
        return "Missing Lan";
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
                lanParty = lanPartyFacade.find(Integer.valueOf(id));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        if(lanParty == null){
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
        logo.setSource(new ExternalResource(lanParty.getCoverPictureURL()));
        horizontalLayout.addComponent(logo);
//        http://cdn.akamai.steamstatic.com/steam/apps/431240/capsule_sm_120.jpg

        TitleDescriptionPanel titleDescriptionPanel = new TitleDescriptionPanel(lanParty.getName(), lanParty.getDescription());
        titleDescriptionPanel.setWidth(100, Unit.PERCENTAGE);
        horizontalLayout.addComponentsAndExpand(titleDescriptionPanel);

        LanPartyStatsPanel lanPartyStatsPanel = new LanPartyStatsPanel(lanParty, lanPartyFacade.getMember(lanParty));
        titleDescriptionPanel.setWidth(100, Unit.PERCENTAGE);
        verticalLayout.addComponents(horizontalLayout, lanPartyStatsPanel);

        GoogleMap map = MapPanel.buildMap(lanParty.getAddress(), lanParty.getName());
        if(map != null){
            Panel mapPanel = new Panel();
            mapPanel.setContent(map);
            verticalLayout.addComponent(mapPanel);
        }



        return verticalLayout;
    }




}
