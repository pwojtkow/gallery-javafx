package com.starterkit.javafx.dataprovider;

import java.io.File;
import java.util.List;

import com.starterkit.javafx.dataprovider.impl.FileDataProviderImpl;

public interface FileDataProvider {

	FileDataProvider INSTANCE = new FileDataProviderImpl();
	List<File> getImages(String path);
}
