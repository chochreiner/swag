package at.ac.tuwien.swag.webapp.in;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.model.domain.Square;
import at.ac.tuwien.swag.model.dto.SquareDTO;
import at.ac.tuwien.swag.webapp.in.provider.MapDataProvider;

public class Map extends InPage {
    private static final long serialVersionUID = -5939284250869774500L;

    public Map(PageParameters parameters) {
        super(parameters);
        
        IDataProvider<SquareDTO> dataProvider = new MapDataProvider();
        GridView<SquareDTO> gridView = new GridView<SquareDTO>("rows", dataProvider) {
        	
			private static final long serialVersionUID = -5912364629079858110L;

			@Override
        	protected void populateEmptyItem(Item<SquareDTO> item) {
				item.add(new Label("cell", "*empty*"));
        	}

			@Override
			protected void populateItem(Item<SquareDTO> item) {
				
				final SquareDTO square = item.getModelObject();
        		item.add(new Label("cell", square.getCoordX()+" "+square.getCoordY()));
			}
        
        };
        
        
        add(gridView);
    }

}
