package obj;

import java.util.Date;

/**
 * The Class Group.
 */
public class Group {

	/*********************************
	 * A very simple class that has four attributes and one toString() method.
	 * The get and set methods are not counted as real 'methods'.
	 */

	/*********************************
	 * Fields
	 ********************************/
	private int id;
	private String name;
	private Date created;
	private Date modified;

	/**
	 * Constructor
	 */
	public Group() {	
	    
		this.id = 0;
		this.name = "";
		this.created = new Date(0);
		this.modified = new Date(0);
	}

	/********************************
	 * Getters/Setters
	 *******************************/

	/**
	 * 
	 * @return the id of the group
	 */
	public int getID() {
		return id;
	}

	/**
	 * Sets the id of the group.
	 * 
	 * @param id
	 *            : the new id
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Gets the name of the group.
	 * 
	 * @return the group name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the group.
	 * 
	 * @param name
	 *            : the new group name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the date the group was created.
	 * 
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * Sets the date the group was created.
	 * 
	 * @param date
	 *            : the new created
	 */
	public void setCreated(Date date) {
		this.created = date;
	}

	/**
	 * Gets the date the group was modified.
	 * 
	 * @return the last modified Date
	 */
	public Date getModified() {
		return modified;
	}

	/**
	 * Sets the date the group was modified.
	 * 
	 * @param date
	 *            : the new modified
	 */
	public void setModified(Date date) {
		this.modified = date;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", created=" + created
				+ ", modified=" + modified + "]";
	}

}
