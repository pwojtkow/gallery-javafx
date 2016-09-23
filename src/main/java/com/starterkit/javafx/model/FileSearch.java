package com.starterkit.javafx.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class FileSearch {

	private StringProperty path = new SimpleStringProperty();
	private ListProperty<File> images = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<>()));
	
	public FileSearch() {
		path.set("C:\\Users\\Public\\Pictures\\Sample Pictures");
	}
	
	public FileSearch(String newPath) {
		path.set(newPath);
	}
	
	public String getPath() {
		return path.get();
	}
	
	public void setPath(String value) {
		path.set(value);
	}
	
	public List<File> getImages() {
		return images.get();
	}
	
	public void setImages(List<File> value) {
		images.setAll(value);
	}
	
}

