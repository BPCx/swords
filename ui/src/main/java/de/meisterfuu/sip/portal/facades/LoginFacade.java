package de.meisterfuu.sip.portal.facades;

import de.meisterfuu.sip.portal.domain.Member;
import de.meisterfuu.sip.portal.repositories.MemberRepository;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.ejb.Stateful;
import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

/**
 * Created by meisterfuu.
 */
@Stateful
public class LoginFacade {

    @Inject
    private MemberRepository memberRepository;

    public boolean checkPassword(String username, char[] password){
        Member loginMember = memberRepository.findByUsername(username);
        String passwordSalt = loginMember.getPasswordSalt();
        String hash = hashPassword(password, passwordSalt.getBytes());
        return username.equals(loginMember.getName()) && hash.equals(loginMember.getPasswordHash());
    }

    public Member setPassword(Member member, char[] newPassword){
        if(newPassword.length == 0){
            return member;
        }
        String passwordSalt = UUID.randomUUID().toString();
        String hash = hashPassword(newPassword, passwordSalt.getBytes());
        member.setPasswordHash(hash);
        member.setPasswordSalt(passwordSalt);
        return member;
    }

    public static String hashPassword( final char[] password, final byte[] salt) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
            int keyLength = 256;
            int iterations = 5;
            PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
            SecretKey key = skf.generateSecret( spec );
            byte[] res = key.getEncoded( );
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< res.length ;i++){
                sb.append(Integer.toString((res[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch( NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

//    public String getPasswordHash(String passwordToHash, String salt){
//        String generatedPassword = null;
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-512");
//            md.update(salt.getBytes());
//            byte[] bytes = md.digest(passwordToHash.getBytes());
//            StringBuilder sb = new StringBuilder();
//            for(int i=0; i< bytes.length ;i++){
//                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
//            }
//            generatedPassword = sb.toString();
//        } catch (NoSuchAlgorithmException e){
//            e.printStackTrace();
//        }
//        return generatedPassword;
//    }

}
