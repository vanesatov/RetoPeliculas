package org.example.dao;

import org.example.models.Usuario;

import java.util.List;

/**
 * Interfaz genérica para operaciones CRUD.
 *
 * @param <T> El tipo de entidad.
 */
public interface DAO<T> {

    /**
     * Encuentra todas las entidades.
     *
     * @return Una lista de todas las entidades.
     */
    public List<T> findAll();

    /**
     * Encuentra una entidad por su ID.
     *
     * @param id El ID de la entidad.
     * @return La entidad encontrada o null si no se encuentra.
     */
    public T findById(Integer id);

    /**
     * Guarda una nueva entidad en la base de datos.
     *
     * @param t La entidad a guardar.
     */
    public void save(T t);

    /**
     * Actualiza una entidad existente en la base de datos.
     *
     * @param t La entidad a actualizar.
     */
    public void update(T t);

    /**
     * Elimina una entidad de la base de datos.
     *
     * @param t La entidad a eliminar.
     */
    public void delete(T t);
}