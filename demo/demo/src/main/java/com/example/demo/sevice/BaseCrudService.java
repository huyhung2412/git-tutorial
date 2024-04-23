package com.example.demo.sevice;

public interface BaseCrudService<Q, D, I, R> {

    R create(Q request);

    R findById(I id);

    R update(I id, Q request);

    void delete(I id);

    R toResponse(D domainEntity);

}
