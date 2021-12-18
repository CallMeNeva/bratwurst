package io.github.altoukhovmax.frankfurterdesktop.service.response.mapper;

@FunctionalInterface
public interface DTOMapper<D, T> {

    T map(D dataObj) throws DTOMappingException;
}
