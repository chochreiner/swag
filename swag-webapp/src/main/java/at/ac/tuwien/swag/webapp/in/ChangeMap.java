package at.ac.tuwien.swag.webapp.in;

import java.util.HashMap;
import java.util.List;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.model.dao.MapDAO;
import at.ac.tuwien.swag.model.dao.MapUserDAO;
import at.ac.tuwien.swag.model.domain.Map;
import at.ac.tuwien.swag.webapp.SwagWebSession;

import com.google.inject.Inject;

public class ChangeMap extends InPage {
	
    private static final long serialVersionUID = -5939284250869774500L;

    @Inject
	private MapUserDAO mapUserDao;
    
    @Inject
	private MapDAO mapDao;
  
    public ChangeMap(PageParameters parameters) {
        super(parameters);

        SwagWebSession session = (SwagWebSession) getSession(); 
        
        String username = session.getUsername();
        
        System.out.println(username);
      
        String query = "SELECT mu.map FROM MapUser mu JOIN mu.user u WHERE u.username=:username";
        
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        
        List<Map> maps = mapDao.findByQuery(query, params);
        
        for(Map map : maps) {
        	System.out.println(map.getName());
        }
    }

}
