package at.ac.tuwien.swag.webapp.in;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class Map extends InPage {
    private static final long serialVersionUID = -5939284250869774500L;
	private ListView listView;

    public Map(PageParameters parameters) {
        super(parameters);
        
       List<List<String>> gameMap = new ArrayList<List<String>>();
       
       List<String> row = new ArrayList<String>();
       
       row.add("1");
       row.add("2");
       row.add("3");
       row.add("4");
       row.add("5");
       
       gameMap.add(row);
       gameMap.add(row);
       gameMap.add(row);
       gameMap.add(row);
       gameMap.add(row);
        
     listView = new ListView("gameMap", gameMap) {

		private static final long serialVersionUID = 7083713778515545799L;

		@Override
		protected void populateItem( ListItem row) {
			
			List rowList = (List) row.getModelObject();
			
			ListView rowListView = new ListView("row", rowList) {

				private static final long serialVersionUID = 3054181382288233598L;

				@Override
				protected void populateItem(ListItem squareList) {
					
					String square = (String) squareList.getModelObject();
					squareList.add(new Label("square", square));
				}
			};
			row.add(rowListView);
		}
     };
        add(listView);
    }

}
