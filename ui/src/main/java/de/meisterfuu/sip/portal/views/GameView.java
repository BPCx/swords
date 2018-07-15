package de.meisterfuu.sip.portal.views;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import de.meisterfuu.sip.portal.core.AbstractView;
import de.meisterfuu.sip.portal.core.ErrorView;
import de.meisterfuu.sip.portal.domain.Game;
import de.meisterfuu.sip.portal.domain.Member;
import de.meisterfuu.sip.portal.facades.GameFacade;
import de.meisterfuu.sip.portal.shared.GameStatsPanel;
import de.meisterfuu.sip.portal.shared.TitleDescriptionPanel;

import javax.inject.Inject;
import java.util.Set;


@CDIView(value = GameView.VIEW_ID)
public class GameView extends AbstractView {

    public static final String VIEW_ID = "game";
    private Game game;

    @Inject
    private GameFacade gameFacade;

    @Override
    public String getViewId() {
        return VIEW_ID;
    }

    @Override
    public String getTitle() {
        if(game != null && game.getName() != null){
            return game.getName();
        }
        return "Missing Game";
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
//            Map<String, List<String>> parameterMap  = QueryParser.splitQuery(Page.getCurrent().getLocation().toURL());
//            String gameID = parameterMap.get("game").get(0);
            String[] paths = Page.getCurrent().getLocation().getPath().split("/");
            String gameID = paths[paths.length-1];
            if(gameID.equals(VIEW_ID)){
                gameID = null;
            }
            if (gameID != null && !gameID.isEmpty()) {
                game = gameFacade.find(Integer.valueOf(gameID));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        if(game == null){
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
        logo.setSource(new ExternalResource(game.getCoverPictureURL()));
        horizontalLayout.addComponent(logo);
//        http://cdn.akamai.steamstatic.com/steam/apps/431240/capsule_sm_120.jpg

        TitleDescriptionPanel titleDescriptionPanel = new TitleDescriptionPanel(game.getName(), game.getComment());
        titleDescriptionPanel.setWidth(100, Unit.PERCENTAGE);
        horizontalLayout.addComponentsAndExpand(titleDescriptionPanel);

        verticalLayout.addComponent(horizontalLayout);


        GameStatsPanel gameStatsPanel = new GameStatsPanel(game,gameFacade.getMembers(game));
        verticalLayout.addComponent(gameStatsPanel);

        return verticalLayout;
    }


}
