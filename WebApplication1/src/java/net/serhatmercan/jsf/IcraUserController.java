/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.serhatmercan.jsf;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author said
 */

@ManagedBean
@SessionScoped
public class IcraUserController {
    
    private List<DavaBilgileri> davaGroup = null;
    
    @PostConstruct
    public void init(){
        davaGroup = new ArrayList<DavaBilgileri>();
    }

    public void add(){
        davaGroup.add(new DavaBilgileri());
    }
    
    public void remove(DavaBilgileri e){
        davaGroup.remove(e);
    }
    
    public String save(){
        return "result.xhtml?faces-redirect=true";
    }

    public List<DavaBilgileri> getDavaGroup() {
        return davaGroup;
    }

    public void setDavaGroup(List<DavaBilgileri> davaGroup) {
        this.davaGroup = davaGroup;
    }
}
