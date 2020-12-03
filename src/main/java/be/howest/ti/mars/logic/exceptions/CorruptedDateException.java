package be.howest.ti.mars.logic.exceptions;

public class CorruptedDateException extends RuntimeException{
    public CorruptedDateException(String msg){
        super(msg);
    }
}
