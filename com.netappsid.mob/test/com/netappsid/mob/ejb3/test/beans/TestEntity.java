package com.netappsid.mob.ejb3.test.beans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TestEntity implements Serializable
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getId()
	{
		return id;
	}
}
