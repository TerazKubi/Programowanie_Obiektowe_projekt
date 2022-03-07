package pl.put.poznan;

import java.io.Serializable;

final public class IdsEntity implements Serializable {
    private final int movieIDCount;
    private final int rentIDCount;

    public IdsEntity(int movieIDCount, int rentIDCount) {
        this.movieIDCount = movieIDCount;
        this.rentIDCount = rentIDCount;
    }
    public IdsEntity() {
        this.movieIDCount = 0;
        this.rentIDCount = 0;
    }
    public int getMovieIDCount(){
        return movieIDCount;
    }
    public int getRentIDCount() {
        return rentIDCount;
    }
}
