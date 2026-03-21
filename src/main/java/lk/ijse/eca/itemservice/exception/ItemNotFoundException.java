package lk.ijse.eca.itemservice.exception;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String itemId) {
        super("Item not found with ID: " + itemId);
    }
}
