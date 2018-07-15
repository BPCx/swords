package de.meisterfuu.sip.portal.shared;

import com.vaadin.ui.Grid;
import com.vaadin.ui.Panel;
import de.meisterfuu.sip.portal.domain.Ingredient;

import java.util.List;


/**
 * Created by meisterfuu.
 */
public class IngredientsPanel extends Panel {

    public IngredientsPanel(List<Ingredient> ingredients){

        Grid<Ingredient> grid = new Grid<>(Ingredient.class);
        grid.setItems(ingredients);
        grid.setWidth(100, Unit.PERCENTAGE);
        this.setWidth(100, Unit.PERCENTAGE);

        this.setContent(grid);
    }

}
