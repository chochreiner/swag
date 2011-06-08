package at.ac.tuwien.swag.webapp.in;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
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
	private ListView<List<Square>> gameMaplistView;

	@Inject
    private MapDAO mapDao;
	
	 @Inject
	 private SquareDAO squareDao;
	
	private GameMapDataProvider gameMapProvider;
	private int startX;
	private int endX;
	private int startY;
	private int endY;
	private WebMarkupContainer gameMapContainer;

    public MapPage(PageParameters parameters) {
        super(parameters);

    
		this.setupNavigationLinks();
        
       gameMapProvider = new GameMapDataProvider(mapDao, squareDao);
       startX = 1;
   		endX = 5;
   		startY =1;
   		endY =5;
        
        ; // = gameMapProvider.getMap();
        
        
        
        IModel<List<List<Square>>> gameMapList =  new LoadableDetachableModel<List<List<Square>>>() {
			private static final long serialVersionUID = 2042471436531963110L;

			protected List<List<Square>> load() {
                return getGameMapList();
            }
        };   
        
     gameMaplistView = new ListView<List<Square>>("gameMap", gameMapList) {

		private static final long serialVersionUID = 7083713778515545799L;

		@Override
		protected void populateItem( ListItem<List<Square>> row) {
			
			List<Square> rowList = row.getModelObject();
			
			ListView<Square> rowListView = new ListView<Square>("row", rowList) {

				private static final long serialVersionUID = 3054181382288233598L;

				@Override
				protected void populateItem(ListItem<Square> squareList) {
					
					Square square = (Square) squareList.getModelObject();
					squareList.add(new Label("square", square.getCoordX() +" / "+ square.getCoordY()));
				}
			};
			row.add(rowListView);
		}
     };
       
        //encapsulate the ListView in a WebMarkupContainer in order for it to update
        gameMapContainer = new WebMarkupContainer("gameMapContainer");
        //generate a markup-id so the contents can be updated through an AJAX call
        gameMapContainer.setOutputMarkupId(true);
       gameMapContainer.add(new AjaxSelfUpdatingTimerBehavior(Duration.minutes(1)));
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
    	
    	add(new AjaxFallbackLink<String>("mapUp") {

    		private static final long serialVersionUID = 2323006706369304418L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				startY --;
				endY --;
				target.addComponent(gameMapContainer);
			}

        });
    	
    	add(new AjaxFallbackLink<String>("mapDown") {

    		private static final long serialVersionUID = 2323006706369304418L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				startY ++;
				endY ++;
				target.addComponent(gameMapContainer);
			}

        });
    	
    	add(new AjaxFallbackLink<String>("mapRight") {

    		private static final long serialVersionUID = 2323006706369304418L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				startX ++;
				endX ++;
				target.addComponent(gameMapContainer);
				
			}

        });
    	
    	add(new AjaxFallbackLink<String>("mapLeft") {

    		private static final long serialVersionUID = 2323006706369304418L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				startX --;
				endX --;
				target.addComponent(gameMapContainer);
			}

        });
    }
}
