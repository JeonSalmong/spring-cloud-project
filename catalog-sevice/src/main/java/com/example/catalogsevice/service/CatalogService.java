package com.example.catalogsevice.service;

import com.example.catalogsevice.repository.CatalogEntity;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();
}
