package com.prp.actions;

import com.opensymphony.xwork2.ModelDriven;
import com.prp.models.Setup;

import net.aditri.web.utility.BaseWebAction;

public class SetupAction extends BaseWebAction implements ModelDriven<Setup>{
private static final long serialVersionUID = 1L;
	
	private Setup setup = new Setup();
	public Setup getModel() {
		return this.setup;
	}
	public Setup getSetup() {
		return setup;
	}
	public void setSetup(Setup setup) {
		this.setup = setup;
	}
	
	public String show()
	{	
		return SUCCESS;
	}
}
