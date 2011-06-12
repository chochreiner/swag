package at.ac.tuwien.swag.webapp.in;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import at.ac.tuwien.swag.model.dao.MapUserDAO;
import at.ac.tuwien.swag.model.domain.MapUser;
import at.ac.tuwien.swag.model.domain.RessourceType;
import at.ac.tuwien.swag.webapp.SwagWebSession;
import at.ac.tuwien.swag.webapp.in.map.MapPage;

import com.google.inject.Inject;

public class ResourcenSidePanel extends Panel {
    private static final long serialVersionUID = -1346674179641818989L;

    @Inject
    private MapUserDAO mapUserDAO;

    private MapUser mapuser = null;

    public ResourcenSidePanel(String id) {
        super(id);

        setMapuser();

        add(setRessourceLabel("wood", RessourceType.WOOD, mapuser));
        add(setRessourceLabel("grain", RessourceType.GRAIN, mapuser));
        add(setRessourceLabel("clay", RessourceType.CLAY, mapuser));
        add(setRessourceLabel("iron", RessourceType.IRON, mapuser));
    }

    private Label setRessourceLabel(String id, RessourceType typ, MapUser mapuser) {
    	
    	String resCount="";
    	switch(typ) {
    	
    	case WOOD:
    		if(mapuser != null && mapuser.getWoodRessource() !=null) {
    			resCount = mapuser.getWoodRessource().getAmount().toString();
    		}
    		break;
    		
    	case IRON:
    		if(mapuser != null && mapuser.getIronRessource() !=null) {
    			resCount = mapuser.getIronRessource().getAmount().toString();
    		}
    		break;
    		
    	case CLAY:
    		if(mapuser != null && mapuser.getClayRessource() !=null) {
    			resCount = mapuser.getClayRessource().getAmount().toString();
    		}
    		break;
    		
    	case GRAIN:
    		if(mapuser != null && mapuser.getGrainRessource() !=null) {
    			resCount = mapuser.getGrainRessource().getAmount().toString();
    		}
    		break;
    	}
    	
    	return  new Label(id, resCount);
    }
    
    private void setMapuser() {
        String query =
            "SELECT m FROM MapUser m LEFT JOIN FETCH m.squares WHERE m.user.username = :username AND m.map.name = :mapname";

        SwagWebSession session = (SwagWebSession) getSession();

        if (session.getMapname() == null) {
            return;
        }

        Map<String, String> values = new HashMap<String, String>();
        values.put("username", session.getUsername());
        values.put("mapname", session.getMapname());

        List<MapUser> buffer = mapUserDAO.findByQuery(query, values);

        if (!buffer.isEmpty()) {
            mapuser = buffer.get(0);
        } else {
            setResponsePage(MapPage.class);
        }
    }
}
