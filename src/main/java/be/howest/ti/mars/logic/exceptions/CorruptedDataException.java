package be.howest.ti.mars.logic.exceptions;

public class CorruptedDataException extends RuntimeException{
    public CorruptedDataException(String msg){
        super(msg);
    }
}
