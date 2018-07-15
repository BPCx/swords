package de.meisterfuu.sip.portal.facades;

import de.meisterfuu.sip.portal.domain.Game;
import de.meisterfuu.sip.portal.domain.LanParty;
import de.meisterfuu.sip.portal.domain.Member;
import de.meisterfuu.sip.portal.repositories.MemberRepository;

import javax.ejb.Stateful;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by meisterfuu.
 */
@Stateful
public class MemberFacade {

    @Inject
    private MemberRepository memberRepository;

    public Member create(Member member) {
        return memberRepository.create(member);
    }

    public void delete(Object id) {
        memberRepository.delete(id);
    }

    public Member find(Object id) {
        Member member = memberRepository.find(id);
        if(member != null && member.getGames() != null){
            member.getGames().isEmpty();
        }
        if(member != null && member.getLanPartys() != null){
            member.getLanPartys().isEmpty();
        }
        return member;
    }

    public Member update(Member member) {
        return memberRepository.update(member);
    }

    public ArrayList<Member> getAll() {
        return memberRepository.getAll();
    }

    public Set<Game> getGames(Member member) {
        Member attached = find(member.getId());
        if(attached == null){
            return null;
        }
        return attached.getGames();
    }

    public Set<LanParty> getLanPartys(Member member) {
        Member attached = find(member.getId());
        if(attached == null){
            return null;
        }
        return attached.getLanPartys();
    }

}
