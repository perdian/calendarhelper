package de.perdian.apps.calendarhelper.support.storage;

import java.nio.file.Path;

class StorageServiceImpl implements StorageService {

    private Path rootPath = null;

    StorageServiceImpl(Path rootPath) {
        this.setRootPath(rootPath);
    }

    @Override
    public Path getRootPath() {
        return this.rootPath;
    }
    private void setRootPath(Path rootPath) {
        this.rootPath = rootPath;
    }

}
