package de.meisterfuu.sip.portal.shared;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import de.meisterfuu.sip.portal.domain.Game;
import de.meisterfuu.sip.portal.domain.Genre;
import de.meisterfuu.sip.portal.domain.Member;

import java.util.Set;
import java.util.stream.Collectors;


/**
 * Created by meisterfuu.
 */
public class GameStatsPanel extends Panel {

    public GameStatsPanel(Game game, Set<Member> memberList){

        VerticalLayout statsLayout = new VerticalLayout();
        statsLayout.setWidth(100, Unit.PERCENTAGE);
        this.setWidth(100, Unit.PERCENTAGE);


        String player = "Spielerzahl:<span class=\"focus-text-color\">";
        for(int i = 0; i < game.getPlayer(); i++){
            player += " "+ VaadinIcons.GAMEPAD.getHtml();
        }
        player += "</span>";
        player += " ("+game.getPlayer()+")";
        Label playerCount = new Label();
        playerCount.setContentMode(ContentMode.HTML);
        playerCount.setValue(player);
        playerCount.setSizeFull();
        statsLayout.addComponent(playerCount);

        String genres = "Genres: <span class=\"focus-text-color\">";
        genres += game.getGenres().stream().map(Genre::getName).collect(Collectors.joining(", "));
        genres += "</span>";
        Label genreLabel = new Label();
        genreLabel.setContentMode(ContentMode.HTML);
        genreLabel.setValue(genres);
        genreLabel.setSizeFull();
        statsLayout.addComponent(genreLabel);

        String plattform = "Plattform: <span class=\"focus-text-color\">";
        plattform += game.getPlatform().name();
        plattform += "</span>";
        Label plattformLabel = new Label();
        plattformLabel.setContentMode(ContentMode.HTML);
        plattformLabel.setValue(plattform);
        plattformLabel.setSizeFull();
        statsLayout.addComponent(plattformLabel);

        String member = "Im Besitz: <span class=\"focus-text-color\">";
        member += memberList.stream().map(Member::getName).collect(Collectors.joining(", "));
        member += "</span>";
        Label memberLabel = new Label();
        memberLabel.setContentMode(ContentMode.HTML);
        memberLabel.setValue(member);
        memberLabel.setSizeFull();
        statsLayout.addComponent(memberLabel);

        this.setContent(statsLayout);
    }

}
