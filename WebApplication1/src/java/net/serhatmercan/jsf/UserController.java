package net.serhatmercan.jsf;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped

public class UserController {
    
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
