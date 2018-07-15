package de.meisterfuu.sip.portal.views;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.Binder;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import de.meisterfuu.sip.portal.core.AbstractView;
import de.meisterfuu.sip.portal.core.ErrorView;
import de.meisterfuu.sip.portal.domain.LanParty;
import de.meisterfuu.sip.portal.domain.Member;
import de.meisterfuu.sip.portal.facades.LanPartyFacade;
import de.meisterfuu.sip.portal.facades.MemberFacade;
import de.meisterfuu.sip.portal.shared.Addressield;
import org.vaadin.addons.ComboBoxMultiselect;

import javax.inject.Inject;


@CDIView(value = EditLanPartyView.VIEW_ID)
public class EditLanPartyView extends AbstractView {

    public static final String VIEW_ID = "editlanparty";
    private LanParty lanParty;
    private boolean create = false;


    @Inject
    LanPartyFacade lanPartyFacade;

    @Inject
    MemberFacade memberFacade;

    @Override
    public String getViewId() {
        return VIEW_ID;
    }

    @Override
    public String getTitle() {
        if(lanParty != null && lanParty.getName() != null){
            return lanParty.getName();
        }
        return "Neues Event";
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
//            Map<String, List<String>> parameterMap  = QueryParser.splitQuery(Page.getCurrent().getLocation().toURL());
//            String lanPartyId = parameterMap.get("game").get(0);
            String[] paths = Page.getCurrent().getLocation().getPath().split("/");
            String lanPartyId = paths[paths.length-1];
            if(lanPartyId.equalsIgnoreCase(VIEW_ID)){
                lanPartyId = null;
            }
            if(lanPartyId == null || lanPartyId.isEmpty()){
                lanParty = new LanParty();
                create = true;
            } else {
                lanParty = lanPartyFacade.find(Integer.valueOf(lanPartyId));
                create = false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        if(lanParty == null){
            UI.getCurrent().getNavigator().navigateTo(ErrorView.VIEW_ID);
        }
        super.enter(event);
    }

    @Override
    public Component buildContent() {
        return buildBinderLayout(lanParty, create);
    }

    public FormLayout buildBinderLayout(LanParty lanParty, boolean create){
        FormLayout formLayout = new FormLayout();
        formLayout.setSizeFull();

        TextField txName = new TextField("Name");
        RichTextArea txDescription = new RichTextArea("Beschreibung");
        TextField txLocation = new TextField("Location");
        TextField txCover = new TextField("Image");
        ComboBoxMultiselect<Member> cbMember = new ComboBoxMultiselect<>("Teilnehmer");
        cbMember.setItems(memberFacade.getAll());

        Addressield addressield = new Addressield();
        addressield.setCaption("Adresse");

        DateField dfStart = new DateField("Beginn");
        DateField dfEnd = new DateField("Ende");

        Binder<LanParty> binder = new Binder<>();

        binder.setBean(lanParty);

        binder.bind(txName, LanParty::getName, LanParty::setName);
        binder.bind(txDescription, LanParty::getDescription, LanParty::setDescription);
        binder.bind(txLocation, LanParty::getLocation, LanParty::setLocation);
        binder.bind(dfStart, LanParty::getStartDate, LanParty::setStartDate);
        binder.bind(dfEnd, LanParty::getEndDate, LanParty::setEndDate);
        binder.bind(txCover, LanParty::getCoverPictureURL, LanParty::setCoverPictureURL);
        binder.bind(addressield, LanParty::getAddress, LanParty::setAddress);
        binder.bind(cbMember, lan -> lanPartyFacade.getMember(lan), LanParty::setMembers);

//        binder.bind(txStreet, lanParty1 -> lanParty1.getAddress().getStreet(), (lanParty1, value) -> lanParty1.getAddress().setStreet(value));
//        binder.bind(txCity, lanParty1 -> lanParty1.getAddress().getCity(), (lanParty1, value) -> lanParty1.getAddress().setCity(value));
//        binder.bind(txZip, lanParty1 -> lanParty1.getAddress().getZip(), (lanParty1, value) -> lanParty1.getAddress().setZip(value));
//        binder.bind(txCountry, lanParty1 -> lanParty1.getAddress().getCountry(), (lanParty1, value) -> lanParty1.getAddress().setCountry(value));
//        binder.bind(txAdName, lanParty1 -> lanParty1.getAddress().getName(), (lanParty1, value) -> lanParty1.getAddress().setName(value));

        Button save = new Button(create ? "Erstellen" : "Speichern");
        save.addClickListener(clickEvent -> {
            LanParty bean = binder.getBean();
            if(create){
                lanPartyFacade.update(bean);
            } else {
                lanPartyFacade.update(bean);
            }
            UI.getCurrent().getNavigator().navigateTo(LanPartyListView.VIEW_ID);
        });

        formLayout.addComponents(txName, dfStart, dfEnd, txLocation, txCover, addressield, cbMember, txDescription, save);
        return formLayout;
    }
}
