package de.meisterfuu.sip.portal.facades;

import de.meisterfuu.sip.portal.domain.LanParty;
import de.meisterfuu.sip.portal.domain.Member;
import de.meisterfuu.sip.portal.domain.WebLink;
import de.meisterfuu.sip.portal.repositories.LanPartyRepository;

import javax.ejb.Stateful;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by meisterfuu.
 */
@Stateful
public class LanPartyFacade {

    @Inject
    private LanPartyRepository lanPartyRepository;


    public LanParty create(LanParty lanParty) {
        return lanPartyRepository.create(lanParty);
    }

    public void delete(Object id) {
        lanPartyRepository.delete(id);
    }

    public LanParty find(Object id) {
        LanParty lanParty = lanPartyRepository.find(id);
        if(lanParty != null && lanParty.getWebLinks() != null){
            lanParty.getWebLinks().isEmpty();
        }
        if(lanParty != null && lanParty.getMembers() != null){
            lanParty.getMembers().isEmpty();
        }
        return lanParty;
    }

    public LanParty update(LanParty lanParty) {
        return lanPartyRepository.update(lanParty);
    }

    public ArrayList<LanParty> getAll() {
        return lanPartyRepository.getAll();
    }

    public Set<Member> getMember(LanParty lanParty) {
        LanParty attached = find(lanParty.getId());
        if(attached == null){
            return null;
        }
        return attached.getMembers();
    }

    public Set<WebLink> getWebLinks(LanParty lanParty) {
        LanParty attached = find(lanParty.getId());
        if(attached == null){
            return null;
        }
        return attached.getWebLinks();
    }

}
