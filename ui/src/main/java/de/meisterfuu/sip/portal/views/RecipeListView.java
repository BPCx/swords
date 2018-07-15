package de.meisterfuu.sip.portal.views;

import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ComponentRenderer;
import de.meisterfuu.sip.portal.core.AbstractView;
import de.meisterfuu.sip.portal.domain.LanParty;
import de.meisterfuu.sip.portal.domain.Recipe;
import de.meisterfuu.sip.portal.facades.LanPartyFacade;
import de.meisterfuu.sip.portal.facades.RecipeFacade;

import javax.inject.Inject;


@CDIView(value = RecipeListView.VIEW_ID)
public class RecipeListView extends AbstractView {

    public static final String VIEW_ID = "recipes";

    @Inject
    RecipeFacade recipeFacade;

    @Override
    public String getViewId() {
        return VIEW_ID;
    }

    @Override
    public String getTitle() {
        return "Recipes";
    }

    @Override
    public Component buildContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);

        Grid<Recipe> grid = new Grid<>();
        grid.setSizeFull();

        grid.setItems(recipeFacade.getAll());

        grid.addColumn(Recipe::getId).setCaption("Id");
        grid.addColumn(Recipe::getName).setCaption("Name");
        grid.addColumn(Recipe::getDescription).setCaption("Beschreibung");

        grid.addColumn(recipe -> {
            Button editButton = new Button("Edit");
            editButton.addStyleName("borderless");
            editButton.setIcon(VaadinIcons.EDIT);
            editButton.addClickListener(clickEvent -> UI.getCurrent().getNavigator().navigateTo(EditRecipeView.VIEW_ID+"/"+recipe.getId()));
            return editButton;
        }).setCaption("").setRenderer(new ComponentRenderer());


        grid.addItemClickListener(itemClick -> {
            if(itemClick.getMouseEventDetails().isDoubleClick()){
                int id = itemClick.getItem().getId();
                UI.getCurrent().getNavigator().navigateTo(RecipeView.VIEW_ID+"/"+id);
            }
        });
        layout.addComponentsAndExpand(grid);


        Button button = new Button("Recipe hinzufügen");
        button.addClickListener(clickEvent -> UI.getCurrent().getNavigator().navigateTo(EditRecipeView.VIEW_ID));
        layout.addComponent(button);

        return layout;
    }
}
