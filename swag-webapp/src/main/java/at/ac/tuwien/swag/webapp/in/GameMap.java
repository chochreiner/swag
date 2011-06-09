package at.ac.tuwien.swag.webapp.in;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;

import at.ac.tuwien.swag.model.domain.Square;

public class GameMap extends WebMarkupContainer {

	
	private static final long serialVersionUID = -2473064801663338918L;
	private ListView<List<Square>> gameMaplistView;
	private IModel<List<List<Square>>> gameMapList;
	
	
	public GameMap(String id, IModel<List<List<Square>>> gameMapList) {
		super(id);
		
		this.gameMapList = gameMapList;
		
		this.setOutputMarkupId(true);
		
		this.setupGameMapView();
		this.add(gameMaplistView);
	}

	private void setupGameMapView() {
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
						squareList.add(new Label("square", "X: "+square.getCoordX() +" / Y: "+ square.getCoordY()));
					}
				};
				row.add(rowListView);
			}
	     };
	}
}
