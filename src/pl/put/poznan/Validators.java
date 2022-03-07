package pl.put.poznan;

public class Validators {
    static String getMovieTitle(String text) throws EmptyMovieTitleException{
        if (text.isEmpty() || text == null){
            throw new EmptyMovieTitleException();
        }else{
            return text;
        }
    }
    static Integer getMoviePremiereYear(String text) throws EmptyMoviePremiereYearException, MoviePremiereYearFormatException, InvalidValuePremiereYearException {
        try {
            Integer year = Integer.parseInt(text);
            if (year < 0){
                throw new InvalidValuePremiereYearException();
            }

            if (text.isEmpty() || text == null){
                throw new EmptyMoviePremiereYearException();
            }
            if(text.length() > 4 || text.length() < 4){
                throw new MoviePremiereYearFormatException();
            }
            return year;
        }catch(NumberFormatException exception){
            throw new MoviePremiereYearFormatException();
        }

    }
    static String getDiskType(String text) throws EmptyTypeException {
        if (text.isEmpty() || text == null){
            throw new EmptyTypeException();
        }else{
            return text;
        }
    }
    static Integer getDiskCount(String text) throws InvalidValueDiskCountException, EmptyDiskCountException, DiskTypeFormatException {
        try {
            if (text.isEmpty() || text == null){
                throw new EmptyDiskCountException();
            }
            Integer count = Integer.parseInt(text);
            if (count < 0){
                throw new InvalidValueDiskCountException();
            }



            return count;
        }catch(NumberFormatException exception){
            throw new DiskTypeFormatException();
        }
    }
    static String getPhoneNumber(String text) throws PhoneNumberFormatException, EmptyPhoneNumberException {
        try{
            if (text.isEmpty() || text == null){
                throw new EmptyPhoneNumberException();
            }
            Integer number = Integer.parseInt(text);
            if(number < 0){
                throw new PhoneNumberFormatException();
            }
            if (text.length() < 9 || text.length() > 9){
                throw new PhoneNumberFormatException();
            }
            return text;
        }catch(NumberFormatException exception){
            throw new PhoneNumberFormatException();
        }

    }
    static String getName(String text) throws EmptyNameException, NameFormatException {

        if (text.isEmpty() || text == null){
            throw new EmptyNameException();
        }
        if (!text.contains(" ")){
            throw new NameFormatException();
        }
        char[] chars = text.toCharArray();
        for(char c : chars){
            if(Character.isDigit(c)) throw new NameFormatException();
        }
        return text;


    }
    static Disk getDisk(Disk d) throws EmptyDiskException{
        if(d == null){
            throw new EmptyDiskException();
        }
        return d;
    }
}
