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
import de.meisterfuu.sip.portal.domain.Recipe;
import de.meisterfuu.sip.portal.facades.LanPartyFacade;
import de.meisterfuu.sip.portal.facades.RecipeFacade;
import de.meisterfuu.sip.portal.shared.IngredientsPanel;
import de.meisterfuu.sip.portal.shared.LanPartyStatsPanel;
import de.meisterfuu.sip.portal.shared.MapPanel;
import de.meisterfuu.sip.portal.shared.TitleDescriptionPanel;

import javax.inject.Inject;


@CDIView(value = RecipeView.VIEW_ID)
public class RecipeView extends AbstractView {

    public static final String VIEW_ID = "recipe";
    private Recipe recipe;

    @Inject
    RecipeFacade recipeFacade;

    @Override
    public String getViewId() {
        return VIEW_ID;
    }

    @Override
    public String getTitle() {
        if(recipe != null && recipe.getName() != null){
            return recipe.getName();
        }
        return "Missing Recipe";
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
                recipe = recipeFacade.find(Integer.valueOf(id));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        if(recipe == null){
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
        logo.setSource(new ExternalResource(recipe.getCoverPictureURL()));
        horizontalLayout.addComponent(logo);
//        http://cdn.akamai.steamstatic.com/steam/apps/431240/capsule_sm_120.jpg

        TitleDescriptionPanel titleDescriptionPanel = new TitleDescriptionPanel(recipe.getName(), recipe.getDescription());
        titleDescriptionPanel.setWidth(100, Unit.PERCENTAGE);
        horizontalLayout.addComponentsAndExpand(titleDescriptionPanel);


        IngredientsPanel ingredientsPanel = new IngredientsPanel(recipe.getIngredients());
        ingredientsPanel.setWidth(100, Unit.PERCENTAGE);

        verticalLayout.addComponents(horizontalLayout, ingredientsPanel);

        return verticalLayout;
    }




}
