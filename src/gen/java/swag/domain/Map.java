package swag.domain;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "map", uniqueConstraints = {

@UniqueConstraint(columnNames = {"name"})

})
public class Map extends AbstractEntity {
	//*** constructors

	public Map() {/**/
	}

	public Map(List<MapUser> users, List<Square> squares) {

		this.users = users;

		this.squares = squares;

	}

	//*** getters

	@Basic(optional = false)
	public int getMaxNumUsers() {
		return maxNumUsers;
	}

	@Basic(optional = false)
	public int getXSize() {
		return xSize;
	}

	@Basic(optional = false)
	public int getYSize() {
		return ySize;
	}

	@Basic(optional = false)
	public String getName() {
		return name;
	}

	@OneToMany(mappedBy = "map")
	public List<MapUser> getUsers() {

		if (users == null)
			users = new ArrayList<MapUser>();

		return users;
	}

	@OneToMany(mappedBy = "map")
	public List<Square> getSquares() {

		if (squares == null)
			squares = new ArrayList<Square>();

		return squares;
	}

	//*** setters

	public void setMaxNumUsers(int maxNumUsers) {
		this.maxNumUsers = maxNumUsers;
	}

	public void setXSize(int xSize) {
		this.xSize = xSize;
	}

	public void setYSize(int ySize) {
		this.ySize = ySize;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUsers(List<MapUser> users) {
		this.users = users;
	}

	public void setSquares(List<Square> squares) {
		this.squares = squares;
	}

	//*** alternate setters which allow method chaining

	public Map maxNumUsers(int maxNumUsers) {
		this.maxNumUsers = maxNumUsers;
		return this;
	}

	public Map xSize(int xSize) {
		this.xSize = xSize;
		return this;
	}

	public Map ySize(int ySize) {
		this.ySize = ySize;
		return this;
	}

	public Map name(String name) {
		this.name = name;
		return this;
	}

	public Map users(List<MapUser> users) {
		this.users = users;
		return this;
	}

	public Map squares(List<Square> squares) {
		this.squares = squares;
		return this;
	}

	//*** misc

	public String toString() {
		return "Map[" +

		"maxNumUsers: " + toString(getMaxNumUsers()) + ", " + "name: "
				+ toString(getName()) + ", " + "squares: "
				+ toString(getSquares()) + ", " + "users: "
				+ toString(getUsers()) + ", " + "xSize: "
				+ toString(getXSize()) + ", " + "ySize: "
				+ toString(getYSize())

				+ "]";
	}

	//*** PRIVATE PARTS

	private int maxNumUsers;

	private int xSize;

	private int ySize;

	private String name;

	private List<MapUser> users;

	private List<Square> squares;

}
