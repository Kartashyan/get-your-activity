package com.getyourguide.demo.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getyourguide.demo.domain.Supplier;
import com.getyourguide.demo.domain.SupplierRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.util.List;

@Repository
public class SupplierRepositoryImpl implements SupplierRepository {

    private List<Supplier> suppliers;

    public SupplierRepositoryImpl() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        suppliers = mapper.readValue(
                new ClassPathResource("suppliers.json").getInputStream(),
                new TypeReference<List<Supplier>>() {}
        );
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return suppliers;
    }

    @Override
    public Supplier getSupplierById(Long id) {
        return suppliers.stream()
                .filter(supplier -> supplier.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}