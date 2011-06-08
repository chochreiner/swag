package at.ac.tuwien.swag.webapp.in;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;

public class MapGridView extends GridView<MapPage> {

	private static final long serialVersionUID = 1530735186250385606L;
	
	public MapGridView(String id, IDataProvider<MapPage> dataProvider) {
		super(id, dataProvider );
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void populateEmptyItem(Item<MapPage> item) {
		
		item.add(new Label("cell", "empty"));
		
	}

	@Override
	protected void populateItem(Item<MapPage> item) {
		item.add(new Label("cell", "empty"));
		
	}
}
