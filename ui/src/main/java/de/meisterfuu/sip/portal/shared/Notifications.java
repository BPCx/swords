package de.meisterfuu.sip.portal.shared;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;


public class Notifications {

    /* Configuration */
    private static final Position POSITION = Position.TOP_RIGHT;
    private static final String SUCCESS_STYLE = "success";
    private static final String ERROR_STYLE = "failure";
    private static final String WARNING_STYLE = "warning";
    private static final boolean BAR = false;
    private static final boolean SMALL = false;
    private static final boolean CLOSEABLE = true;
    private static final int NO_TIME_OUT = -1;
    private static final int WARNING_TIME_OUT = 4000;
    private static final int SUCCESS_TIME_OUT = 3000;
    private static final int INFO_TIME_OUT = 3000;

    public static void showError(String caption, String message) {
        Notification notification = new Notification(caption, message, Notification.Type.ERROR_MESSAGE, true);
        notification.setStyleName(ERROR_STYLE);
        notification.setDelayMsec(NO_TIME_OUT);
        show(notification);
    }

    public static void showWarning(String caption, String message) {
        Notification notification = new Notification(caption, message, Notification.Type.WARNING_MESSAGE, true);
        notification.setStyleName(WARNING_STYLE);
        notification.setDelayMsec(WARNING_TIME_OUT);

        show(notification);
    }

    public static void showSuccess(String caption, String message) {
        Notification notification = new Notification(caption, message, Notification.Type.HUMANIZED_MESSAGE, true);
        notification.setStyleName(SUCCESS_STYLE);
        notification.setDelayMsec(SUCCESS_TIME_OUT);
        show(notification);
    }

    public static void showSuccess(String title, String content, Page page) {
        Notification notification = new Notification(title, content, Notification.Type.HUMANIZED_MESSAGE, true);
        notification.setStyleName(SUCCESS_STYLE);
        notification.setDelayMsec(SUCCESS_TIME_OUT);
        show(notification,page);
    }

    public static void showInfo(String caption, String message) {
        showInfo(caption, message, INFO_TIME_OUT);
    }

    public static void showConfirmInfo(String caption, String message) {
        showInfo(caption, message, NO_TIME_OUT);
    }

    public static void showInfo(String caption, String message, int timeOut) {
        Notification notification = new Notification(caption, message, Notification.Type.HUMANIZED_MESSAGE, true);
        notification.setIcon(VaadinIcons.INFO);
        notification.setStyleName("dark");
        notification.setDelayMsec(timeOut);
//        notification.setDelayMsec(Math.round((message.length()/35.33f) * 1000f)); // Durchschnittliche Zeit, die der Benutzer zum Lesen benötigt
        show(notification, Position.MIDDLE_CENTER);
    }

    public static void showTechnicalError() {
        String caption = "Technischer Fehler";
        String message = "Es ist ein interner Fehler aufgetreten.";
        Notification notification = new Notification(caption, message, Notification.Type.ERROR_MESSAGE, true);
        notification.setPosition(POSITION);
        notification.setStyleName("error bar closable");
        notification.setIcon(VaadinIcons.EXCLAMATION);
        notification.setDelayMsec(-1);
        notification.show(Page.getCurrent());
    }

    public static void showTechnicalError(Exception e) {
        String caption = e.getClass().getSimpleName();
        String message = "<b>" + e.getMessage() + "</b><br/>";
        for (int i = 0; i < e.getStackTrace().length; i++) {
            StackTraceElement elem = e.getStackTrace()[i];
            message += "| " + elem.getClassName() + "\t@" + elem.getMethodName() + " (" + elem.getLineNumber() + ")<br/>";
        }

        Notification notification = new Notification(caption, message, Notification.Type.ERROR_MESSAGE, true);
        notification.setPosition(POSITION);
        notification.setStyleName("error bar closable");
        notification.setIcon(VaadinIcons.EXCLAMATION);
        notification.setDelayMsec(-1);
        notification.show(Page.getCurrent());
    }

    private static void show(Notification notification, Position position) {
        prepareNotification(notification, position);
        notification.show(Page.getCurrent());
    }

    private static void show(Notification notification, Position position,Page page) {
        prepareNotification(notification, position);
        notification.show(page);
    }

    private static void prepareNotification(Notification notification, Position position) {
        notification.setPosition(position);
        if (BAR) {
            notification.setStyleName(notification.getStyleName() + " bar");
        }
        if (SMALL) {
            notification.setStyleName(notification.getStyleName() + " small");
        }
        if (CLOSEABLE) {
            notification.setStyleName(notification.getStyleName() + " closable");
        }
    }

    private static void show(Notification notification) {
        show(notification, POSITION);
    }

    private static void show(Notification notification, Page page) {
        show(notification, POSITION, page);
    }



}
