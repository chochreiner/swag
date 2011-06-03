package swag.domain;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "mapUser", uniqueConstraints = {

})
public class MapUser extends AbstractEntity {
	//*** constructors

	public MapUser() {/**/
	}

	//*** getters

	@ManyToOne
	public Map getMap() {

		return map;
	}

	@ManyToOne
	public User getUser() {

		return user;
	}

	@OneToMany
	public List<StoredRessource> getStoredResources() {

		if (storedResources == null)
			storedResources = new ArrayList<StoredRessource>();

		return storedResources;
	}

	@OneToMany
	public List<Square> getSquares() {

		if (squares == null)
			squares = new ArrayList<Square>();

		return squares;
	}

	//*** setters

	public void setMap(Map map) {
		this.map = map;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setStoredResources(List<StoredRessource> storedResources) {
		this.storedResources = storedResources;
	}

	public void setSquares(List<Square> squares) {
		this.squares = squares;
	}

	//*** alternate setters which allow method chaining

	public MapUser map(Map map) {
		this.map = map;
		return this;
	}

	public MapUser user(User user) {
		this.user = user;
		return this;
	}

	public MapUser storedResources(List<StoredRessource> storedResources) {
		this.storedResources = storedResources;
		return this;
	}

	public MapUser squares(List<Square> squares) {
		this.squares = squares;
		return this;
	}

	//*** misc

	public String toString() {
		return "MapUser[" +

		"map: " + toString(getMap()) + ", " + "squares: "
				+ toString(getSquares()) + ", " + "storedResources: "
				+ toString(getStoredResources()) + ", " + "user: "
				+ toString(getUser())

				+ "]";
	}

	//*** PRIVATE PARTS

	private Map map;

	private User user;

	private List<StoredRessource> storedResources;

	private List<Square> squares;

}
