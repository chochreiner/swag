package at.ac.tuwien.swag.webapp.in;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import at.ac.tuwien.swag.model.dao.MapUserDAO;
import at.ac.tuwien.swag.model.domain.MapUser;
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

        SwagWebSession session = (SwagWebSession) getSession();

        setMapuser();

        if (mapuser != null) {
            add(new Label("wood", mapuser.getWoodRessource().getAmount().toString()));
            add(new Label("grain", mapuser.getGrainRessource().getAmount().toString()));
            add(new Label("clay", mapuser.getClayRessource().getAmount().toString()));
            add(new Label("iron", mapuser.getIronRessource().getAmount().toString()));
        } else {
            add(new Label("wood", ""));
            add(new Label("grain", ""));
            add(new Label("clay", ""));
            add(new Label("iron", ""));

        }

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
