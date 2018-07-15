package de.meisterfuu.sip.portal.facades;

import de.meisterfuu.sip.portal.domain.Game;
import de.meisterfuu.sip.portal.domain.Genre;
import de.meisterfuu.sip.portal.domain.Member;
import de.meisterfuu.sip.portal.domain.WebLink;
import de.meisterfuu.sip.portal.repositories.GameRepository;

import javax.ejb.Stateful;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by meisterfuu.
 */
@Stateful
public class GameFacade {

    @Inject
    GameRepository gameRepository;


    public Game create(Game game) {
        return gameRepository.create(game);
    }

    public void delete(Object id) {
        gameRepository.delete(id);
    }

    public Game find(Object id) {
        Game game = gameRepository.find(id);
        if(game != null && game.getGenres() != null){
            game.getGenres().isEmpty();
        }
        if(game != null && game.getWebLinks() != null){
            game.getWebLinks().isEmpty();
        }
        if(game != null && game.getMembers() != null){
            game.getMembers().isEmpty();
        }
        return game;
    }

    public Game update(Game game) {
        return gameRepository.update(game);
    }

    public ArrayList<Game> getAll() {
        ArrayList<Game> all = gameRepository.getAll();
//        all.forEach(g -> g.getGenres().forEach(g1 -> System.out.print(g1.getName())));
        return all;
    }

    public Set<Genre> getGenres(Game game) {
        Game attached = find(game.getId());
        if(attached == null){
            return null;
        }
        return attached.getGenres();
    }

    public Set<WebLink> getWebLinks(Game game) {
        Game attached = find(game.getId());
        if(attached == null){
            return null;
        }
        return attached.getWebLinks();
    }

    public Set<Member> getMembers(Game game) {
        Game attached = find(game.getId());
        if(attached == null){
            return null;
        }
        return attached.getMembers();
    }
}
