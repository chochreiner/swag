package at.ac.tuwien.swag.model.dto;

import java.util.Iterator;

public abstract class AbstractDTO {

	public long getId()          { return id;    }
	public void setId( long id ) { this.id = id; }
	
	public String toString() {
		return "<" + getClass().getSimpleName() + ":" + id + ">";
	}
	
	//*** PRIVATE PARTS 
	
	private long id;

	//*** TO STRING HELPERS FOR SUBCLASSES
	
	protected static String toString( Object o ) {
		if ( o == null )
			return null;
		if ( o instanceof AbstractDTO ) {
			AbstractDTO e = (AbstractDTO) o;
			return e.getClass().getSimpleName() + "[id: " + e.id + "]";
		}
		return o.toString();
	}
	protected static <E extends AbstractDTO> String toString( Iterable<E> es ) {
		if ( es == null ) return null;
		
		String      out = "[";
		Iterator<E> it  = es.iterator();
		
		if ( !it.hasNext() ) return "[]";
		
		out += it.next();
		
		while ( it.hasNext() ) {
			out += ", " + it.next();
		}
		
		return out + "]";
	}
	
}
