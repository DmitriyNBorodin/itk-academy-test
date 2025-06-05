package itk_test.util;

public class NotEnoughMoneyException extends RuntimeException{
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
