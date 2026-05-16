package com.works.inventoryservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    final InventoryService inventoryService;

    @PostMapping("add")
    public InventoryDocument addInventory(@RequestBody InventoryDocument inventoryDocument) {
        return inventoryService.saveInventory(inventoryDocument);
    }

    @GetMapping("list")
    public Iterable<InventoryDocument> getAllInventories() {
        return inventoryService.getAllInventories();
    }

    @PostMapping("addAll")
    public Iterable<InventoryDocument> getAllInventories(@RequestBody Iterable<InventoryDocument> inventoryDocuments) {
        return inventoryService.saveAllInventories(inventoryDocuments);
    }

    @GetMapping("/search")
    public List<InventoryDocument> search(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("q: " + q);
        return inventoryService.searchInventory(q, page, size);
    }


}
