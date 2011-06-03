package at.ac.tuwien.in;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;

public class MapGridView extends GridView{

	private static final long serialVersionUID = 1530735186250385606L;
	
	public MapGridView(String id, IDataProvider dataProvider) {
		super(id, dataProvider);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void populateEmptyItem(Item item) {
		
		item.add(new Label("cell", "empty"));
		
	}

	@Override
	protected void populateItem(Item item) {
		item.add(new Label("cell", "empty"));
		
	}
}
