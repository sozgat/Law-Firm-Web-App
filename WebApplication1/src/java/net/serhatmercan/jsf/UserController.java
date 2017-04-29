package UserController;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped

public class UserController {
    
    private List<DavaKayit> davaGroup = null;
    
    @PostConstruct
    public void init(){
        davaGroup = new ArrayList<DavaKayit>();
    }

    public void add(){
        davaGroup.add(new DavaKayit());
    }
    
    public void remove(DavaKayit e){
        davaGroup.remove(e);
    }
    
    public String save(){
        return "result.xhtml?faces-redirect=true";
    }

    public List<DavaKayit> getDavaGroup() {
        return davaGroup;
    }

    public void setDavaGroup(List<DavaKayit> davaGroup) {
        this.davaGroup = davaGroup;
    }
    
}
