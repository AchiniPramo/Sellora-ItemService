package lk.ijse.eca.itemservice.service.impl;

import lk.ijse.eca.itemservice.dto.ItemDto;
import lk.ijse.eca.itemservice.entity.Item;
import lk.ijse.eca.itemservice.exception.DuplicateItemException;
import lk.ijse.eca.itemservice.exception.ItemNotFoundException;
import lk.ijse.eca.itemservice.mapper.ItemMapper;
import lk.ijse.eca.itemservice.repository.ItemRepository;
import lk.ijse.eca.itemservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    @Transactional
    public ItemDto createItem(ItemDto dto) {
        log.debug("Creating item with ID: {}", dto.getItemId());

        if (itemRepository.existsById(dto.getItemId())) {
            log.warn("Duplicate item ID detected: {}", dto.getItemId());
            throw new DuplicateItemException(dto.getItemId());
        }

        Item saved = itemRepository.save(itemMapper.toEntity(dto));
        log.info("Item created successfully: {}", saved.getItemId());
        return itemMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemDto getItem(String itemId) {
        log.debug("Fetching item with ID: {}", itemId);
        return itemRepository.findById(itemId)
                .map(itemMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("Item not found: {}", itemId);
                    return new ItemNotFoundException(itemId);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemDto> getAllItems() {
        log.debug("Fetching all items");
        List<ItemDto> items = itemRepository.findAll()
                .stream()
                .map(itemMapper::toDto)
                .toList();
        log.debug("Fetched {} item(s)", items.size());
        return items;
    }

    @Override
    @Transactional
    public ItemDto updateItem(String itemId, ItemDto dto) {
        log.debug("Updating item with ID: {}", itemId);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> {
                    log.warn("Item not found for update: {}", itemId);
                    return new ItemNotFoundException(itemId);
                });

        itemMapper.updateEntity(dto, item);
        Item updated = itemRepository.save(item);
        log.info("Item updated successfully: {}", updated.getItemId());
        return itemMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteItem(String itemId) {
        log.debug("Deleting item with ID: {}", itemId);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> {
                    log.warn("Item not found for deletion: {}", itemId);
                    return new ItemNotFoundException(itemId);
                });

        itemRepository.delete(item);
        log.info("Item deleted successfully: {}", itemId);
    }
}
