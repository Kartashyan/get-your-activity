package com.getyourguide.demo.domain;

import java.io.IOException;
import java.util.List;

public interface SupplierRepository {
  List<Supplier> getAllSuppliers() throws IOException;
  Supplier getSupplierById(Long id) throws IOException;
}
