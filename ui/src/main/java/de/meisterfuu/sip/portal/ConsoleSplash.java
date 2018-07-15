package de.meisterfuu.sip.portal;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;


@Singleton
@Startup
public class ConsoleSplash {

    @PostConstruct
    public void splash() {
        
        System.out.println("\n"+
        "     _______.___________. __    __   __    __   __          __  .___  ___.    .______     ______     ______    __      \n" +
                "    /       |           ||  |  |  | |  |  |  | |  |        |  | |   \\/   |    |   _  \\   /  __  \\   /  __  \\  |  |     \n" +
                "   |   (----`---|  |----`|  |  |  | |  |__|  | |  |        |  | |  \\  /  |    |  |_)  | |  |  |  | |  |  |  | |  |     \n" +
                "    \\   \\       |  |     |  |  |  | |   __   | |  |        |  | |  |\\/|  |    |   ___/  |  |  |  | |  |  |  | |  |     \n" +
                ".----)   |      |  |     |  `--'  | |  |  |  | |  `----.   |  | |  |  |  |    |  |      |  `--'  | |  `--'  | |  `----.\n" +
                "|_______/       |__|      \\______/  |__|  |__| |_______|   |__| |__|  |__|    | _|       \\______/   \\______/  |_______|\n" +
                "                                                                                                                       ");
    }

}
