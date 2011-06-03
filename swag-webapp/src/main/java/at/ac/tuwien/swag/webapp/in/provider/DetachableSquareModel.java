package at.ac.tuwien.swag.webapp.in.provider;

import org.apache.wicket.model.LoadableDetachableModel;

import at.ac.tuwien.swag.model.domain.Square;

public class DetachableSquareModel extends LoadableDetachableModel<Square> {

	private static final long serialVersionUID = 7969439693141784767L;
	
	private long id;
	
	public DetachableSquareModel(Square square) {
	
		square.getId();
	}
	
	public DetachableSquareModel(long id)
    {
        if (id == 0)
        {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }

	
	@Override
	protected Square load() {
		// TODO Auto-generated method stub
		
		// returns db object for ID
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetachableSquareModel other = (DetachableSquareModel) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
}
