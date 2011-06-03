package swag.domain;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "soldier", uniqueConstraints = {

})
public class Soldier extends AbstractEntity {
	//*** constructors

	public Soldier() {/**/
	}

	//*** getters

	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	public SoldierType getType() {
		return type;
	}

	@Basic(optional = false)
	public double getAttackStrength() {
		return attackStrength;
	}

	@Basic(optional = false)
	public int getAmount() {
		return amount;
	}

	@Basic(optional = false)
	public double getSpeed() {
		return speed;
	}

	@ManyToOne
	public Troop getTroop() {

		return troop;
	}

	//*** setters

	public void setType(SoldierType type) {
		this.type = type;
	}

	public void setAttackStrength(double attackStrength) {
		this.attackStrength = attackStrength;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setTroop(Troop troop) {
		this.troop = troop;
	}

	//*** alternate setters which allow method chaining

	public Soldier type(SoldierType type) {
		this.type = type;
		return this;
	}

	public Soldier attackStrength(double attackStrength) {
		this.attackStrength = attackStrength;
		return this;
	}

	public Soldier amount(int amount) {
		this.amount = amount;
		return this;
	}

	public Soldier speed(double speed) {
		this.speed = speed;
		return this;
	}

	public Soldier troop(Troop troop) {
		this.troop = troop;
		return this;
	}

	//*** misc

	public String toString() {
		return "Soldier[" +

		"amount: " + toString(getAmount()) + ", " + "attackStrength: "
				+ toString(getAttackStrength()) + ", " + "speed: "
				+ toString(getSpeed()) + ", " + "troop: "
				+ toString(getTroop()) + ", " + "type: " + toString(getType())

				+ "]";
	}

	//*** PRIVATE PARTS

	private SoldierType type;

	private double attackStrength;

	private int amount;

	private double speed;

	private Troop troop;

}
