package org.example.dao;

import org.example.models.Copia;
import org.example.models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase CopiaDAO proporciona métodos para realizar operaciones CRUD en la entidad Copia.
 * Implementa la interfaz DAO para la entidad Copia.
 */
public class CopiaDAO implements DAO<Copia> {

    public static final String SELECT_FROM_COPIA = "SELECT * FROM Copia";
    public static final String SELECT_FROM_COPIA_WHERE_ID = "SELECT * FROM Copia WHERE id = ?";
    public static final String INSERT_INTO_COPIA = "INSERT into Copia (id_pelicula,id_usuario,estado,soporte)values(?,?,?,?)";
    public static final String UPDATE_COPIA = "UPDATE Copia SET estado = ?, soporte = ? WHERE id = ?";
    public static final String DELETE_FROM_COPIA_WHERE_ID = "DELETE FROM Copia WHERE id = ?";
    public static final String SELECT_FROM_COPIA_WHERE_ID_USUARIO = "SELECT * FROM Copia WHERE id_usuario = ?";
    private static Connection con = null;

    /**
     * Constructor de la clase CopiaDAO.
     *
     * @param c La conexión a la base de datos.
     */
    public CopiaDAO(Connection c) {
        con = c;
    }

    /**
     * Encuentra todas las copias en la base de datos.
     *
     * @return Una lista de todas las copias.
     */
    @Override
    public List<Copia> findAll() {
        var lista = new ArrayList<Copia>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SELECT_FROM_COPIA);

            while (rs.next()) {
                Copia copy = new Copia();
                copy.setId(rs.getInt("id"));
                copy.setId_pelicula(rs.getInt("id_pelicula"));
                copy.setId_usuario(rs.getInt("id_usuario"));
                copy.setEstado(rs.getString("estado"));
                copy.setSoporte(rs.getString("soporte"));
                lista.add(copy);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    /**
     * Encuentra una copia por su ID.
     *
     * @param id El ID de la copia.
     * @return La copia encontrada o null si no se encuentra.
     */
    @Override
    public Copia findById(Integer id) {
        Copia copy = null;
        try (PreparedStatement pst = con.prepareStatement(SELECT_FROM_COPIA_WHERE_ID)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                copy = new Copia();
                copy.setId(rs.getInt("id"));
                copy.setId_pelicula(rs.getInt("id_pelicula"));
                copy.setId_usuario(rs.getInt("id_usuario"));
                copy.setEstado(rs.getString("estado"));
                copy.setSoporte(rs.getString("soporte"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    /**
     * Guarda una nueva copia en la base de datos.
     *
     * @param copia La copia a guardar.
     */
    @Override
    public void save(Copia copia) {
        try (PreparedStatement ps = con.prepareStatement(INSERT_INTO_COPIA, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, copia.getId_pelicula());
            ps.setInt(2, copia.getId_usuario());
            ps.setString(3, copia.getEstado());
            ps.setString(4, copia.getSoporte());

            if (ps.executeUpdate() == 1) {
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                Integer copia_id = keys.getInt(1);
                copia.setId(copia_id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Actualiza una copia existente en la base de datos.
     *
     * @param copia La copia a actualizar.
     */
    @Override
    public void update(Copia copia) {
        try (PreparedStatement ps = con.prepareStatement(UPDATE_COPIA)) {
            ps.setString(1, copia.getEstado());
            ps.setString(2, copia.getSoporte());
            ps.setInt(3, copia.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Elimina una copia de la base de datos.
     *
     * @param copia La copia a eliminar.
     */
    @Override
    public void delete(Copia copia) {
        try (PreparedStatement ps = con.prepareStatement(DELETE_FROM_COPIA_WHERE_ID)) {
            ps.setInt(1, copia.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Encuentra todas las copias asociadas a un usuario.
     *
     * @param usuario El usuario cuyas copias se desean encontrar.
     * @return Una lista de copias asociadas al usuario.
     */
    public List<Copia> findAllByUser(Usuario usuario) {
        Integer id = usuario.getId();
        var lista = new ArrayList<Copia>();
        try (PreparedStatement pst = con.prepareStatement(SELECT_FROM_COPIA_WHERE_ID_USUARIO)) {
            pst.setInt(1, id);
            var rs = pst.executeQuery();

            while (rs.next()) {
                Copia copy = new Copia();
                copy.setId(rs.getInt("id"));
                copy.setId_pelicula(rs.getInt("id_pelicula"));
                copy.setId_usuario(rs.getInt("id_usuario"));
                copy.setEstado(rs.getString("estado"));
                copy.setSoporte(rs.getString("soporte"));
                lista.add(copy);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }
}
