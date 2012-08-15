package com.findwise.hydra;

import java.util.Date;
import java.util.Map;

import com.findwise.hydra.common.SerializationUtils;

public class Stage {
	public enum Mode { ACTIVE, INACTIVE, DEBUG }
	private String name;
	private DatabaseFile databaseFile;
	private Mode mode;
	private Map<String, Object> properties;
	private Date propertiesModifiedDate;
	private boolean changedProperties;
	
	/**
	 * Creates a stage that is ACTIVE by default.
	 */
	public Stage(String name, DatabaseFile databaseFile) {
		this(name, databaseFile, Mode.ACTIVE);
	}
	
	public Stage(String name, DatabaseFile databaseFile, Mode mode) {
		this.name = name;
		this.databaseFile = databaseFile;
		this.mode = mode;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public DatabaseFile getDatabaseFile() {
		return databaseFile;
	}
	
	public void setDatabaseFile(DatabaseFile databaseFile) {
		this.databaseFile = databaseFile;
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public Map<String, Object> getProperties() {
		return properties;
	}
	
	public Date getPropertiesModifiedDate() {
		return new Date(propertiesModifiedDate.getTime());
	}
	
	public void setPropertiesModifiedDate(Date propertiesModifiedDate) {
		this.propertiesModifiedDate = new Date(propertiesModifiedDate.getTime());
	}
	
	public void setProperties(Map<String, Object> properties) {
		//Might give false positives, but that should be ok
		if(!SerializationUtils.toJson(properties).equals(SerializationUtils.toJson(this.properties))) {
			changedProperties = true;
		}
		this.properties = properties;
	}
	
	public boolean isPropertiesChanged() {
		return changedProperties;
	}
	
	@Override
	public String toString() {
		return SerializationUtils.toJson(this);
	}
	
	public boolean isEqual(Stage s) {
		if(!s.getName().equals(name) || !s.getPropertiesModifiedDate().equals(getPropertiesModifiedDate())) {
			return false;
		}
		return getDatabaseFile().isEqual(s.getDatabaseFile());
	}
}
