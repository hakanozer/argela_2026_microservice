package com.works.inventoryservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    final InventoryRepository inventoryRepository;
    final ElasticsearchOperations elasticsearchOperations;

    public InventoryDocument saveInventory(InventoryDocument inventoryDocument) {
        if (inventoryDocument.getId() == null) {
            inventoryDocument.setId(java.util.UUID.randomUUID().toString());
        }
        return inventoryRepository.save(inventoryDocument);
    }

    public Iterable<InventoryDocument> getAllInventories() {
        log.info("getAllInventories");
        return inventoryRepository.findAll();
    }

    // save all method
    public Iterable<InventoryDocument> saveAllInventories(Iterable<InventoryDocument> inventoryDocuments) {
        // id değerini uuid olarak ata
        inventoryDocuments.forEach(item -> { item.setId(UUID.randomUUID().toString() );});
        return inventoryRepository.saveAll(inventoryDocuments);
    }

    public List<InventoryDocument> searchInventory(String q, int page, int size) {

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(query -> query
                        .bool(bool -> bool
                                // 1. Fuzzy multi-match (mevcut)
                                .should(s1 -> s1
                                        .multiMatch(m -> m
                                                .query(q)
                                                .fields("productName^3", "description", "category", "productCode")
                                                .fuzziness("AUTO")
                                        )
                                )
                                // 2. productName prefix match (baş harften itibaren arama)
                                .should(s2 -> s2
                                        .matchPhrasePrefix(mp -> mp
                                                .field("productName")
                                                .query(q.toLowerCase())
                                        )
                                )
                                // 3. Wildcard - productName (keyword değil, text tokenları üzerinde)
                                .should(s3 -> s3
                                        .wildcard(w -> w
                                                .field("productName")
                                                .value("*" + q.toLowerCase() + "*")
                                                .caseInsensitive(true) // ES 7.10+
                                        )
                                )
                                .minimumShouldMatch("1")
                        )
                )
                .withPageable(PageRequest.of(page, size))
                .build();

        SearchHits<InventoryDocument> hits =
                elasticsearchOperations.search(searchQuery, InventoryDocument.class);

        return hits.stream()
                .map(SearchHit::getContent)
                .toList();
    }

}
