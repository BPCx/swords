package de.meisterfuu.sip.portal.shared;

import com.vaadin.data.Binder;
import com.vaadin.ui.*;
import de.meisterfuu.sip.portal.domain.Address;

/**
 * Created by meisterfuu.
 */
public class Addressield extends CustomField<Address> {

    private  Binder<Address> binder = new Binder<>();
    private Address address;

    @Override
    protected Component initContent() {
        VerticalLayout baseLayout = new VerticalLayout();
        TextField txStreet = new TextField();
        TextField txCity = new TextField();
        TextField txZip = new TextField();
        TextField txName = new TextField();
        TextField txCountry = new TextField();
        TextField txLon = new TextField();
        TextField txLat = new TextField();

        txCity.setPlaceholder("Stadt");
        txStreet.setPlaceholder("Straße");
        txZip.setPlaceholder("PLZ");
        txName.setPlaceholder("Name");
        txCountry.setPlaceholder("Land");
        txLon.setPlaceholder("Longitude");
        txLat.setPlaceholder("Latitude");

        binder.bind(txStreet, Address::getStreet, Address::setStreet);
        binder.bind(txCity, Address::getCity, Address::setCity);
        binder.bind(txZip, Address::getZip, Address::setZip);
        binder.bind(txName, Address::getName, Address::setName);
        binder.bind(txCountry, Address::getCountry, Address::setCountry);
        binder.bind(txLon, Address::getLon, Address::setLon);
        binder.bind(txLat, Address::getLat, Address::setLat);

        baseLayout.addComponents(txName, txStreet, txZip, txCity, txCountry, txLon, txLat);

        baseLayout.setMargin(false);

        if(address != null){
            binder.setBean(address);
        } else {
            clear();
        }

        return baseLayout;
    }

    @Override
    protected void doSetValue(Address address) {
        this.address = address;
        binder.setBean(address);
    }

    @Override
    public Address getValue() {
        return binder.getBean();
    }

    @Override
    public Address getEmptyValue() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {
        doSetValue(new Address());
    }
}
