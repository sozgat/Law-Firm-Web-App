/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hukuk.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author said
 */

@ManagedBean
@SessionScoped
public class IcraUserController {
    
    private List<DavaIslemleri> davaGroup = null;
    
    @PostConstruct
    public void init(){
        davaGroup = new ArrayList<DavaIslemleri>();
    }

    public void add(){
        davaGroup.add(new DavaIslemleri());
    }
    
    public void remove(DavaIslemleri e){
        davaGroup.remove(e);
    }
    
    public String save(){
        int icraId;
        
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        String id = params.get("icraId");
        icraId = Integer.parseInt(id);
        
        VeriTabaniIslemleri vti = new VeriTabaniIslemleri();
        
        for(DavaIslemleri davaGroup1 : davaGroup)
        {
            vti.sqlKomut = "INSERT INTO TBLICRA_BILGILER(TIP,AD,SOYAD,TCKIMLIKNO,DOGUMTARIH,SAVUNMA,IDICRALAR) "
                    + "VALUES('"+davaGroup1.getDavaTuru()+"','"+davaGroup1.getAd()+"','"+davaGroup1.getSoyad()+"','"+davaGroup1.getTcKimlikNo()+
                    "',"+DbFunctions.stringToDate(davaGroup1.getDogumTarihi())+",'"+davaGroup1.getSavunmasi()+"',"+icraId+")";
            vti.uygula();
        }
        
        return "anasayfa.xhtml";
    }

    public List<DavaIslemleri> getDavaGroup() {
        return davaGroup;
    }

    public void setDavaGroup(List<DavaIslemleri> davaGroup) {
        this.davaGroup = davaGroup;
    }
}
