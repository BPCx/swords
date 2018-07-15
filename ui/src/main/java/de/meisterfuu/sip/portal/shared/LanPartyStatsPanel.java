package de.meisterfuu.sip.portal.shared;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import de.meisterfuu.sip.portal.domain.LanParty;
import de.meisterfuu.sip.portal.domain.Member;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Created by meisterfuu.
 */
public class LanPartyStatsPanel extends Panel {

    public LanPartyStatsPanel(LanParty lanParty, Set<Member> memberList){

        VerticalLayout statsLayout = new VerticalLayout();
        statsLayout.setWidth(100, Unit.PERCENTAGE);
        this.setWidth(100, Unit.PERCENTAGE);


        String begin = "Datum: Vom <span class=\"focus-text-color\">";
        begin += lanParty.getStartDate();
        begin += "</span> bis <span class=\"focus-text-color\">";
        begin += lanParty.getEndDate();
        begin += "</span> (";
        begin += lanParty.getStartDate().until(lanParty.getEndDate(), ChronoUnit.DAYS)+" Tage)";
        Label beginLabel = new Label();
        beginLabel.setContentMode(ContentMode.HTML);
        beginLabel.setValue(begin);
        beginLabel.setSizeFull();
        statsLayout.addComponent(beginLabel);

        String countdown = "Beginnt in <span class=\"focus-text-color\">";
        countdown += LocalDate.now().until(lanParty.getStartDate(), ChronoUnit.DAYS);
        countdown += "</span> Tagen!";
        Label countdownLabel = new Label();
        countdownLabel.setContentMode(ContentMode.HTML);
        countdownLabel.setValue(countdown);
        countdownLabel.setSizeFull();
        statsLayout.addComponent(countdownLabel);

        String location = "Location: <span class=\"focus-text-color\">";
        location += lanParty.getLocation();
        location += "</span>";
        Label locationLabel = new Label();
        locationLabel.setContentMode(ContentMode.HTML);
        locationLabel.setValue(location);
        locationLabel.setSizeFull();
        statsLayout.addComponent(locationLabel);

        String member = "Teilnehmer: <span class=\"focus-text-color\">";
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
