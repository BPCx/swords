package de.meisterfuu.sip.portal;

import de.meisterfuu.sip.portal.domain.Member;
import de.meisterfuu.sip.portal.domain.Role;
import de.meisterfuu.sip.portal.facades.LoginFacade;
import de.meisterfuu.sip.portal.repositories.MemberRepository;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class OnStartup {

    @Inject
    MemberRepository repository;

    @Inject
    LoginFacade loginFacade;

    @PostConstruct
    public void startUp() {
        initialDeploy();

        if(repository.getAll().size() == 0){
            Member fuu = new Member();
            fuu.setRole(Role.ADMIN);
            fuu.setName("dasfuu");
            loginFacade.setPassword(fuu, "test".toCharArray());
            repository.create(fuu);
        }

        Logger.getLogger(OnStartup.class.getName()).log(Level.INFO, "Build version: {0}", getBuildVersion());
    }

    private void initialDeploy() {
//        deployFile("config" + File.separator + "lang_de.json", true);
//        deployFile("config" + File.separator + "lang_doge.json", true);
    }

    private boolean deployFile(String fileName) {
        return deployFile(fileName, false);
    }

    private boolean deployFile(String fileName, boolean overwrite) {
        System.out.println("Deploy(" + overwrite + "): " + fileName);
        String writePath = System.getProperty("jboss.home.dir") + "/Portal/" + fileName;
        File file = new File(writePath);
        if (!file.exists() || overwrite) {
            file = file.getParentFile();
            file.mkdirs();
            try (PrintWriter writer = new PrintWriter(writePath, "UTF-8")) {
                writer.print(readFile(fileName));
                writer.flush();
                return true;
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    String readFile(String path)
            throws IOException, URISyntaxException {
        InputStream input = getClass().getClassLoader().getResourceAsStream(path);
        String result = convertStreamToString(input);

        input.close();
        return result;
    }

    String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is, "UTF-8").useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public String getBuildVersion() {

        // try to load from version from version.properties
        try {
            Properties versionFileProperties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("version.properties");
            if (inputStream != null) {
                versionFileProperties.load(inputStream);
                return versionFileProperties.getProperty("version", "") + " " + versionFileProperties.getProperty("timestamp", "");
            }
        } catch (IOException e) {
            Logger.getLogger(OnStartup.class.getName()).log(Level.SEVERE, null, e);
        }
        return "";
    }

}
