package de.meisterfuu.sip.portal.views;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.Binder;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import de.meisterfuu.sip.portal.core.AbstractView;
import de.meisterfuu.sip.portal.core.ErrorView;
import de.meisterfuu.sip.portal.domain.Game;
import de.meisterfuu.sip.portal.domain.Member;
import de.meisterfuu.sip.portal.facades.GameFacade;
import de.meisterfuu.sip.portal.facades.LoginFacade;
import de.meisterfuu.sip.portal.facades.MemberFacade;
import de.meisterfuu.sip.portal.shared.Addressield;
import org.vaadin.addons.ComboBoxMultiselect;

import javax.inject.Inject;


@CDIView(value = EditMemberView.VIEW_ID)
public class EditMemberView extends AbstractView {

    public static final String VIEW_ID = "editmember";
    private Member member;
    private boolean create = false;

    @Inject
    MemberFacade memberFacade;

    @Inject
    GameFacade gameFacade;

    @Inject
    LoginFacade loginFacade;

    @Override
    public String getViewId() {
        return VIEW_ID;
    }

    @Override
    public String getTitle() {
        if(member != null && member.getName() != null){
            return member.getName();
        }
        return "Neuer Member";
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
//            Map<String, List<String>> parameterMap  = QueryParser.splitQuery(Page.getCurrent().getLocation().toURL());
//            String gameID = parameterMap.get("member").get(0);
            String[] paths = Page.getCurrent().getLocation().getPath().split("/");
            String gameID = paths[paths.length-1];
            if(gameID.equals(VIEW_ID)){
                gameID = null;
            }
            if(gameID == null || gameID.isEmpty()){
                member = new Member();
                create = true;
            } else {
                member = memberFacade.find(Integer.valueOf(gameID));
                create = false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        if(member == null){
            UI.getCurrent().getNavigator().navigateTo(ErrorView.VIEW_ID);
        }
        super.enter(event);
    }

    @Override
    public Component buildContent() {
        return buildBinderLayout(member, create);
    }

    public FormLayout buildBinderLayout(Member member, boolean create){
        FormLayout formLayout = new FormLayout();
        formLayout.setSizeFull();

        TextField txName = new TextField("Name");
        TextField txPassword = new TextField("Passwort");
        TextField txAvatar = new TextField("Avatar");
        TextField txRank = new TextField("Rang");
        DateField dfJoin = new DateField("Beitritt");
        RichTextArea txComment = new RichTextArea("Kommentar");
        ComboBoxMultiselect<Game> cbGames = new ComboBoxMultiselect<>("Games");
        cbGames.setItems(gameFacade.getAll());
        Addressield addressield = new Addressield();
        addressield.setCaption("Adresse");

        Binder<Member> binder = new Binder<>();

        binder.setBean(member);

        binder.bind(txName, Member::getName, Member::setName);
        binder.bind(txPassword, m -> "", (m, pw) -> loginFacade.setPassword(m, pw.toCharArray()));
        binder.bind(addressield, Member::getAddress, Member::setAddress);
        binder.bind(cbGames, g -> memberFacade.getGames(g), Member::setGames);
        binder.bind(txComment, Member::getComment, Member::setComment);
        binder.bind(txAvatar, Member::getAvatarPictureURL, Member::setAvatarPictureURL);
        binder.bind(txRank, Member::getRank, Member::setRank);
        binder.bind(dfJoin, Member::getJoinDate, Member::setJoinDate);

        Button save = new Button(create ? "Erstellen" : "Speichern");
        save.addClickListener(clickEvent -> {
            Member bean = binder.getBean();
            if(create){
                memberFacade.create(bean);
            } else {
                memberFacade.update(bean);
            }
            UI.getCurrent().getNavigator().navigateTo(MembersView.VIEW_ID);
        });

        formLayout.addComponents(txName, txPassword, txAvatar, cbGames, addressield, txComment, save);
        return formLayout;
    }
}
