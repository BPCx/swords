package de.meisterfuu.sip.portal.views;

import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ComponentRenderer;
import de.meisterfuu.sip.portal.core.AbstractView;
import de.meisterfuu.sip.portal.domain.Member;
import de.meisterfuu.sip.portal.facades.MemberFacade;

import javax.inject.Inject;


@CDIView(value = MembersView.VIEW_ID)
public class MembersView extends AbstractView {

    public static final String VIEW_ID = "members";

    @Inject
    MemberFacade memberFacade;

    @Override
    public String getViewId() {
        return VIEW_ID;
    }

    @Override
    public String getTitle() {
        return "Member";
    }

    @Override
    public Component buildContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);

        Grid<Member> grid = new Grid<>();
        grid.setSizeFull();

        grid.setItems(memberFacade.getAll());

        grid.addColumn(Member::getId).setCaption("Id");
        grid.addColumn(Member::getName).setCaption("Name");
        grid.addColumn(m -> m.getAddress().getCity() != null ? m.getAddress().getCity() : "").setCaption("Stadt");

        grid.addColumn(member -> {
            Button editButton = new Button("Edit");
            editButton.addStyleName("borderless");
            editButton.setIcon(VaadinIcons.EDIT);
            editButton.addClickListener(clickEvent -> UI.getCurrent().getNavigator().navigateTo(EditMemberView.VIEW_ID+"/"+member.getId()));
            return editButton;
        }).setCaption("").setRenderer(new ComponentRenderer());


        grid.addItemClickListener(itemClick -> {
            if(itemClick.getMouseEventDetails().isDoubleClick()){
                int id = itemClick.getItem().getId();
                UI.getCurrent().getNavigator().navigateTo(MemberView.VIEW_ID+"/"+id);
            }
        });

        layout.addComponentsAndExpand(grid);


        Button button = new Button("Member hinzufügen");
        button.addClickListener(clickEvent -> UI.getCurrent().getNavigator().navigateTo(EditMemberView.VIEW_ID));
        layout.addComponent(button);

//        Label txtHello = new Label(l.get(Keys.WELCOME));
//        txtHello.setSizeUndefined();
//        txtHello.addStyleName("h1");
//        txtHello.setId("welcome-label");
//        layout.addComponent(txtHello);
//        layout.setComponentAlignment(txtHello, Alignment.MIDDLE_CENTER);
        return layout;
    }
}
