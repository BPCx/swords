package de.meisterfuu.sip.portal.core;

import com.vaadin.ui.*;
import org.apache.shiro.SecurityUtils;


public abstract class AbstractPermissionView extends AbstractView{


    private String permission;

    public AbstractPermissionView(String permission){
        this.permission = permission;
    }

    public Component buildContent(){
        if(SecurityUtils.getSubject().isPermitted(permission)){
            return buildPermittedContent();
        } else {
            return new Label("Insufficient Permissions");
        }
    }

    public abstract Component buildPermittedContent();

}
