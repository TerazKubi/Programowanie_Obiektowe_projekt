package pl.put.poznan;

public class Main {
    public static void main(String[] args){
        FileDataSource fileDataSource = new FileDataSource(args[0], args[1], args[2]);
        Repository repository = new Repository(fileDataSource);
        MoviesDB moviesdb = new MoviesDB(repository);


    }
}
