package at.ac.tuwien.swag.webapp.in;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.google.inject.Inject;

import at.ac.tuwien.swag.model.dao.MapDAO;
import at.ac.tuwien.swag.model.dao.SquareDAO;
import at.ac.tuwien.swag.model.domain.Map;
import at.ac.tuwien.swag.model.domain.Square;
import at.ac.tuwien.swag.webapp.in.provider.GameMapDataProvider;

public class MapPage extends InPage {
    private static final long serialVersionUID = -5939284250869774500L;
	private ListView listView;

	@Inject
    private MapDAO mapDao;
	
	 @Inject
	 private SquareDAO squareDao;
	 
	 @Inject
	private EntityManager em;
	
    public MapPage(PageParameters parameters) {
        super(parameters);

        System.out.println("select");
		
        
        GameMapDataProvider gameMapProvider = new GameMapDataProvider(mapDao, squareDao, em);
    
        
        List<List<Square>> gameMap = gameMapProvider.getMap();
        
     listView = new ListView("gameMap", gameMap) {

		private static final long serialVersionUID = 7083713778515545799L;

		@Override
		protected void populateItem( ListItem row) {
			
			List rowList = (List) row.getModelObject();
			
			ListView rowListView = new ListView("row", rowList) {

				private static final long serialVersionUID = 3054181382288233598L;

				@Override
				protected void populateItem(ListItem squareList) {
					
					Square square = (Square) squareList.getModelObject();
					squareList.add(new Label("square", square.getCoordX() +""+ square.getCoordY()));
				}
			};
			row.add(rowListView);
		}
     };
        add(listView);
    }

}
