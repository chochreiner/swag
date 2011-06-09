package at.ac.tuwien.swag.webapp.in;

import java.util.HashMap;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.time.Duration;

import at.ac.tuwien.swag.model.dao.MapDAO;
import at.ac.tuwien.swag.model.dao.MapUserDAO;
import at.ac.tuwien.swag.model.dao.SquareDAO;
import at.ac.tuwien.swag.model.domain.MapUser;
import at.ac.tuwien.swag.model.domain.Square;
import at.ac.tuwien.swag.model.dto.SquareDTO;
import at.ac.tuwien.swag.webapp.SwagWebSession;
import at.ac.tuwien.swag.webapp.in.provider.GameMapDataProvider;

import com.google.inject.Inject;

public class MapPage extends InPage {
    private static final long serialVersionUID = -5939284250869774500L;
	private ListView<List<Square>> gameMaplistView;

	@Inject
    private MapDAO mapDao;
	

	@Inject
    private MapUserDAO mapUserDao;
	
	 @Inject
	 private SquareDAO squareDao;
	
	private GameMapDataProvider gameMapProvider;
	private int startX;
	private int endX;
	private int startY;
	private int endY;
	private WebMarkupContainer gameMapContainer;
	private MapUser mapUser;
	private SquareDTO homebase;
	private int mapDim;

    public MapPage(PageParameters parameters) {
        super(parameters);

        //Map dimension
        mapDim = 5;
        
        startX = 1;
   		endX = 5;
   		startY =1;
   		endY =5;
     
    
		this.setupNavigationLinks();
		SwagWebSession session = (SwagWebSession) getSession(); 
     
	gameMapProvider = new GameMapDataProvider(session.getMapname(), mapDao, squareDao);
	mapUser = getMapUser();
	homebase = getHomeBase();
	
	//calculateStartCoord(homebase);
       	
	IModel<List<List<Square>>> gameMapList =  new LoadableDetachableModel<List<List<Square>>>() {
		private static final long serialVersionUID = 2042471436531963110L;

		protected List<List<Square>> load() {
            return gameMapProvider.getPartialMap(startX, startY, endX, endY);
        }
    };   
		
		gameMapContainer = new GameMap("gameMapContainer", gameMapList);
        add(gameMapContainer);
    }
    
    private void calculateStartCoord(SquareDTO homebase) {
    	int hbX = homebase.getCoordX();
    	int hbY = homebase.getCoordY();
    	
    	int diff = ((mapDim-1)/2);
    	
    	startX = hbX-diff;
    	endX = hbX+diff;
    	
    	startY = hbY-diff;
    	endY = hbY+diff;
    }
    
    private SquareDTO getHomeBase() {
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
        if(mapUsers.size() == 0) {
        	throw new NullPointerException("No MapUser found in DB");
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
					startY = 1;endY = mapDim;
				}else {startY --;endY --;}
				target.addComponent(gameMapContainer);
			}
        });
    	
    	add(new AjaxFallbackLink<String>("mapDown") {
    		private static final long serialVersionUID = 2323006706369304418L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if(endY == gameMapProvider.getMapYSize() || endY > gameMapProvider.getMapYSize()) {
					startY = gameMapProvider.getMapYSize() -(mapDim-1);
					endY = gameMapProvider.getMapYSize();
				}else { startY ++; endY ++;}
				target.addComponent(gameMapContainer);
			}
        });
    	
    	add(new AjaxFallbackLink<String>("mapRight") {
    		private static final long serialVersionUID = 2323006706369304418L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if(endX == gameMapProvider.getMapXSize() || endX > gameMapProvider.getMapXSize()) {
					startX = gameMapProvider.getMapXSize() -(mapDim-1);
					endX = gameMapProvider.getMapXSize();
				}else {startX ++; endX ++;}
				target.addComponent(gameMapContainer);
			}
        });
    	
    	add(new AjaxFallbackLink<String>("mapLeft") {
    		private static final long serialVersionUID = 2323006706369304418L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if(startX == 1 || startX < 0) {
					startX = 1; endX = mapDim;	
				}else { startX --; endX --;}
				target.addComponent(gameMapContainer);
			}
        });
    }
}
