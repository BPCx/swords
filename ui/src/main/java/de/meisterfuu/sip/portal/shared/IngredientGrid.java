package de.meisterfuu.sip.portal.shared;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextField;
import de.meisterfuu.sip.portal.domain.Ingredient;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class IngredientGrid extends Grid<Ingredient> {

    public IngredientGrid() {
        super();
        init();
    }

    public IngredientGrid(String caption) {
        super(caption);
        init();
    }

    private void init() {
        setSelectionMode(SelectionMode.NONE);

        TextField nameEditor = new TextField();
        TextField countEditor = new TextField();
        ComboBox<Ingredient.Units> unitsComboBox = new ComboBox<>();
        unitsComboBox.setItems(Ingredient.Units.values());

        Slider progressEditor = new Slider();
        progressEditor.setWidth(100.0f, Unit.PERCENTAGE);
        progressEditor.setMax(1000.0);
        progressEditor.setMin(1.0);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);


        this.addColumn(Ingredient::getName)
                .setEditorComponent(nameEditor, Ingredient::setName)
                .setCaption("Name");

        this.addColumn(r -> numberFormat.format(r.getCount()))
                .setEditorComponent(countEditor, (ingredient, aFloat) ->
                        {
                            try {
                                ingredient.setCount(numberFormat.parse(aFloat).floatValue());
                            } catch (ParseException e) {
                                throw new NumberFormatException(e.getLocalizedMessage());
                            }
                        }
                )
                .setCaption("Menge");

        this.addColumn(Ingredient::getUnit)
                .setEditorComponent(unitsComboBox, Ingredient::setUnit)
                .setCaption("Einheit");

//        grid.addColumn(Person::getWeightHistory, new SparklineRenderer())
//                .setCaption("Weight")
//                .setExpandRatio(4);

        this.getEditor().setEnabled(true);
    }
}
