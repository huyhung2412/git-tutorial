package com.example.demo.sevice;

import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractCrudService<Q, RP extends JpaRepository<D, I>, D, I, R> implements BaseCrudService<Q, D, I, R> {

    protected final RP repository;

    protected AbstractCrudService(RP repository) {
        this.repository = repository;
    }

    @Override
    public R create(Q request) {
        D domainEntity = createAndSave(request);
        return toResponse(domainEntity);
    }

    @Override
    public R update(I id, Q request) {
        D domainEntity = updateAndSave(id, request);
        return toResponse(domainEntity);
    }

    protected abstract D updateAndSave(I id, Q request);

    protected abstract D createAndSave(Q request);

    @Override
    public R findById(I id) {
        D domainEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category with id " + id + "is not found"));
        return toResponse(domainEntity);
    }



    @Override
    public void delete(I id) {
        D domainEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category with id " + id + "is not found"));
        repository.delete(domainEntity);
    }
}
