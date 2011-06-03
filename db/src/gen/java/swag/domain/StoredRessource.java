package swag.domain;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "storedRessource", uniqueConstraints = {

})
public class StoredRessource extends AbstractEntity {
	//*** constructors

	public StoredRessource() {/**/
	}

	//*** getters

	@Basic(optional = false)
	public int getAmount() {
		return amount;
	}

	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	public SharedRessourceType getRessourceType() {
		return ressourceType;
	}

	@Basic(optional = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastUpdate() {
		return lastUpdate;
	}

	@Basic(optional = false)
	public double getCurrentRate() {
		return currentRate;
	}

	@ManyToOne
	public MapUser getMapUser() {

		return mapUser;
	}

	//*** setters

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setRessourceType(SharedRessourceType ressourceType) {
		this.ressourceType = ressourceType;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setCurrentRate(double currentRate) {
		this.currentRate = currentRate;
	}

	public void setMapUser(MapUser mapUser) {
		this.mapUser = mapUser;
	}

	//*** alternate setters which allow method chaining

	public StoredRessource amount(int amount) {
		this.amount = amount;
		return this;
	}

	public StoredRessource ressourceType(SharedRessourceType ressourceType) {
		this.ressourceType = ressourceType;
		return this;
	}

	public StoredRessource lastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
		return this;
	}

	public StoredRessource currentRate(double currentRate) {
		this.currentRate = currentRate;
		return this;
	}

	public StoredRessource mapUser(MapUser mapUser) {
		this.mapUser = mapUser;
		return this;
	}

	//*** misc

	public String toString() {
		return "StoredRessource[" +

		"amount: " + toString(getAmount()) + ", " + "currentRate: "
				+ toString(getCurrentRate()) + ", " + "lastUpdate: "
				+ toString(getLastUpdate()) + ", " + "mapUser: "
				+ toString(getMapUser()) + ", " + "ressourceType: "
				+ toString(getRessourceType())

				+ "]";
	}

	//*** PRIVATE PARTS

	private int amount;

	private SharedRessourceType ressourceType;

	private Date lastUpdate;

	private double currentRate;

	private MapUser mapUser;

}
