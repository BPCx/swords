package de.meisterfuu.sip.portal.facades;

import de.meisterfuu.sip.portal.domain.Recipe;
import de.meisterfuu.sip.portal.repositories.RecipeRepository;

import javax.ejb.Stateful;
import javax.inject.Inject;
import java.util.ArrayList;

/**
 * Created by meisterfuu.
 */
@Stateful
public class RecipeFacade {

    @Inject
    private RecipeRepository recipeRepository;

    public Recipe findByName(String name) {
        return recipeRepository.findByName(name);
    }

    public Recipe create(Recipe recipe) {
        return recipeRepository.create(recipe);
    }

    public void delete(int id) {
        recipeRepository.delete(id);
    }

    public Recipe find(int id) {
        Recipe recipe = recipeRepository.find(id);
        recipe.getIngredients().size();
        return recipe;
    }

    public Recipe update(Recipe recipe) {
        return recipeRepository.update(recipe);
    }

    public ArrayList<Recipe> getAll() {
        return recipeRepository.getAll();
    }
}
