package org.example.dao;

import org.example.models.Pelicula;
import org.example.models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PeliculaDAO es una clase de Objeto de Acceso a Datos (DAO) que proporciona métodos para interactuar con la tabla Pelicula en la base de datos.
 * Incluye métodos para encontrar, guardar, actualizar y eliminar registros de Pelicula.
 */

public class PeliculaDAO implements DAO<Pelicula> {

    public static final String SELECT_FROM_PELICULA_WHERE_ID = "SELECT * FROM Pelicula WHERE id = ?";
    public static final String INSERT_INTO_PELICULA = "insert into Pelicula(titulo, genero, año, descripcion, director)values(?,?,?,?)";
    public static final String SELECT_FROM_PELICULA = "SELECT * FROM Pelicula";
    public static final String UPDATE_PELICULA = "update Pelicula set titulo = ?, genero = ?, año = ?, descripcion = ?, director = ? where id = ?";
    public static final String DELETE_FROM_PELICULA_WHERE_ID = "delete from Pelicula where id = ?";
    public static final String SELECT_FROM_PELICULA_WHERE_TITULO = "SELECT * FROM Pelicula WHERE titulo = ?";
    private static Connection con = null;

    public PeliculaDAO(Connection c) {
        con = c;
    }


    /**
     * Encuentra todas las películas en la base de datos.
     *
     * @return Una lista de todas las películas.
     */
    @Override
    public List<Pelicula> findAll() {
        var lista = new ArrayList<Pelicula>();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SELECT_FROM_PELICULA);

            while (rs.next()) {
                Pelicula peli = new Pelicula();
                peli.setId(rs.getInt("id"));
                peli.setTitulo(rs.getString("titulo"));
                peli.setGenero(rs.getString("genero"));
                peli.setAño(rs.getInt("año"));
                peli.setDescripcion(rs.getString("descripcion"));
                peli.setDirector(rs.getString("director"));
                lista.add(peli);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;

    }

    /**
     * Encuentra una película por su ID.
     *
     * @param id El ID de la película.
     * @return La película encontrada o null si no se encuentra.
     */
    @Override
    public Pelicula findById(Integer id) {
        Pelicula pelicula = null;

        try (PreparedStatement ps = con.prepareStatement(SELECT_FROM_PELICULA_WHERE_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                pelicula = new Pelicula();
                pelicula.setId(rs.getInt("id"));
                pelicula.setTitulo(rs.getString("titulo"));
                pelicula.setGenero(rs.getString("genero"));
                pelicula.setAño(rs.getInt("año"));
                pelicula.setDescripcion(rs.getString("descripcion"));
                pelicula.setDirector(rs.getString("director"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pelicula;
    }

    /**
     * Guarda una nueva película en la base de datos.
     *
     * @param pelicula La película a guardar.
     */
    @Override
    public void save(Pelicula pelicula) {
        try (PreparedStatement ps = con.prepareStatement(INSERT_INTO_PELICULA, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, pelicula.getTitulo());
            ps.setString(2, pelicula.getGenero());
            ps.setInt(3, pelicula.getAño());
            ps.setString(4, pelicula.getDescripcion());
            ps.setString(5, pelicula.getDirector());

            if (ps.executeUpdate() == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                pelicula.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Actualiza una película existente en la base de datos.
     *
     * @param pelicula La película a actualizar.
     */
    @Override
    public void update(Pelicula pelicula) {
        try (PreparedStatement ps = con.prepareStatement(UPDATE_PELICULA)) {
            ps.setString(1, pelicula.getTitulo());
            ps.setString(2, pelicula.getGenero());
            ps.setInt(3, pelicula.getAño());
            ps.setString(4, pelicula.getDescripcion());
            ps.setString(5, pelicula.getDirector());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Elimina una película de la base de datos.
     *
     * @param pelicula La película a eliminar.
     */
    @Override
    public void delete(Pelicula pelicula) {
        try (PreparedStatement ps = con.prepareStatement(DELETE_FROM_PELICULA_WHERE_ID)) {
            ps.setInt(1, pelicula.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Encuentra una película por su título.
     *
     * @param titulo El título de la película.
     * @return La película encontrada o null si no se encuentra.
     */

    public Pelicula findByTitulo(String titulo) {
        Pelicula pelicula = null;
        String query = SELECT_FROM_PELICULA_WHERE_TITULO;

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, titulo);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                pelicula = new Pelicula();
                pelicula.setId(rs.getInt("id"));
                pelicula.setTitulo(rs.getString("titulo"));
                pelicula.setGenero(rs.getString("genero"));
                pelicula.setAño(rs.getInt("año"));
                pelicula.setDescripcion(rs.getString("descripcion"));
                pelicula.setDirector(rs.getString("director"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pelicula;
    }
}
