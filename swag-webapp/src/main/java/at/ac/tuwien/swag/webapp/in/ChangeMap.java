package at.ac.tuwien.swag.webapp.in;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.model.dao.MapDAO;
import at.ac.tuwien.swag.model.domain.Map;
import at.ac.tuwien.swag.webapp.SwagWebSession;

import com.google.inject.Inject;

public class ChangeMap extends InPage {
	
    private static final long serialVersionUID = -5939284250869774500L;

    @Inject
	private MapDAO mapDao;

	private DropDownChoice<String> defaultmaps;
  
    public ChangeMap(PageParameters parameters) {
        super(parameters);
        
        addMapSelection();
    }
    
    private void addMapSelection() {
    	 //Retrieve username from session
        SwagWebSession session = (SwagWebSession) getSession(); 
        String username = session.getUsername();
        
        //Retrieve all maps for the user
        String query = "SELECT mu.map FROM MapUser mu JOIN mu.user u WHERE u.username=:username";
        
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
  
        List<String> mapNames = new ArrayList<String>();
        List<Map> maps = mapDao.findByQuery(query, params);
        for(Map map: maps){
        	mapNames.add(map.getName());
        }
        
        defaultmaps = new DropDownChoice<String>("maps", mapNames) {
        	
            private static final long serialVersionUID = -8265215294167749280L;

            @Override
            protected boolean wantOnSelectionChangedNotifications() {
                return true;
            }

            @Override
            protected void onBeforeRender() {
                super.onBeforeRender();
                setDefaultMap();
            }

            @Override
            protected void onSelectionChanged(String newSelection) {
            	SwagWebSession session = (SwagWebSession) getSession(); 
                session.setMapname(newSelection);
                setResponsePage(MapPage.class);
            }

        };

        setDefaultMap();
        
        add(defaultmaps);

       // addSecuredComponent(defaultprojects, "ROLE_USER");
    }

    protected void setDefaultMap() {
    	SwagWebSession session = (SwagWebSession) getSession(); 
    	
        if (session.getMapname() != null) {
        
            defaultmaps.setDefaultModel(new Model<String>(session.getMapname()));
           
        } else {
            defaultmaps.setDefaultModel(new Model<String>());
        }
    }

}
