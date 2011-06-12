package at.ac.tuwien.swag.webapp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.ac.tuwien.swag.model.dao.MapUserDAO;
import at.ac.tuwien.swag.model.dao.StoredRessourceDAO;
import at.ac.tuwien.swag.model.domain.MapUser;
import at.ac.tuwien.swag.model.domain.StoredRessource;
import at.ac.tuwien.swag.webapp.service.RessourceService;

import com.google.inject.Inject;

public class RessourceServiceImpl implements RessourceService {

    @Inject
    private MapUserDAO mapUserDAO;

    @Inject
    private StoredRessourceDAO ressourceDAO;

    @Override
    public void update(String username, String mapname) {

        mapUserDAO.beginTransaction();
        MapUser user = setMapuser(username, mapname);
        user.setClayRessource(updateRessource(user.getClayRessource()));
        user.setWoodRessource(updateRessource(user.getWoodRessource()));
        user.setGrainRessource(updateRessource(user.getGrainRessource()));
        user.setIronRessource(updateRessource(user.getIronRessource()));

        mapUserDAO.update(user);
        mapUserDAO.commitTransaction();
    }

    private StoredRessource updateRessource(StoredRessource ressource) {

        Long dif = new Date().getTime() - ressource.getLastUpdate().getTime();

        dif = dif / 10;

        Double amount = dif * ressource.getCurrentRate();

        ressource.setAmount((int) (ressource.getAmount() + amount));

        ressourceDAO.update(ressource);

        return ressource;
    }

    private MapUser setMapuser(String username, String mapname) {
        String query =
            "SELECT m FROM MapUser m LEFT JOIN FETCH m.ironRessource LEFT JOIN FETCH m.clayRessource" +
                    "LEFT JOIN FETCH m.woodRessource LEFT JOIN FETCH m.grainRessource" +
                    " WHERE m.user.username = :username AND m.map.name = :mapname";

        Map<String, String> values = new HashMap<String, String>();
        values.put("username", username);
        values.put("mapname", mapname);

        List<MapUser> buffer = mapUserDAO.findByQuery(query, values);

        if (!buffer.isEmpty()) {
            return buffer.get(0);
        }
        return null;
    }
}
