package at.ac.tuwien.in;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.in.provider.MapDataProvider;

public class Map extends InPage {
    private static final long serialVersionUID = -5939284250869774500L;

    public Map(PageParameters parameters) {
        super(parameters);
        
        IDataProvider<?> dataProvider = new MapDataProvider();
        GridView<?> gridView = new GridView("rows", dataProvider) {
        	
        	@Override
        	protected void populateEmptyItem(Item item) {
        		
        		item.add(new Label("cell", "empty"));
        		
        	}

        	@Override
        	protected void populateItem(Item item) {
        		item.add(new Label("cell", "empty"));
        		
        	}
        
        };

        gridView.setRows(4);
        gridView.setColumns(3);

        add(gridView);
    }

}
