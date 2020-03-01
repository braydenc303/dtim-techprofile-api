package org.haxwell.dtim.techprofile.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Path {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	///
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
    @ManyToMany
	@JoinTable(
		name="path_milestone_map"
		, joinColumns={
			@JoinColumn(name="pathId")
			}
		, inverseJoinColumns={
			@JoinColumn(name="milestoneId")
			}
		)
	private Set<Milestone> milestones;
    
    public Set<Milestone> getMilestones() {
    	return milestones;
    }
    
    public void setMilestones(Set<Milestone> milestones) {
    	this.milestones = milestones;
    }
    
	public Path(String name) {
		this.name = name;
	}
	
	public Path() {
		
	}
}
