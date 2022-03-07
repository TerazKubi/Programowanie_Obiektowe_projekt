package pl.put.poznan;

import java.io.*;
import java.util.ArrayList;

public class Repository {
    final FileDataSource dataSource;

    public Repository(FileDataSource dataSource) {
        this.dataSource = dataSource;
    }

    void saveDisksToFile(ArrayList<DiskEntity> diskEntities) throws CannotSaveToFileException {
        try {
            dataSource.saveDisksToFile(diskEntities);
        } catch (IOException e) {
            throw new CannotSaveToFileException();
        }
    }
    ArrayList<DiskEntity> readDisksFromFile() {
        try {
            return dataSource.readDisksFromFile();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<DiskEntity>();
        }
    }
    void saveIdsToFile(IdsEntity ids) throws CannotSaveToFileException {
        try {
            dataSource.saveIdsToFile(ids);
        } catch (IOException e) {
            throw new CannotSaveToFileException();
        }
    }
    IdsEntity readIdsFromFile() {
        try {
            return dataSource.readIdsFromFile();
        } catch (IOException | ClassNotFoundException e) {
            return new IdsEntity();
        }
    }
    void saveRentsToFile(ArrayList<Rent> rentEntities) throws CannotSaveToFileException {
        try {
            dataSource.saveRentsToFile(rentEntities);
        } catch (IOException e) {
            throw new CannotSaveToFileException();
        }
    }
    ArrayList<Rent> readRentsFromFile() {
        try {
            return dataSource.readRentsFromFile();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<Rent>();
        }
    }
}
