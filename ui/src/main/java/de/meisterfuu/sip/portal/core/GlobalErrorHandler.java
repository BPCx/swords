package de.meisterfuu.sip.portal.core;

import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorHandler;
import de.meisterfuu.sip.portal.shared.Notifications;


public class GlobalErrorHandler implements ErrorHandler {

    @Override
    public void error(ErrorEvent event) {
        event.getThrowable().printStackTrace();
//            Notifications.showError("Error", ExceptionUtils.getRootCause(event.getThrowable()).toString());
        Notifications.showError("Error", event.getThrowable().getMessage());
    }

}
