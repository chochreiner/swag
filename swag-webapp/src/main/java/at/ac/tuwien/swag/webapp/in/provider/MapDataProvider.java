package at.ac.tuwien.swag.webapp.in.provider;

import java.util.Iterator;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

import at.ac.tuwien.swag.model.domain.Square;

public class MapDataProvider implements IDataProvider<Square>{
	
	private static final long serialVersionUID = -3668383425408535701L;

	@Override
	public void detach() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterator<Square> iterator(int first, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public IModel<Square> model(Square object) {
		
		return new DetachableSquareModel(object);
	}
}
