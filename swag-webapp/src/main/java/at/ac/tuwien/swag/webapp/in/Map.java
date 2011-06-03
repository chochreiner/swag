package at.ac.tuwien.swag.webapp.in;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.model.domain.Square;
import at.ac.tuwien.swag.webapp.in.provider.MapDataProvider;

public class Map extends InPage {
    private static final long serialVersionUID = -5939284250869774500L;

    public Map(PageParameters parameters) {
        super(parameters);
        
        IDataProvider<Square> dataProvider = new MapDataProvider();
        GridView<Square> gridView = new GridView<Square>("rows", dataProvider) {
        	
			private static final long serialVersionUID = -5912364629079858110L;

			@Override
        	protected void populateEmptyItem(Item<Square> item) {
        		
        		item.add(new Label("cell", "empty"));
        		
        	}

        	@Override
        	protected void populateItem(Item<Square> item) {
        		item.add(new Label("cell", "empty"));
        		
        	}
        
        };

        gridView.setRows(4);
        gridView.setColumns(3);

        add(gridView);
    }

}
