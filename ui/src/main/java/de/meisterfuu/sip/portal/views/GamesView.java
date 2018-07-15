package de.meisterfuu.sip.portal.views;

import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ComponentRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;
import de.meisterfuu.sip.portal.core.AbstractView;
import de.meisterfuu.sip.portal.domain.Game;
import de.meisterfuu.sip.portal.domain.Genre;
import de.meisterfuu.sip.portal.facades.GameFacade;
import org.apache.shiro.SecurityUtils;

import javax.inject.Inject;
import java.util.stream.Collectors;


@CDIView(value = GamesView.VIEW_ID)
public class GamesView extends AbstractView {

    public static final String VIEW_ID = "games";

    @Inject
    GameFacade gameFacade;

    @Override
    public String getViewId() {
        return VIEW_ID;
    }

    @Override
    public String getTitle() {
        return "Games";
    }

    @Override
    public Component buildContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);

        Grid<Game> grid = new Grid<>();
        grid.setSizeFull();

        grid.setItems(gameFacade.getAll());

        grid.addColumn(Game::getId).setCaption("Id");
        grid.addColumn(Game::getName).setCaption("Name");
        grid.addColumn(Game::getComment).setCaption("Kommentar").setRenderer(new HtmlRenderer()).setMaximumWidth(300);
//        grid.addColumn(game -> game.getGenres().stream().map(Genre::getName).collect(Collectors.joining(", "))).setCaption("Genre");
        grid.addColumn(game -> gameFacade.getGenres(game).stream().map(Genre::getName).collect(Collectors.joining(", "))).setCaption("Genre");
        grid.addColumn(Game::getPlayer).setCaption("Spielerzahl");
        grid.addColumn(Game::getPlatform).setCaption("Plattform");


        grid.addColumn(game -> {
            Button editButton = new Button("Edit");
            editButton.addStyleName("borderless");
            editButton.setIcon(VaadinIcons.EDIT);
            editButton.addClickListener(clickEvent -> UI.getCurrent().getNavigator().navigateTo(EditGameView.VIEW_ID+"/"+game.getId()));
            return editButton;
        }).setCaption("").setRenderer(new ComponentRenderer());


        grid.addItemClickListener(itemClick -> {
            if(itemClick.getMouseEventDetails().isDoubleClick()){
                int id = itemClick.getItem().getId();
                UI.getCurrent().getNavigator().navigateTo(GameView.VIEW_ID+"/"+id);
            }
        });

        layout.addComponentsAndExpand(grid);


        Button button = new Button("Game hinzufügen");
        button.addClickListener(clickEvent -> UI.getCurrent().getNavigator().navigateTo(EditGameView.VIEW_ID));
        layout.addComponent(button);

//        Label txtHello = new Label(l.get(Keys.WELCOME));
//        txtHello.setSizeUndefined();
//        txtHello.addStyleName("h1");
//        txtHello.setId("welcome-label");
//        layout.addComponent(txtHello);
//        layout.setComponentAlignment(txtHello, Alignment.MIDDLE_CENTER);
        return layout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);


    }
}
