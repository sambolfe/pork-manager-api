package br.csi.porkManagerApi.exceptions;

public class InvalidRequestDataException extends RuntimeException{
    public InvalidRequestDataException(String message) {
        super(message);
    }
}
