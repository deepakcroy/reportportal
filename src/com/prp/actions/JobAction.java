package com.prp.actions;

import com.opensymphony.xwork2.ModelDriven;
import com.prp.models.Job;

import net.aditri.web.utility.BaseWebAction;

public class JobAction extends BaseWebAction implements ModelDriven<Job>{
	private static final long serialVersionUID = 1L;
	
	private Job jb = new Job();
	public Job getModel() {
		return this.jb;
	}
	public Job getJob() {
		return jb;
	}
	public void setJob(Job jb) {
		this.jb = jb;
	}
	
	public String show()
	{	
		return SUCCESS;
	}
}
