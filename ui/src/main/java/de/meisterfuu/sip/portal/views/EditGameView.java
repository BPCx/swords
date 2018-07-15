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
import de.meisterfuu.sip.portal.facades.GameFacade;
import de.meisterfuu.sip.portal.repositories.GenreRepository;
import org.vaadin.addons.ComboBoxMultiselect;

import javax.inject.Inject;


@CDIView(value = EditGameView.VIEW_ID)
public class EditGameView extends AbstractView {

    public static final String VIEW_ID = "editgame";
    private Game game;
    private boolean create = false;

    @Inject
    GenreRepository genreRepository;

    @Inject
    GameFacade editGameFacade;

    @Override
    public String getViewId() {
        return VIEW_ID;
    }

    @Override
    public String getTitle() {
        if(game != null && game.getName() != null){
            return game.getName();
        }
        return "Neues Game";
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
            if(gameID == null || gameID.isEmpty()){
                game = new Game();
                create = true;
            } else {
                game = editGameFacade.find(Integer.valueOf(gameID));
                create = false;
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
        return buildBinderLayout(game, create);
    }

    public FormLayout buildBinderLayout(Game game, boolean create){
        FormLayout formLayout = new FormLayout();
        formLayout.setSizeFull();

        TextField txName = new TextField("Name");
        RichTextArea txComment = new RichTextArea("Kommentar");
        TextField txPlayerCount = new TextField("Spielerzahl");
        TextField txLogoURL = new TextField("Logo URL");
        TextField txImageURL = new TextField("Coverbild URL");
        ComboBox<Platform> cbPlatform = new ComboBox<>("Plattform");
        cbPlatform.setItems(Platform.values());

        ComboBoxMultiselect<Genre> cbGenre = new ComboBoxMultiselect<>("Genre");
        cbGenre.setItems(genreRepository.getAll());

        Binder<Game> binder = new Binder<>();

//        if(create) {
            binder.setBean(game);
//        } else {
//            binder.setBean(gameRepository.find(game.getId()));
//        }

        binder.bind(txName, Game::getName, Game::setName);
        binder.bind(txComment, Game::getComment, Game::setComment);
        binder.bind(cbPlatform, Game::getPlatform, Game::setPlatform);
        binder.bind(txImageURL, Game::getCoverPictureURL, Game::setCoverPictureURL);
        binder.bind(txLogoURL, Game::getLogoPictureURL, Game::setLogoPictureURL);
        binder.bind(cbGenre, g -> editGameFacade.getGenres(g), Game::setGenres);
        binder.forField(txPlayerCount)
                .withConverter(Integer::valueOf, String::valueOf)
                .bind(Game::getPlayer, Game::setPlayer);

        Button save = new Button(create ? "Erstellen" : "Speichern");
        save.addClickListener(clickEvent -> {
            Game bean = binder.getBean();
            if(create){
                bean = editGameFacade.update(bean);
            } else {
                bean = editGameFacade.update(bean);
            }
            UI.getCurrent().getNavigator().navigateTo(GameView.VIEW_ID+"/"+bean.getId());
        });

        Button delete = new Button(create ? "Abbrechen" : "Löschen");
        delete.addClickListener(clickEvent -> {
            if (!create) {
                editGameFacade.delete(game.getId());
            }
            UI.getCurrent().getNavigator().navigateTo(GamesView.VIEW_ID);
        });

        formLayout.addComponents(txName, txComment, cbPlatform, cbGenre, txPlayerCount, txImageURL, txLogoURL, save, delete);
        return formLayout;
    }
}
