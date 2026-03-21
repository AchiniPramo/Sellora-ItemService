package lk.ijse.eca.itemservice.service;

import lk.ijse.eca.itemservice.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto createItem(ItemDto dto);

    ItemDto getItem(String itemId);

    List<ItemDto> getAllItems();

    ItemDto updateItem(String itemId, ItemDto dto);

    void deleteItem(String itemId);
}
