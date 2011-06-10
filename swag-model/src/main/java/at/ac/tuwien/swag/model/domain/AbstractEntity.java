package at.ac.tuwien.swag.model.domain;

import java.io.Serializable;
import java.util.Iterator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
	private static final long serialVersionUID = -7007590793374147375L;

	@Id
	@GeneratedValue
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
		if ( o instanceof AbstractEntity ) {
			AbstractEntity e = (AbstractEntity) o;
			return e.getClass().getSimpleName() + "[id: " + e.id + "]";
		}
		return o.toString();
	}
	protected static <E extends AbstractEntity> String toString( Iterable<E> es ) {
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
