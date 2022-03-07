package pl.put.poznan;

import java.io.Serializable;

public class Disk extends Movie {
    private String diskType;
    private int diskCountMax;
    private int diskCountNow;
    private int ID;


    public Disk(int ID, String title, int premiereYear, int diskCountNow, int diskCountMax, String diskType) {
        super(title, premiereYear);
        this.ID = ID;
        this.diskCountMax = diskCountMax;
        this.diskCountNow = diskCountNow;
        this.diskType = diskType;
    }
    public void rentDisk(){
        if (this.diskCountNow > 0)
            this.diskCountNow -= 1;
    }
    public void returnDisk(){
        if (this.diskCountNow < this.diskCountMax)
            this.diskCountNow += 1;
    }

    public String getTitle(){
        return this.title;
    }
    public int getDiskCountMax(){
        return this.diskCountMax;
    }
    public int getDiskCountNow(){
        return this.diskCountNow;
    }
    public int getID(){return this.ID;}

    public DiskEntity getAsEntity(){
        return new DiskEntity(this.title, this.diskType, this.premiereYear, this.diskCountMax, this.diskCountNow, this.ID);
    }

    @Override
    public String toString(){
        return this.title + " " + this.diskCountNow + "/" + this.diskCountMax;
    }
}
