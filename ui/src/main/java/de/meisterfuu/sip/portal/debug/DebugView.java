package de.meisterfuu.sip.portal.debug;

import com.vaadin.cdi.CDIView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import de.meisterfuu.sip.portal.core.AbstractView;
import de.meisterfuu.sip.portal.domain.Genre;
import de.meisterfuu.sip.portal.domain.Ingredient;
import de.meisterfuu.sip.portal.domain.Recipe;
import de.meisterfuu.sip.portal.facades.RecipeFacade;
import de.meisterfuu.sip.portal.repositories.GenreRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;


@CDIView(value = DebugView.VIEW_ID)
public class DebugView extends AbstractView {

    public static final String VIEW_ID = "entkaefern";

    @Inject
    GenreRepository genreRepository;

    @Inject
    RecipeFacade recipeFacade;


    @PostConstruct
    private void init() {

    }

    @Override
    public String getTitle() {
        return "Entkäfern";
    }

    @Override
    public Component buildContent() {
        VerticalLayout vl = new VerticalLayout();
        vl.setMargin(true);
        vl.setSpacing(true);

        Button debugButton = new Button("DEBUG");
        TextField txDebug = new TextField("DEBUG");

        debugButton.addClickListener((Button.ClickListener) clickEvent -> {
            try {
                genreRepository.create(new Genre(txDebug.getValue()));
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        Button recipeButton = new Button("RECIPE");
        recipeButton.addClickListener((Button.ClickListener) clickEvent -> {
            try {
                Recipe recipe = new Recipe();
                recipe.setName("Salzbraten");
                recipe.setDescription("Salzbraten mit Rotkohl");
                recipe.setCoverPictureURL("https://static.chefkoch-cdn.de/ck.de/rezepte/158/158278/768498-960x720-der-allerbeste-salzbraten.jpg");

                Ingredient ingredient = new Ingredient();
                ingredient.setName("Schweinefleisch");
                ingredient.setCount(3);
                ingredient.setUnit(Ingredient.Units.KILOGRAM);

                Ingredient ingredient2 = new Ingredient();
                ingredient2.setName("Salz");
                ingredient2.setCount(3);
                ingredient2.setUnit(Ingredient.Units.KILOGRAM);

                ArrayList<Ingredient> ingredients = new ArrayList<>();
                ingredients.add(ingredient);
                ingredients.add(ingredient2);

                recipe.setIngredients(ingredients);

                recipeFacade.create(recipe);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        vl.addComponents(txDebug, debugButton, recipeButton);

        return vl;



    }

    @Override
    public String getViewId() {
        return VIEW_ID;
    }

}
