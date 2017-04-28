/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.serhatmercan.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author said
 */
@ManagedBean
@ViewScoped
public class TekrarDavaBilgileriBean implements Serializable{
    private List<TekrarDavaBilgileri> alan1;
    public TekrarDavaBilgileriBean(){
         alan1 = new ArrayList();

        alan1.add(new TekrarDavaBilgileri());
    }
    public void setFields(List<TekrarDavaBilgileri> p_lFields)
    {
        alan1 = p_lFields;
    }

    public List<TekrarDavaBilgileri> getFields()
    {
        return alan1;
    }

    public void onButtonRemoveFieldClick(final TekrarDavaBilgileri p_oField)
    {
        alan1.remove(p_oField);
    }

    public void onButtonAddFieldClick(AjaxBehaviorEvent p_oEvent)
    {
        alan1.add(new TekrarDavaBilgileri());
    }
}
    

