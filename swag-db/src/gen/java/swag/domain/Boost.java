package swag.domain;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "boost", uniqueConstraints = {

})
public class Boost extends AbstractEntity {
	//*** constructors

	public Boost() {/**/
	}

	//*** getters

	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	public RessourceType getType() {
		return type;
	}

	@ManyToOne
	public Square getSquare() {

		return square;
	}

	//*** setters

	public void setType(RessourceType type) {
		this.type = type;
	}

	public void setSquare(Square square) {
		this.square = square;
	}

	//*** alternate setters which allow method chaining

	public Boost type(RessourceType type) {
		this.type = type;
		return this;
	}

	public Boost square(Square square) {
		this.square = square;
		return this;
	}

	//*** misc

	public String toString() {
		return "Boost[" +

		"square: " + toString(getSquare()) + ", " + "type: "
				+ toString(getType())

				+ "]";
	}

	//*** PRIVATE PARTS

	private RessourceType type;

	private Square square;

}
