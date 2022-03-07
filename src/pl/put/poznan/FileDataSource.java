package pl.put.poznan;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.*;
import java.util.ArrayList;

public class FileDataSource {
    final String moviePath;
    final String rentPath;
    final String idsPath;

    public FileDataSource(String moviePath, String rentPath, String idsPath) {
        this.moviePath = moviePath;
        this.rentPath = rentPath;
        this.idsPath = idsPath;
    }

    void saveDisksToFile(ArrayList<DiskEntity> diskEntities) throws IOException {
        ObjectOutputStream writer = null;
        try {
            writer = new ObjectOutputStream(new FileOutputStream(moviePath));
            writer.writeObject(diskEntities);
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }
    ArrayList<DiskEntity> readDisksFromFile() throws IOException, ClassNotFoundException {
        ObjectInputStream reader = null;
        try {
            reader = new ObjectInputStream(new FileInputStream(moviePath));
            ArrayList<DiskEntity> entities = (ArrayList<DiskEntity>) reader.readObject();
            return entities;
        } finally {
            if(reader != null){
                reader.close();
            }
        }
    }
    void saveIdsToFile(IdsEntity ids) throws IOException {
        ObjectOutputStream writer = null;
        try {
            writer = new ObjectOutputStream(new FileOutputStream(idsPath));
            writer.writeObject(ids);
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }
    IdsEntity readIdsFromFile() throws IOException, ClassNotFoundException {
        ObjectInputStream reader = null;
        try {
            reader = new ObjectInputStream(new FileInputStream(idsPath));
            IdsEntity ids = (IdsEntity) reader.readObject();
            return ids;
        } finally {
            if(reader != null){
                reader.close();
            }
        }
    }
    void saveRentsToFile(ArrayList<Rent> rentEntities) throws IOException {
        ObjectOutputStream writer = null;
        try {
            writer = new ObjectOutputStream(new FileOutputStream(rentPath));
            writer.writeObject(rentEntities);
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }
    ArrayList<Rent> readRentsFromFile() throws IOException, ClassNotFoundException {
        ObjectInputStream reader = null;
        try {
            reader = new ObjectInputStream(new FileInputStream(rentPath));
            ArrayList<Rent> entities = (ArrayList<Rent>) reader.readObject();
            return entities;
        } finally {
            if(reader != null){
                reader.close();
            }
        }
    }
}
