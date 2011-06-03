package swag.domain;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "square", uniqueConstraints = {

})
public class Square extends AbstractEntity {
	//*** constructors

	public Square() {/**/
	}

	public Square(List<Boost> boosts,
			List<RessourceBuilding> resourceBuildings, List<Troop> troops,
			List<BaseBuilding> baseBuildings) {

		this.boosts = boosts;

		this.resourceBuildings = resourceBuildings;

		this.troops = troops;

		this.baseBuildings = baseBuildings;

	}

	//*** getters

	@Basic(optional = false)
	public int getCoordX() {
		return coordX;
	}

	@Basic(optional = false)
	public int getCoordY() {
		return coordY;
	}

	@Basic(optional = false)
	public boolean getIsHomeBase() {
		return isHomeBase;
	}

	@ManyToOne
	public Map getMap() {

		return map;
	}

	@OneToMany(mappedBy = "square")
	public List<Boost> getBoosts() {

		if (boosts == null)
			boosts = new ArrayList<Boost>();

		return boosts;
	}

	@ManyToOne
	public MapUser getUser() {

		return user;
	}

	@OneToMany(mappedBy = "square")
	public List<RessourceBuilding> getResourceBuildings() {

		if (resourceBuildings == null)
			resourceBuildings = new ArrayList<RessourceBuilding>();

		return resourceBuildings;
	}

	@OneToMany(mappedBy = "square")
	public List<Troop> getTroops() {

		if (troops == null)
			troops = new ArrayList<Troop>();

		return troops;
	}

	@OneToMany(mappedBy = "square")
	public List<BaseBuilding> getBaseBuildings() {

		if (baseBuildings == null)
			baseBuildings = new ArrayList<BaseBuilding>();

		return baseBuildings;
	}

	//*** setters

	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	public void setIsHomeBase(boolean isHomeBase) {
		this.isHomeBase = isHomeBase;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public void setBoosts(List<Boost> boosts) {
		this.boosts = boosts;
	}

	public void setUser(MapUser user) {
		this.user = user;
	}

	public void setResourceBuildings(List<RessourceBuilding> resourceBuildings) {
		this.resourceBuildings = resourceBuildings;
	}

	public void setTroops(List<Troop> troops) {
		this.troops = troops;
	}

	public void setBaseBuildings(List<BaseBuilding> baseBuildings) {
		this.baseBuildings = baseBuildings;
	}

	//*** alternate setters which allow method chaining

	public Square coordX(int coordX) {
		this.coordX = coordX;
		return this;
	}

	public Square coordY(int coordY) {
		this.coordY = coordY;
		return this;
	}

	public Square isHomeBase(boolean isHomeBase) {
		this.isHomeBase = isHomeBase;
		return this;
	}

	public Square map(Map map) {
		this.map = map;
		return this;
	}

	public Square boosts(List<Boost> boosts) {
		this.boosts = boosts;
		return this;
	}

	public Square user(MapUser user) {
		this.user = user;
		return this;
	}

	public Square resourceBuildings(List<RessourceBuilding> resourceBuildings) {
		this.resourceBuildings = resourceBuildings;
		return this;
	}

	public Square troops(List<Troop> troops) {
		this.troops = troops;
		return this;
	}

	public Square baseBuildings(List<BaseBuilding> baseBuildings) {
		this.baseBuildings = baseBuildings;
		return this;
	}

	//*** misc

	public String toString() {
		return "Square[" +

		"baseBuildings: " + toString(getBaseBuildings()) + ", " + "boosts: "
				+ toString(getBoosts()) + ", " + "coordX: "
				+ toString(getCoordX()) + ", " + "coordY: "
				+ toString(getCoordY()) + ", " + "isHomeBase: "
				+ toString(getIsHomeBase()) + ", " + "map: "
				+ toString(getMap()) + ", " + "resourceBuildings: "
				+ toString(getResourceBuildings()) + ", " + "troops: "
				+ toString(getTroops()) + ", " + "user: " + toString(getUser())

				+ "]";
	}

	//*** PRIVATE PARTS

	private int coordX;

	private int coordY;

	private boolean isHomeBase;

	private Map map;

	private List<Boost> boosts;

	private MapUser user;

	private List<RessourceBuilding> resourceBuildings;

	private List<Troop> troops;

	private List<BaseBuilding> baseBuildings;

}
