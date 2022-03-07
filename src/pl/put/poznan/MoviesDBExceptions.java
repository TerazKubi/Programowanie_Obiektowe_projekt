package pl.put.poznan;

abstract class MoviesDBExceptions extends Exception{

}
class EmptyMovieTitleException extends MoviesDBExceptions{
    @Override
    public String toString() {
        return "Tytuł nie może być pusty";
    }
}
class EmptyMoviePremiereYearException extends MoviesDBExceptions{
    @Override
    public String toString() {
        return "Data premiery nie może być pusta";
    }
}
class MoviePremiereYearFormatException extends MoviesDBExceptions{
    @Override
    public String toString() {
        return "Zły format roku premiery";
    }
}
class InvalidValuePremiereYearException extends MoviesDBExceptions{
    @Override
    public String toString() {
        return "Zła wartość roku premiery";
    }
}
class EmptyTypeException extends MoviesDBExceptions{
    @Override
    public String toString() {
        return "Rodzaj płyty nie może być pusty";
    }
}
class InvalidValueDiskCountException extends MoviesDBExceptions{
    @Override
    public String toString() {
        return "Błędna wartość ilości płyt";
    }
}
class EmptyDiskCountException extends MoviesDBExceptions{
    @Override
    public String toString() {
        return "Ilość płyt nie może być pusta";
    }
}
class DiskTypeFormatException extends MoviesDBExceptions{
    @Override
    public String toString() {
        return "Zły format ilość płyt";
    }
}
class EmptyPhoneNumberException extends MoviesDBExceptions{
    @Override
    public String toString() {
        return "Numer telefonu nie moży być pusty";
    }
}
class PhoneNumberFormatException extends MoviesDBExceptions{
    @Override
    public String toString() {
        return "Zły format w numerze telefonu";
    }
}
class EmptyNameException extends MoviesDBExceptions{
    @Override
    public String toString() {
        return "Imię i nazwisko nie może być puste";
    }
}
class NameFormatException extends MoviesDBExceptions{
    @Override
    public String toString() {
        return "Zły format w imieniu i nazwisku";
    }
}
class EmptyDiskException extends MoviesDBExceptions{
    @Override
    public String toString() {
        return "Wybierz film do wypożyczenia";
    }
}
class CannotSaveToFileException extends MoviesDBExceptions{
    @Override
    public String toString(){
        return "Nie udało się zapisać do pliku";
    }
}

