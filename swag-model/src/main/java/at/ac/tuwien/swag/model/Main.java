package at.ac.tuwien.swag.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import at.ac.tuwien.swag.model.dao.MapDAO;
import at.ac.tuwien.swag.model.dao.MessageDAO;
import at.ac.tuwien.swag.model.dao.SquareDAO;
import at.ac.tuwien.swag.model.dao.UserDAO;
import at.ac.tuwien.swag.model.domain.BaseBuilding;
import at.ac.tuwien.swag.model.domain.BaseBuildingType;
import at.ac.tuwien.swag.model.domain.Map;
import at.ac.tuwien.swag.model.domain.MapUser;
import at.ac.tuwien.swag.model.domain.Message;
import at.ac.tuwien.swag.model.domain.RessourceBuilding;
import at.ac.tuwien.swag.model.domain.RessourceBuildingType;
import at.ac.tuwien.swag.model.domain.Square;
import at.ac.tuwien.swag.model.domain.User;


public class Main {
    public static void main(String[] args) throws Exception {
    	EntityManagerFactory factory = Persistence.createEntityManagerFactory("swag");
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        UserDAO    userDao = new UserDAO(em);
        MapDAO     mapDao = new MapDAO(em);
        MessageDAO messageDao = new MessageDAO(em);
        SquareDAO  squareDao = new SquareDAO(em);

        System.out.println("####### Register users #######");

        User nero = new User();
        nero.setName("nero");
        nero.setFullname("Nero Claudius Caesar Augustus Germanicus");
        nero.setAddress("Forum Romanum 1, Rom");
        nero.setEmail("chef@imperiumRomanum.it");
        nero.setPassword("servus");

        User ariovist = new User();
        ariovist.setName("ariovist");
        ariovist.setFullname("Ariovist der Germanne");
        ariovist.setAddress("Erberg 1");
        ariovist.setEmail("chef@markomannenweb.de");
        ariovist.setPassword("bier1234");

        tx.begin();

        em.persist(nero);
        em.persist(ariovist);

        tx.commit();

        System.out.println("####### List registered users #######");

         for (User user : userDao.findAll()) {
         System.out.println(user);
         }

        System.out.println("####### Find user by EmailAdress #######");

        for (User user : userDao.findByEmail("chef@imperiumRomanum.it")) {
            System.out.println(user.getFullname() + " - " + user.getEmail());
        }

        System.out.println("####### Create Map and Squares #######");

        Map map = new Map();
        map.setMaxNumUsers(100);
        map.setName("Markomannwar");
        map.setXSize(10);
        map.setYSize(10);

        Integer xAxis = 1;
        Integer yAxis = 1;

        List<Square> squares = new ArrayList<Square>();

        for (int i = 0; i < 100; i++) {
            if (xAxis > 10) {
                xAxis = 1;
                yAxis++;
            }

            Square square = new Square();
            square.setCoordX(xAxis);
            square.setCoordY(yAxis);
            square.setMap(map);
            square.setIsHomeBase(false);
            squares.add(square);
            xAxis++;
        }

        map.setSquares(squares);

        tx.begin();
        em.persist(map);
        for (Square sq : squares) {
            em.persist(sq);
        }

        tx.commit();

        System.out.println("####### Get Map and AssignPlayer #######");

        Map playground = mapDao.findByName("Markomannwar");
        List<MapUser> users = new ArrayList<MapUser>();

        List<Square> nerosquares = new ArrayList<Square>();
        Square neroStartsquare = squares.get(0);

        neroStartsquare.setIsHomeBase(true);
        nerosquares.add(neroStartsquare);

        MapUser neroMap = new MapUser();
        neroMap.setMap(playground);
        neroMap.setUser(nero);
        neroMap.setSquares(nerosquares);

        users.add(neroMap);

        List<Square> ariovistsquares = new ArrayList<Square>();
        Square ariovistStartsquare = squares.get(50);

        ariovistStartsquare.setIsHomeBase(true);
        ariovistsquares.add(ariovistStartsquare);

        MapUser ariovistMap = new MapUser();
        ariovistMap.setMap(playground);
        ariovistMap.setUser(ariovist);
        ariovistMap.setSquares(ariovistsquares);

        users.add(ariovistMap);

        playground.setUsers(users);

        tx.begin();
        em.persist(neroMap);
        em.persist(ariovistMap);
        em.merge(playground);
        em.merge(neroStartsquare);
        tx.commit();

        System.out.println("####### Build Building for Nero #######");

        List<BaseBuilding>      basebuilding      = new ArrayList<BaseBuilding>();
        List<RessourceBuilding> ressourcebuilding = new ArrayList<RessourceBuilding>();

        BaseBuilding neroBarrack = new BaseBuilding();
        neroBarrack.setLevel(1);
        neroBarrack.setType(BaseBuildingType.BARRACKS);
        neroBarrack.setSquare(neroStartsquare);

        RessourceBuilding neroLumberjack = new RessourceBuilding();
        neroLumberjack.setLevel(1);
        neroLumberjack.setType(RessourceBuildingType.LUMBERJACK);
        neroLumberjack.setSquare(neroStartsquare);

        basebuilding.add(neroBarrack);
        ressourcebuilding.add(neroLumberjack);

        neroStartsquare.setBaseBuildings(basebuilding);
        neroStartsquare.setResourceBuildings(ressourcebuilding);

        tx.begin();
        em.persist(neroBarrack);
        em.persist(neroLumberjack);
        em.merge(neroStartsquare);
        tx.commit();

        System.out.println("####### Print Homesquares #######");

        for (Square sq : squareDao.findByIsHomeBase(true)) {
            System.out.println("Coords: " + sq.getCoordX() + sq.getCoordY());
            if (sq.getBaseBuildings() != null) {
                 System.out.println(sq.getBaseBuildings().toString());
            }
            if (sq.getResourceBuildings() != null) {
                // System.out.println(sq.getResourceBuildings().toString());
            }
        }

        System.out.println("####### Send Message Between User #######");

        Set<User> recipients = new HashSet<User>();
        recipients.add(ariovist);

        Message message = new Message();
        message.setTimestamp(new Date());
        message.setSubject("welcome");
        message.setText("You are going to die");
        message.setFrom(nero);
        message.setTo(recipients);

        tx.begin();
        em.persist(message);
        tx.commit();

        System.out.println("####### Read message #######");

        for (Message incoming : messageDao.findBySubject("welcome")) {
            System.out.println("Date:    " + incoming.getTimestamp());
            System.out.println("Subject: " + incoming.getSubject());
            System.out.println("Text:    " + incoming.getText());
        }

        // UNDO EVERYTHING
        Helper.dropDatabase( em );
    }
}