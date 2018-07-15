package de.meisterfuu.sip.portal.views;

import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ComponentRenderer;
import de.meisterfuu.sip.portal.core.AbstractView;
import de.meisterfuu.sip.portal.domain.LanParty;
import de.meisterfuu.sip.portal.facades.LanPartyFacade;

import javax.inject.Inject;


@CDIView(value = LanPartyListView.VIEW_ID)
public class LanPartyListView extends AbstractView {

    public static final String VIEW_ID = "lanpartys";

    @Inject
    LanPartyFacade lanPartyFacade;

    @Override
    public String getViewId() {
        return VIEW_ID;
    }

    @Override
    public String getTitle() {
        return "LAN Partys";
    }

    @Override
    public Component buildContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);

        Grid<LanParty> grid = new Grid<>();
        grid.setSizeFull();

        grid.setItems(lanPartyFacade.getAll());

        grid.addColumn(LanParty::getId).setCaption("Id");
        grid.addColumn(LanParty::getName).setCaption("Name");
        grid.addColumn(LanParty::getLocation).setCaption("Location");
        grid.addColumn(LanParty::getAddress).setCaption("Addresse");
        grid.addColumn(LanParty::getStartDate).setCaption("Startdatum");
        grid.addColumn(LanParty::getEndDate).setCaption("Enddatum");

        grid.addColumn(lanParty -> {
            Button editButton = new Button("Edit");
            editButton.addStyleName("borderless");
            editButton.setIcon(VaadinIcons.EDIT);
            editButton.addClickListener(clickEvent -> UI.getCurrent().getNavigator().navigateTo(EditLanPartyView.VIEW_ID+"/"+lanParty.getId()));
            return editButton;
        }).setCaption("").setRenderer(new ComponentRenderer());


        grid.addItemClickListener(itemClick -> {
            if(itemClick.getMouseEventDetails().isDoubleClick()){
                int id = itemClick.getItem().getId();
                UI.getCurrent().getNavigator().navigateTo(LanPartyView.VIEW_ID+"/"+id);
            }
        });
        layout.addComponentsAndExpand(grid);


        Button button = new Button("Lanparty hinzufügen");
        button.addClickListener(clickEvent -> UI.getCurrent().getNavigator().navigateTo(EditLanPartyView.VIEW_ID));
        layout.addComponent(button);

        return layout;
    }
}
