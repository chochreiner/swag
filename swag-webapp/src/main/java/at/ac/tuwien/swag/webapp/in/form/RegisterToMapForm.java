package at.ac.tuwien.swag.webapp.in.form;

import static at.ac.tuwien.swag.util.MapMaker.map;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import at.ac.tuwien.swag.model.dao.MapDAO;
import at.ac.tuwien.swag.model.dao.MapUserDAO;
import at.ac.tuwien.swag.model.dao.SquareDAO;
import at.ac.tuwien.swag.model.dao.UserDAO;
import at.ac.tuwien.swag.model.domain.Map;
import at.ac.tuwien.swag.model.domain.MapUser;
import at.ac.tuwien.swag.model.domain.SharedRessourceType;
import at.ac.tuwien.swag.model.domain.Square;
import at.ac.tuwien.swag.model.domain.StoredRessource;
import at.ac.tuwien.swag.model.domain.User;
import at.ac.tuwien.swag.webapp.SwagWebSession;

import com.google.inject.Inject;

public class RegisterToMapForm extends Form<Void> {
    private static final long serialVersionUID = 4979391078353931660L;

    @Inject
    MapDAO mapDao;

    @Inject
    MapUserDAO mapUserDao;

    @Inject
    private UserDAO userDao;

    @Inject
    private SquareDAO squareDao;
    private String mapname;

    private DropDownChoice<String> allmaps;

    public RegisterToMapForm(String id) {
        super(id);

        this.addAllMapSelection();
    }

    private void addAllMapSelection() {
        LoadableDetachableModel<List<String>> userMapList = new LoadableDetachableModel<List<String>>() {
            private static final long serialVersionUID = -5466406801708536032L;

            @Override
            protected List<String> load() {
                String username = ((SwagWebSession) getSession()).getUsername();

                // TODO replace me with an query
                List<String> usermap = new ArrayList<String>();
                for (Map map : mapDao.getAll()) {
                    boolean flag = true;
                    List<MapUser> mus = map.getUsers();
                    for (MapUser mu : mus) {
                        String uname = mu.getUser().getUsername();
                        if (uname.equals(username)) {
                            flag = false;
                        }
                    }
                    if (flag) {
                        usermap.add(map.getName());
                    }
                }
                return usermap;
            }
        };

        allmaps = new DropDownChoice<String>("allmaps", userMapList);
        allmaps.setDefaultModel(new Model<String>());
        add(allmaps);

    }

    @Override
    protected void onSubmit() {
        mapUserDao.beginTransaction();

        try {
            mapname = (String) allmaps.getDefaultModel().getObject();
            Map playground = mapDao.findByName(mapname);

            String username = ((SwagWebSession) getSession()).getUsername();
            User user = userDao.findByUsername(username);

            Square startsquare = findFreeSquareForHomeBase(playground);

            if (startsquare != null) {
                // Defines the homebase
                List<Square> usersquares = new ArrayList<Square>();
                startsquare.setIsHomeBase(true);
                usersquares.add(startsquare);

                // create MapUSer object
                MapUser mapUser = new MapUser();
                mapUser.setMap(playground);
                mapUser.setUser(user);
                mapUser.setSquares(usersquares);
                mapUser.setClayRessource(new StoredRessource(1500, SharedRessourceType.CLAY, new Date(), 1.0, mapUser));
                mapUser.setWoodRessource(new StoredRessource(1500, SharedRessourceType.WOOD, new Date(), 1.0, mapUser));
                mapUser
                    .setGrainRessource(new StoredRessource(1500, SharedRessourceType.GRAIN, new Date(), 1.0, mapUser));
                mapUser.setIronRessource(new StoredRessource(1500, SharedRessourceType.IRON, new Date(), 1.0, mapUser));

                // add mapuser to map
                playground.getUsers().add(mapUser);

                // persist the stuff
                mapUserDao.insert(mapUser);
                mapUserDao.commitTransaction();
                mapDao.insert(playground);
                squareDao.insert(startsquare);

                info("Your was added to the map: " + mapname);
            } else {
                info("New free squares avaibile on map: " + mapname);
            }
        } catch (NoResultException e) {
            info(e.getMessage());
        } finally {
            mapUserDao.commitTransaction();
        }
    }

    private Square findFreeSquareForHomeBase(Map map) {
        final String query =
            " SELECT " +
                    "	s " +
                    " FROM " +
                    "	Square s " +
                    " WHERE " +
                    "	s.user = null AND s.map = :map";

        List<Square> sq = squareDao.findByQuery(query, map("map", map));

        if (sq.isEmpty()) {
            return null;
        } else {
            return sq.get(0);
        }
    }

}
