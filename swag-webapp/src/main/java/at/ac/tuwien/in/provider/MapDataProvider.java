package at.ac.tuwien.in.provider;

import java.util.Iterator;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

public class MapDataProvider implements IDataProvider<Cell>{
	
	private static final long serialVersionUID = -3668383425408535701L;

	@Override
	public void detach() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterator iterator(int first, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IModel<Cell> model(Cell object) {
		// TODO Auto-generated method stub
		return null;
	}
}
