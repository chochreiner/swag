package swag.domain;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "baseBuilding", uniqueConstraints = {

})
public class BaseBuilding extends AbstractEntity {
	//*** constructors

	public BaseBuilding() {/**/
	}

	//*** getters

	@Basic(optional = false)
	public int getLevel() {
		return level;
	}

	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	public BaseBuildingType getType() {
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

	public void setType(BaseBuildingType type) {
		this.type = type;
	}

	public void setSquare(Square square) {
		this.square = square;
	}

	//*** alternate setters which allow method chaining

	public BaseBuilding level(int level) {
		this.level = level;
		return this;
	}

	public BaseBuilding type(BaseBuildingType type) {
		this.type = type;
		return this;
	}

	public BaseBuilding square(Square square) {
		this.square = square;
		return this;
	}

	//*** misc

	public String toString() {
		return "BaseBuilding[" +

		"level: " + toString(getLevel()) + ", " + "square: "
				+ toString(getSquare()) + ", " + "type: " + toString(getType())

				+ "]";
	}

	//*** PRIVATE PARTS

	private int level;

	private BaseBuildingType type;

	private Square square;

}
