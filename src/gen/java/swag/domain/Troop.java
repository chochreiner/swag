package swag.domain;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "troop", uniqueConstraints = {

})
public class Troop extends AbstractEntity {
	//*** constructors

	public Troop() {/**/
	}

	//*** getters

	@Basic(optional = false)
	public int getNumber() {
		return number;
	}

	@ManyToOne
	public Square getSquare() {

		return square;
	}

	@OneToMany(mappedBy = "troop")
	public List<Soldier> getSoldiers() {

		if (soldiers == null)
			soldiers = new ArrayList<Soldier>();

		return soldiers;
	}

	//*** setters

	public void setNumber(int number) {
		this.number = number;
	}

	public void setSquare(Square square) {
		this.square = square;
	}

	public void setSoldiers(List<Soldier> soldiers) {
		this.soldiers = soldiers;
	}

	//*** alternate setters which allow method chaining

	public Troop number(int number) {
		this.number = number;
		return this;
	}

	public Troop square(Square square) {
		this.square = square;
		return this;
	}

	public Troop soldiers(List<Soldier> soldiers) {
		this.soldiers = soldiers;
		return this;
	}

	//*** misc

	public String toString() {
		return "Troop[" +

		"number: " + toString(getNumber()) + ", " + "soldiers: "
				+ toString(getSoldiers()) + ", " + "square: "
				+ toString(getSquare())

				+ "]";
	}

	//*** PRIVATE PARTS

	private int number;

	private Square square;

	private List<Soldier> soldiers;

}
