package pl.put.poznan;

import java.io.Serializable;

public class Rent implements Serializable {
    private int ID;
    private String name;
    private String phoneNumber;
    private int movieID;
    private String status;

    public Rent(int ID, String name, String phoneNumber, int movieID, String status){
        this.ID = ID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.movieID = movieID;
        this.status = status;
    }
    public void changeStatus(){
        if (!status.equals("ZWROT")) this.status = "ZWROT";
    }

    public int getID() {
        return this.ID;
    }
    public int getMovieID(){
        return this.movieID;
    }
    public String getName() {return this.name;}
    public Object[] getAsRow(String movieTitle){
        return new Object[] {ID, name, phoneNumber, movieTitle, status};
    }

}
