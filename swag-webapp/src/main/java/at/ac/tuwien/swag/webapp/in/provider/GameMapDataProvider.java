package at.ac.tuwien.swag.webapp.in.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import at.ac.tuwien.swag.model.dao.MapDAO;
import at.ac.tuwien.swag.model.dao.SquareDAO;
import at.ac.tuwien.swag.model.domain.Map;
import at.ac.tuwien.swag.model.domain.Square;


public class GameMapDataProvider{
	
	
	
	private MapDAO mapDao;
	private SquareDAO squareDao;
	private HashMap<Integer, HashMap<Integer,Square>> fullMap;
	private Integer mapXSize;
	private Integer mapYSize;
	
	public GameMapDataProvider(String mapname, MapDAO mapDao, SquareDAO squareDao) {
		this.mapDao = mapDao;
		this.squareDao = squareDao;
		this.fullMap = new HashMap<Integer, HashMap<Integer,Square>>();
		
		this.loadFullMap(mapname);
	}

	private void loadFullMap(String mapName) {
	
		Map map = mapDao.findByName(mapName);
		for(Square square :map.getSquares()) {
			
			addSquareToFullMap(square, fullMap);
		}
		
		mapXSize = map.getXSize();
		mapYSize = map.getYSize();
	}
	
	/**
	 * 
	 * 
	 * @param square
	 * @param map
	 */
	private void addSquareToFullMap(Square square, HashMap<Integer, HashMap<Integer,Square>> map ) {
		
		HashMap<Integer, Square> row = map.get(square.getCoordY());
		if(row == null) {
			// create new row list
			row = new HashMap<Integer, Square>();
			map.put(square.getCoordY(), row);
		}
		
		// Hopefully the is no square already
		if(row.get(square.getCoordX()) == null) {
			row.put(square.getCoordX(), square);
		}
	}
	
	
	public List<List<Square>> getPartialMap(Integer startX, Integer startY, Integer endX, Integer endY) {
		
		ArrayList<List<Square>> mapList = new ArrayList<List<Square>>();
		for(int y = startY; y <= endY; y++) {
			
			HashMap<Integer, Square> row = this.fullMap.get(y);
			if(row != null) {
				
				ArrayList<Square> mapRowList = new ArrayList<Square>();
				for(int x = startX; x <= endX; x++) {
					
					Square square = row.get(x);
					if(square != null) {
						
						mapRowList.add(square);
					}else { return null;}
				}
				mapList.add(mapRowList);
			}else { return null;}
			
		}
		
		return mapList;
	}
	
	public List<List<Square>> getMap() {
		
		ArrayList<List<Square>> mapRowList = new ArrayList<List<Square>>();
	
		Map map = mapDao.findByName("Markomannwar");
		
		String query = "SELECT s FROM Map m JOIN m.squares s WHERE m.id = :mapID AND s.coordY = :coordY";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("mapID", map.getId());
		
		
		for(int i=0; i < map.getYSize(); i++) {
			
			params.put("coordY", i);
			
			List<Square> squaresX = squareDao.findByQuery(query, params);
			
			mapRowList.add(squaresX);
		}
		
		return mapRowList;
	}

	public Integer getMapXSize() {
		return mapXSize;
	}

	public Integer getMapYSize() {
		return mapYSize;
	}
}
