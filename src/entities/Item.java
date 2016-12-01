package entities;

import java.util.List;

public class Item {
	
	private Integer id;
	private Integer owner;
	private String title;
	private String description;
	private List<Group> group;
	private List<Zone> zone;
	
	public Item(Integer id, Integer owner, String title, String description, List<Group> group, List<Zone> zone) {
		super();
		this.id = id;
		this.owner = owner;
		this.title = title;
		this.description = description;
		this.group = group;
		this.zone = zone;
	}

	public Item(Integer id, Integer owner, String title, String description) {
		super();
		this.id = id;
		this.owner = owner;
		this.title = title;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Group> getGroup() {
		return group;
	}

	public void setGroup(List<Group> group) {
		this.group = group;
	}

	public List<Zone> getZone() {
		return zone;
	}

	public void setZone(List<Zone> zone) {
		this.zone = zone;
	}
	
	
}
