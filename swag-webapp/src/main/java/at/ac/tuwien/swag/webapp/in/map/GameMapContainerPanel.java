package at.ac.tuwien.swag.webapp.in.map;

import java.util.HashMap;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.google.inject.Inject;

import at.ac.tuwien.swag.model.dao.MapDAO;
import at.ac.tuwien.swag.model.dao.MapUserDAO;
import at.ac.tuwien.swag.model.dao.SquareDAO;
import at.ac.tuwien.swag.model.domain.MapUser;
import at.ac.tuwien.swag.model.domain.Square;
import at.ac.tuwien.swag.model.dto.SquareDTO;
import at.ac.tuwien.swag.webapp.SwagWebSession;
import at.ac.tuwien.swag.webapp.in.provider.GameMapDataProvider;

public class GameMapContainerPanel extends Panel{
	private static final long serialVersionUID = -7446754779314974316L;

	private GameMapDataProvider gameMapProvider;
	private int startX;
	private int endX;
	private int startY;
	private int endY;
	private WebMarkupContainer gameMapView;
	private MapUser mapUser;
	
	private static int MAP_DIMENSION  = 5;
	
	@Inject
    private MapDAO mapDao;

	@Inject
    private MapUserDAO mapUserDao;
	
	 @Inject
	 private SquareDAO squareDao;
	
	public GameMapContainerPanel(String id,String mapName) {
		super(id);
		
		startX = 1;
   		endX = 5;
   		startY =1;
   		endY =5;
   		
   		gameMapProvider = new GameMapDataProvider(mapName, mapDao, squareDao);
   		this.mapUser = this.getMapUser();
   		//homebase = getHomeBase();
   		
   		// MapModel to load for the gameMapContainer
		IModel<List<List<Square>>> gameMapList =  new LoadableDetachableModel<List<List<Square>>>() {
			private static final long serialVersionUID = 2042471436531963110L;

			protected List<List<Square>> load() {
				return gameMapProvider.getPartialMap(startX, startY, endX, endY);
			}
		};   
		gameMapView = new GameMap("gameMapView", mapUser, gameMapList);
        add(gameMapView);
        
        this.setupNavigationLinks();
	}
	
	 private void calculateStartCoord(SquareDTO homebase) {
	    	int hbX = homebase.getCoordX();
	    	int hbY = homebase.getCoordY();
	    	
	    	int diff = ((MAP_DIMENSION-1)/2);
	    	
	    	startX = hbX-diff;
	    	endX = hbX+diff;
	    	
	    	startY = hbY-diff;
	    	endY = hbY+diff;
	    }
	    
	    private SquareDTO getHomeBase(MapUser mapUser) {
	    	SwagWebSession session = (SwagWebSession) getSession(); 
	    	SquareDTO homebase = session.getHomebase();
	    	if(homebase == null) {
	    		homebase = getHomeBaseFromSquareList(mapUser.getSquares());
	    		session.setHomebase(homebase);
	    	}
	    	return homebase;
	    }

	    private SquareDTO getHomeBaseFromSquareList(List<Square> squares) {
	    	for(Square sq :squares) {
	    		
	    		if(sq.getIsHomeBase()) {
	    			return new SquareDTO(sq.getCoordY(), sq.getCoordX(), sq.getIsHomeBase(), null, null, null, null, null, null);
	    		};
	    	}
			return null;
	    }

	    
	    /**
	     * 
	     * @return
	     */
	    private MapUser getMapUser() {
	    	SwagWebSession session = (SwagWebSession) getSession(); 
	    	
	    	//Retrieve all maps for the user
	        String query = "SELECT mu FROM MapUser mu JOIN mu.user u WHERE u.username=:username AND mu.map.name=:mapname";
	        
	        // define parameters
	        HashMap<String, String> params = new HashMap<String, String>();
	        params.put("username", session.getUsername());
	        params.put("mapname", session.getMapname());
	  
	        List<MapUser> mapUsers = mapUserDao.findByQuery(query, params);
	        if(mapUsers.isEmpty()) {
	        	throw new NoResultException("No MapUser found in DB");
	        }
	        return mapUsers.get(0);
	    }
	    
////////////////////////////////NAV ///////////////////////////////////////////////////////////////////////////////   
    /**
     * 
     */
    private void setupNavigationLinks() {	
    	add(new AjaxFallbackLink<String>("mapUp") {
    		private static final long serialVersionUID = 2323006706369304418L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if(startY == 1 || startY < 0) {
					startY = 1;endY = MAP_DIMENSION;
				}else {startY --;endY --;}
				
				//target.addChildren(gameMapContainer, Label.class);
				target.addComponent(gameMapView);
			}
        });
    	
    	add(new AjaxFallbackLink<String>("mapDown") {
    		private static final long serialVersionUID = 2323006706369304418L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if(endY == gameMapProvider.getMapYSize() || endY > gameMapProvider.getMapYSize()) {
					startY = gameMapProvider.getMapYSize() -(MAP_DIMENSION-1);
					endY = gameMapProvider.getMapYSize();
				}else { startY ++; endY ++;}
				
				target.addComponent(gameMapView);
			}
        });
    	
    	add(new AjaxFallbackLink<String>("mapRight") {
    		private static final long serialVersionUID = 2323006706369304418L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if(endX == gameMapProvider.getMapXSize() || endX > gameMapProvider.getMapXSize()) {
					startX = gameMapProvider.getMapXSize() -(MAP_DIMENSION-1);
					endX = gameMapProvider.getMapXSize();
				}else {startX ++; endX ++;}
				target.addComponent(gameMapView);
			}
        });
    	
    	add(new AjaxFallbackLink<String>("mapLeft") {
    		private static final long serialVersionUID = 2323006706369304418L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if(startX == 1 || startX < 0) {
					startX = 1; endX = MAP_DIMENSION;	
				}else { startX --; endX --;}
				target.addComponent(gameMapView);
			}
        });
    }
}
