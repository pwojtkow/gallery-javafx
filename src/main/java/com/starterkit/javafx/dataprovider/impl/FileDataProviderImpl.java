package com.starterkit.javafx.dataprovider.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.starterkit.javafx.dataprovider.FileDataProvider;

public class FileDataProviderImpl implements FileDataProvider {

	@Override
	public List<File> getImages(String path) {
		File folder = new File(path);
		List<File> images = new ArrayList<File>(Arrays.asList(folder.listFiles()));
		return images;
	}


}
