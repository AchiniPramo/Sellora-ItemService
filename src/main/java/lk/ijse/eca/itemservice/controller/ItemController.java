package lk.ijse.eca.itemservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lk.ijse.eca.itemservice.dto.ItemDto;
import lk.ijse.eca.itemservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {

    private final ItemService itemService;

    private static final String PROGRAM_ID_REGEXP = "^[A-Z]+$";

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ItemDto> createItem(
            @Validated({Default.class, ItemDto.OnCreate.class})
            @RequestBody ItemDto dto) {
        log.info("POST /api/v1/items - itemId: {}", dto.getItemId());
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.createItem(dto));
    }

    @GetMapping(value = "/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDto> getItem(
            @PathVariable
            @Pattern(regexp = PROGRAM_ID_REGEXP, message = "Item ID must contain uppercase letters only (A-Z)")
            String itemId) {
        log.info("GET /api/v1/items/{}", itemId);
        return ResponseEntity.ok(itemService.getItem(itemId));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemDto>> getAllItems() {
        log.info("GET /api/v1/items - retrieving all items");
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @PutMapping(
            value = "/{itemId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ItemDto> updateItem(
            @PathVariable
            @Pattern(regexp = PROGRAM_ID_REGEXP, message = "Item ID must contain uppercase letters only (A-Z)")
            String itemId,
            @Valid @RequestBody ItemDto dto) {
        log.info("PUT /api/v1/items/{}", itemId);
        return ResponseEntity.ok(itemService.updateItem(itemId, dto));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable
            @Pattern(regexp = PROGRAM_ID_REGEXP, message = "Item ID must contain uppercase letters only (A-Z)")
            String itemId) {
        log.info("DELETE /api/v1/items/{}", itemId);
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }
}
