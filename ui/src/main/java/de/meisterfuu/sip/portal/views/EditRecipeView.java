package de.meisterfuu.sip.portal.views;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.Binder;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import de.meisterfuu.sip.portal.core.AbstractView;
import de.meisterfuu.sip.portal.core.ErrorView;
import de.meisterfuu.sip.portal.domain.Game;
import de.meisterfuu.sip.portal.domain.Genre;
import de.meisterfuu.sip.portal.domain.Platform;
import de.meisterfuu.sip.portal.domain.Recipe;
import de.meisterfuu.sip.portal.facades.RecipeFacade;
import de.meisterfuu.sip.portal.shared.IngredientGrid;
import de.meisterfuu.sip.portal.shared.IngredientsEditor;
import org.vaadin.addons.ComboBoxMultiselect;

import javax.inject.Inject;


@CDIView(value = EditRecipeView.VIEW_ID)
public class EditRecipeView extends AbstractView {

    public static final String VIEW_ID = "editrecipe";
    private Recipe recipe;
    private boolean create = false;

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
        return "Neues Recipe";
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
//            Map<String, List<String>> parameterMap  = QueryParser.splitQuery(Page.getCurrent().getLocation().toURL());
//            String gameID = parameterMap.get("recipe").get(0);
            String[] paths = Page.getCurrent().getLocation().getPath().split("/");
            String gameID = paths[paths.length-1];
            if(gameID.equals(VIEW_ID)){
                gameID = null;
            }
            if(gameID == null || gameID.isEmpty()){
                recipe = new Recipe();
                create = true;
            } else {
                recipe = recipeFacade.find(Integer.valueOf(gameID));
                create = false;
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
        return buildBinderLayout(recipe, create);
    }

    public FormLayout buildBinderLayout(Recipe recipe, boolean create){
        FormLayout formLayout = new FormLayout();
        formLayout.setSizeFull();

        TextField txName = new TextField("Name");
        RichTextArea txDescription = new RichTextArea("Beschreibung");
        TextField txImageURL = new TextField("Image URL");
        IngredientsEditor ingredientsEditor = new IngredientsEditor(recipe.getIngredients());
        ingredientsEditor.setCaption("Ingredients");

        Binder<Recipe> binder = new Binder<>();

        binder.setBean(recipe);

        binder.bind(txName, Recipe::getName, Recipe::setName);
        binder.bind(txDescription, Recipe::getDescription, Recipe::setDescription);
        binder.bind(txImageURL, Recipe::getCoverPictureURL, Recipe::setCoverPictureURL);

        Button save = new Button(create ? "Erstellen" : "Speichern");
        save.addClickListener(clickEvent -> {
            Recipe bean = binder.getBean();
            bean.setIngredients(recipe.getIngredients());
            if(create){
                bean = recipeFacade.update(bean);
            } else {
                bean = recipeFacade.update(bean);
            }
            UI.getCurrent().getNavigator().navigateTo(RecipeView.VIEW_ID+"/"+bean.getId());
        });

        Button delete = new Button(create ? "Abbrechen" : "Löschen");
        delete.addClickListener(clickEvent -> {
            if (!create) {
                recipeFacade.delete(recipe.getId());
            }
            UI.getCurrent().getNavigator().navigateTo(RecipeListView.VIEW_ID);
        });

        formLayout.addComponents(txName, txDescription, txImageURL, ingredientsEditor, save, delete);
        return formLayout;
    }
}
