package at.ac.tuwien.swag.webapp.in.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import at.ac.tuwien.swag.model.dao.MapDAO;
import at.ac.tuwien.swag.model.dao.SquareDAO;
import at.ac.tuwien.swag.model.dao.UserDAO;
import at.ac.tuwien.swag.model.domain.Map;
import at.ac.tuwien.swag.model.domain.Square;

import com.google.inject.Inject;


public class GameMapDataProvider{
	
	
	
	private MapDAO mapDao;
	private SquareDAO squareDao;
	private EntityManager em;

	public GameMapDataProvider() {
		
		
	}
	
	public GameMapDataProvider(MapDAO mapDao, SquareDAO squareDao, EntityManager em) {
		this.mapDao = mapDao;
		this.squareDao = squareDao;
		
		// TODO fieser fieser hack
		this.em = em;
	}

	public List<List<Square>> getMap() {
		
		ArrayList<List<Square>> mapRowList = new ArrayList<List<Square>>();
	
		Map map = mapDao.findByName("Markomannwar");
		System.out.println(map);
		String query = "SELECT s FROM Map m JOIN m.squares s WHERE m.id = :mapID AND s.coordY = :coordY";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("mapID", ""+map.getId());
		
		
		for(int i=0; i < map.getYSize(); i++) {
			
			params.put("coordY", ""+i);
			
			//List<Square> squaresX = squareDao.findByQuery(query, params);
			
			// TODO fieser fieser hack
			Query emQuery = em.createQuery(query);
			emQuery.setParameter("mapID", map.getId());
			emQuery.setParameter("coordY", i);
			
			List<Square> squaresX = emQuery.getResultList();
			
			mapRowList.add(squaresX);
		}
		
		return mapRowList;
	}
	
}
