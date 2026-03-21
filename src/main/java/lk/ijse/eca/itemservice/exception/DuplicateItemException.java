package lk.ijse.eca.itemservice.exception;

public class DuplicateItemException extends RuntimeException {

    public DuplicateItemException(String itemId) {
        super("Item already exists with ID: " + itemId);
    }
}
