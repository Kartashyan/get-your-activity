package com.getyourguide.demo.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getyourguide.demo.domain.Activity;
import com.getyourguide.demo.domain.ActivityRepository;
import com.getyourguide.demo.domain.Supplier;
import com.getyourguide.demo.domain.SupplierRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.util.List;

@Repository
public class ActivityRepositoryImpl implements ActivityRepository {

    private final SupplierRepository supplierRepository;

    public ActivityRepositoryImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Activity> getAllActivities() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Activity> activities = mapper.readValue(
                new ClassPathResource("activities.json").getInputStream(),
                new TypeReference<List<Activity>>() {}
        );

        for (Activity activity : activities) {
            Long supplierId = activity.getSupplier() != null ? activity.getSupplier().getId() : null;
            if (supplierId != null) {
                Supplier supplier = supplierRepository.getSupplierById(supplierId);
                activity.setSupplier(supplier);
            }
        }

        return activities;
    }
}