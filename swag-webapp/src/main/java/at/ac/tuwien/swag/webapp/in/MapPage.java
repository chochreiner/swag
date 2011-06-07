package at.ac.tuwien.swag.webapp.in;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.time.Duration;

import at.ac.tuwien.swag.model.dao.MapDAO;
import at.ac.tuwien.swag.model.dao.SquareDAO;
import at.ac.tuwien.swag.model.domain.Square;
import at.ac.tuwien.swag.webapp.in.provider.GameMapDataProvider;

import com.google.inject.Inject;

public class MapPage extends InPage {
    private static final long serialVersionUID = -5939284250869774500L;
	private ListView gameMaplistView;

	@Inject
    private MapDAO mapDao;
	
	 @Inject
	 private SquareDAO squareDao;
	
	private GameMapDataProvider gameMapProvider;
	private int startX;
	private int endX;
	private int startY;
	private int endY;

    public MapPage(PageParameters parameters) {
        super(parameters);

    
		this.setupNavigationLinks();
        
       gameMapProvider = new GameMapDataProvider(mapDao, squareDao);
       startX = 1;
   		endX = 5;
   		startY =1;
   		endY =5;
        
        ; // = gameMapProvider.getMap();
        
        
        
        IModel gameMapList =  new LoadableDetachableModel() {
            protected Object load() {
                return getGameMapList();
            }
        };   
        
     gameMaplistView = new ListView("gameMap", gameMapList) {

		private static final long serialVersionUID = 7083713778515545799L;

		@Override
		protected void populateItem( ListItem row) {
			
			List rowList = (List) row.getModelObject();
			
			ListView rowListView = new ListView("row", rowList) {

				private static final long serialVersionUID = 3054181382288233598L;

				@Override
				protected void populateItem(ListItem squareList) {
					
					Square square = (Square) squareList.getModelObject();
					squareList.add(new Label("square", square.getCoordX() +" / "+ square.getCoordY()));
				}
			};
			row.add(rowListView);
		}
     };
       
        //encapsulate the ListView in a WebMarkupContainer in order for it to update
        WebMarkupContainer gameMapContainer = new WebMarkupContainer("gameMapContainer");
        //generate a markup-id so the contents can be updated through an AJAX call
        gameMapContainer.setOutputMarkupId(true);
        gameMapContainer.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(1)));
        // add the list view to the container
        gameMapContainer.add(gameMaplistView);
        // finally add the container to the page
        add(gameMapContainer);
    }
    
    private List<List<Square>> getGameMapList() {
    	
    	
    	
    	List<List<Square>> gameMap = gameMapProvider.getPartialMap(startX, startY, endX, endY);
    	return gameMap;
    }
    
    private void setupNavigationLinks() {
    	
    	add(new IndicatingAjaxFallbackLink("mapUp") {

    		private static final long serialVersionUID = 2323006706369304418L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				startY --;
				endY --;
				
			}

        });
    	
    	add(new IndicatingAjaxFallbackLink("mapDown") {

    		private static final long serialVersionUID = 2323006706369304418L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				startY ++;
				endY ++;
			}

        });
    	
    	add(new IndicatingAjaxFallbackLink("mapRight") {

    		private static final long serialVersionUID = 2323006706369304418L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				startX ++;
				endX ++;
				
			}

        });
    	
    	add(new IndicatingAjaxFallbackLink("mapLeft") {

    		private static final long serialVersionUID = 2323006706369304418L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				startX --;
				endX --;
				
			}

        });
    }
}
