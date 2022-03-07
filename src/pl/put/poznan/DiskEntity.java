package pl.put.poznan;

import java.io.Serializable;

final public class DiskEntity implements Serializable {
    final String title;
    final String type;
    final int premiereYear;
    final int diskCountMax;
    final int diskCountNow;
    final int ID;

    public DiskEntity(String title, String type, int premiereYear, int diskCountMax, int diskCountNow, int ID) {
        this.title = title;
        this.type = type;
        this.premiereYear = premiereYear;
        this.diskCountNow = diskCountNow;
        this.diskCountMax = diskCountMax;
        this.ID = ID;
    }
    public int getID(){
        return this.ID;
    }

    Object[] getAsRow(){
        return new Object[] {ID, title, premiereYear, type, diskCountNow + "/" + diskCountMax};
    }
    public Disk getAsDisk(){
        return new Disk(ID, title, premiereYear, diskCountNow, diskCountMax, type);

    }

}
