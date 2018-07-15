package de.meisterfuu.sip.portal.shared;

import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import de.meisterfuu.sip.portal.domain.Ingredient;

import java.util.List;


/**
 * Created by meisterfuu.
 */
public class IngredientsEditor extends Panel {

    IngredientGrid grid = new IngredientGrid();

    public IngredientsEditor(List<Ingredient> ingredients){

        VerticalLayout verticalLayout = new VerticalLayout();


        grid.setItems(ingredients);
        verticalLayout.addComponent(grid);

        Button newButton = new Button();
        newButton.addClickListener(clickEvent -> {
            ingredients.add(new Ingredient());
            grid.setItems(ingredients);
        });
        verticalLayout.addComponent(newButton);

        grid.setWidth(100, Unit.PERCENTAGE);
        verticalLayout.setWidth(100, Unit.PERCENTAGE);
        this.setWidth(100, Unit.PERCENTAGE);

        this.setContent(verticalLayout);
    }

    public Grid<Ingredient> getGrid() {
        return grid;
    }

}
