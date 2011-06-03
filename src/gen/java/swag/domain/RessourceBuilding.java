package swag.domain;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "ressourceBuilding", uniqueConstraints = {

})
public class RessourceBuilding extends AbstractEntity {
	//*** constructors

	public RessourceBuilding() {/**/
	}

	//*** getters

	@Basic(optional = false)
	public int getLevel() {
		return level;
	}

	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	public RessourceBuildingType getType() {
		return type;
	}

	@ManyToOne
	public Square getSquare() {

		return square;
	}

	//*** setters

	public void setLevel(int level) {
		this.level = level;
	}

	public void setType(RessourceBuildingType type) {
		this.type = type;
	}

	public void setSquare(Square square) {
		this.square = square;
	}

	//*** alternate setters which allow method chaining

	public RessourceBuilding level(int level) {
		this.level = level;
		return this;
	}

	public RessourceBuilding type(RessourceBuildingType type) {
		this.type = type;
		return this;
	}

	public RessourceBuilding square(Square square) {
		this.square = square;
		return this;
	}

	//*** misc

	public String toString() {
		return "RessourceBuilding[" +

		"level: " + toString(getLevel()) + ", " + "square: "
				+ toString(getSquare()) + ", " + "type: " + toString(getType())

				+ "]";
	}

	//*** PRIVATE PARTS

	private int level;

	private RessourceBuildingType type;

	private Square square;

}
