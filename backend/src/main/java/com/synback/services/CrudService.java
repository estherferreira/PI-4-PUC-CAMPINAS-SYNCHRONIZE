// package com.synback.services;

// import org.springframework.data.mongodb.repository.MongoRepository;
// import java.io.Serializable;
// import java.util.Optional;

// public class CrudService<T, ID extends Serializable> {

//     private final MongoRepository<T, ID> repository;

//     public CrudService(MongoRepository<T, ID> repository) {
//         this.repository = repository;
//     }

//     public T save(T entity) {
//         return repository.save(entity);
//     }

//     public Optional<T> findById(ID id) {
//         return repository.findById(id);
//     }

//     public void deleteById(ID id) {
//         repository.deleteById(id);
//     }

//     public T update(T entity) {
//         return repository.save(entity);
//     }
// }