package com.works.inventoryservice;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;


public interface InventoryRepository extends ElasticsearchRepository<InventoryDocument, String> {
        // Ekstra sorgular ekleyebilirsiniz, örneğin:
        List<InventoryDocument> findByProductCode(String productCode);
        List<InventoryDocument> findByCategory(String category);

}
