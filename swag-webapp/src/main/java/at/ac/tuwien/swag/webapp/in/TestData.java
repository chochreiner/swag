package at.ac.tuwien.swag.webapp.in;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

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
import at.ac.tuwien.swag.util.PasswordHasher;

import com.google.inject.Inject;

public class TestData extends InPage {
    private static final long serialVersionUID = -5939284250869774500L;

    @Inject
    private MapDAO mapDao;
    @Inject
    private UserDAO userDao;
    @Inject
    private MapUserDAO mapUserDao;
    @Inject
    private SquareDAO squareDao;

    @Inject
    private PasswordHasher hasher;

    @Inject
    // private EntityManager em;
    public TestData(PageParameters parameters) {
        super(parameters);

        add(new AjaxLazyLoadPanel("testDataLabel", new Model<String>()) {
            private static final long serialVersionUID = -5330241761878870869L;

            @Override
            public Component getLazyLoadComponent(String markupId) {
            	squareDao.beginTransaction();
            		squareDao.deleteAll();
            		mapUserDao.deleteAll();
            		mapDao.deleteAll();
            		userDao.deleteAll();
            	squareDao.commitTransaction();
            		
                setupUser();
                setupMap();
                assignPlayerToMap();

                return new Label(markupId, "TEST DATA CREATED");
            }
        });
    }

    private void setupUser() {
        System.out.println("####### Register users #######");

        User system = new User();
        system.setUsername("system");
        system.setFullname("Administration account");
        system.setAddress("The interblag");
        system.setEmail("swaf@swag.com");
        system.setPassword(hasher.hash("aaa"));
        system.setIsOnline( false );
        
        User nero = new User();
        nero.setUsername("nero");
        nero.setFullname("Nero Claudius Caesar Augustus Germanicus");
        nero.setAddress("Forum Romanum 1, Rom");
        nero.setEmail("chef@imperiumRomanum.it");
        nero.setPassword(hasher.hash("servus"));
        nero.setIsOnline( false );

        User ariovist = new User();
        ariovist.setUsername("ariovist");
        ariovist.setFullname("Ariovist der Germanne");
        ariovist.setAddress("Erberg 1");
        ariovist.setEmail("chef@markomannenweb.de");
        ariovist.setPassword(hasher.hash("bier1234"));
        ariovist.setIsOnline( false );

        userDao.beginTransaction();
        	userDao.insert(nero);
        	userDao.insert(ariovist);
        	userDao.insert( system );
        userDao.commitTransaction();
    }

    // private void testGetUser() {
    // System.out.println("####### List registered users #######");
    //
    // for (User user : userDao.getAll()) {
    // System.out.println(user);
    // }
    //
    // System.out.println("####### Find user by EmailAdress #######");
    //
    //
    //
    // for (User user : userDao.findByEmail("chef@imperiumRomanum.it")) {
    // System.out.println(user.getFullname() + " - " + user.getEmail());
    // }
    // }

    private void setupMap() {
        System.out.println("####### Create Map and Squares #######");

        int startX = 1;
        int endX = 50;
        int startY = 1;
        int endY = 50;

        Map map = new Map();
        map.setMaxNumUsers(100);
        map.setName("Markomannwar");
        map.setXSize(endX);
        map.setYSize(endY);

        Integer xAxis = 1;

        List<Square> squares = new ArrayList<Square>();
        synchronized ( squares ) {
            for (int y = startY; y <= endY; y++) {
            	for (int x = startX; x <= endX; x++) {
            		Square square = new Square();
            		square.setCoordX(x);
            		square.setCoordY(y);
            		square.setMap(map);
            		square.setIsHomeBase(false);
            		squares.add(square);
            		xAxis++;
                }
            }

            map.setSquares(squares);

            mapDao.beginTransaction();
            try {
            	this.mapDao.insert(map);
//            	for (Square sq : squares.toArray( new Square[0] ) ) {
//            		squareDao.insert(sq);
//            	}
            } finally {
            	mapDao.commitTransaction();            	
            }
        }       
    }

    private void assignPlayerToMap() {

        System.out.println("####### Get Map and AssignPlayer #######");

        User nero = userDao.findByUsername("nero");
        User ariovist = userDao.findByUsername("ariovist");

        Map playground = mapDao.findByName("Markomannwar");
        List<MapUser> users = new ArrayList<MapUser>();

        List<Square> nerosquares = new ArrayList<Square>();
        Square neroStartsquare = playground.getSquares().get(0);

        neroStartsquare.setIsHomeBase(true);
        nerosquares.add(neroStartsquare);

        MapUser neroMap = new MapUser();
        neroMap.setMap(playground);
        neroMap.setUser(nero);
        neroMap.setSquares(nerosquares);
        neroMap.setClayRessource(new StoredRessource(1500, SharedRessourceType.CLAY, new Date(), 1.0, neroMap));
        neroMap.setWoodRessource(new StoredRessource(1500, SharedRessourceType.WOOD, new Date(), 1.0, neroMap));
        neroMap.setGrainRessource(new StoredRessource(1500, SharedRessourceType.GRAIN, new Date(), 1.0, neroMap));
        neroMap.setIronRessource(new StoredRessource(1500, SharedRessourceType.IRON, new Date(), 1.0, neroMap));

        users.add(neroMap);

        List<Square> ariovistsquares = new ArrayList<Square>();
        Square ariovistStartsquare = playground.getSquares().get(50);

        ariovistStartsquare.setIsHomeBase(true);
        ariovistsquares.add(ariovistStartsquare);

        MapUser ariovistMap = new MapUser();
        ariovistMap.setMap(playground);
        ariovistMap.setUser(ariovist);
        ariovistMap.setSquares(ariovistsquares);

        ariovistMap.setClayRessource(new StoredRessource(1500, SharedRessourceType.CLAY, new Date(), 1.0, ariovistMap));
        ariovistMap.setWoodRessource(new StoredRessource(1500, SharedRessourceType.WOOD, new Date(), 1.0, ariovistMap));
        ariovistMap
            .setGrainRessource(new StoredRessource(1500, SharedRessourceType.GRAIN, new Date(), 1.0, ariovistMap));
        ariovistMap.setIronRessource(new StoredRessource(1500, SharedRessourceType.IRON, new Date(), 1.0, ariovistMap));

        users.add(ariovistMap);

        playground.setUsers(users);

        mapUserDao.beginTransaction();
        mapUserDao.insert(neroMap);
        mapUserDao.insert(ariovistMap);
        mapDao.insert(playground);
        squareDao.insert(neroStartsquare);
        squareDao.insert(ariovistStartsquare);
        mapUserDao.commitTransaction();
    }

    /*
     * private void setupBuildingForUserNero() {
     * 
     * System.out.println("####### Build Building for Nero #######");
     * 
     * List<BaseBuilding> basebuilding = new ArrayList<BaseBuilding>(); List<RessourceBuilding> ressourcebuilding = new
     * ArrayList<RessourceBuilding>();
     * 
     * BaseBuilding neroBarrack = new BaseBuilding(); neroBarrack.setLevel(1);
     * neroBarrack.setType(BaseBuildingType.BARRACKS); neroBarrack.setSquare(neroStartsquare);
     * 
     * RessourceBuilding neroLumberjack = new RessourceBuilding(); neroLumberjack.setLevel(1);
     * neroLumberjack.setType(RessourceBuildingType.LUMBERJACK); neroLumberjack.setSquare(neroStartsquare);
     * 
     * basebuilding.add(neroBarrack); ressourcebuilding.add(neroLumberjack);
     * 
     * neroStartsquare.setBaseBuildings(basebuilding); neroStartsquare.setResourceBuildings(ressourcebuilding);
     * 
     * tx.begin(); em.persist(neroBarrack); em.persist(neroLumberjack); em.merge(neroStartsquare); tx.commit();
     * 
     * }
     */
    // private void printHomeSquare() {
    //
    // System.out.println("####### Print Homesquares #######");
    //
    // for (Square sq : squareDao.findByIsHomeBase(true)) {
    // System.out.println("Coords: " + sq.getCoordX() + sq.getCoordY());
    // if (sq.getBaseBuildings() != null) {
    // System.out.println(sq.getBaseBuildings().toString());
    // }
    // if (sq.getResourceBuildings() != null) {
    // // System.out.println(sq.getResourceBuildings().toString());
    // }
    // }
    // }

    static void foo() {

        /*
         * System.out.println("####### Send Message Between User #######");
         * 
         * Set<User> recipients = new HashSet<User>(); recipients.add(ariovist);
         * 
         * Message message = new Message(); message.setTimestamp(new Date()); message.setSubject("welcome");
         * message.setText("You are going to die"); message.setFrom(nero); message.setTo(recipients);
         * 
         * tx.begin(); em.persist(message); tx.commit();
         * 
         * System.out.println("####### Read message #######");
         * 
         * for (Message incoming : messageDao.findBySubject("welcome")) { System.out.println("Date:    " +
         * incoming.getTimestamp()); System.out.println("Subject: " + incoming.getSubject());
         * System.out.println("Text:    " + incoming.getText()); }
         */
        // UNDO EVERYTHING
        // Helper.dropDatabase( em );
    }
}
