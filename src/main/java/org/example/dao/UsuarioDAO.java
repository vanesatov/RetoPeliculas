package org.example.dao;

import org.example.models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase DAO para la entidad Usuario.
 * Implementa las operaciones CRUD y otras consultas específicas para la entidad Usuario.
 */
public class UsuarioDAO implements DAO<Usuario> {


    public static final String SELECT_FROM_USUARIO = "SELECT * FROM Usuario";
    public static final String SELECT_FROM_USUARIO_WHERE_ID = "SELECT * FROM Usuario WHERE id = ?";
    public static final String INSERT_INTO_USUARIO = "insert into Usuario(nombre_usuario,contraseña)values(?,?)";
    public static final String UPDATE_USUARIO = "update Usuario set nombre_usuario = ?, contraseña = ? where id = ?";
    public static final String DELETE_FROM_USUARIO_WHERE_ID = "delete from Usuario where id = ?";
    public static final String SELECT_FROM_USUARIO_WHERE_NOMBRE_USUARIO_AND_CONTRASEÑA = "SELECT * FROM Usuario WHERE nombre_usuario = ? AND contraseña = ?";
    private static Connection con = null;

    public UsuarioDAO(Connection c) {
        con = c;
    }

    /**
     * Encuentra todos los usuarios en la base de datos.
     *
     * @return Una lista de todos los usuarios.
     */
    @Override
    public List<Usuario> findAll() {
        var lista = new ArrayList<Usuario>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SELECT_FROM_USUARIO);

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                usuario.setContraseña(rs.getString("contraseña"));
                lista.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }


    /**
     * Encuentra un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @return El usuario encontrado o null si no se encuentra.
     */
    @Override
    public Usuario findById(Integer id) {
        Usuario usuario = null;
        try (PreparedStatement ps = con.prepareStatement(SELECT_FROM_USUARIO_WHERE_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                usuario.setContraseña(rs.getString("contraseña"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param usuario El usuario a guardar.
     */
    @Override
    public void save(Usuario usuario) {
        try (PreparedStatement ps = con.prepareStatement(INSERT_INTO_USUARIO, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombre_usuario());
            ps.setString(2, usuario.getContraseña());

            if (ps.executeUpdate() == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                usuario.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Actualiza un usuario existente en la base de datos.
     *
     * @param usuario El usuario a actualizar.
     */
    @Override
    public void update(Usuario usuario) {
        try (PreparedStatement ps = con.prepareStatement(UPDATE_USUARIO)) {
            ps.setString(1, usuario.getNombre_usuario());
            ps.setString(2, usuario.getContraseña());
            ps.setInt(3, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param usuario El usuario a eliminar.
     */
    @Override
    public void delete(Usuario usuario) {
        try (PreparedStatement ps = con.prepareStatement(DELETE_FROM_USUARIO_WHERE_ID)) {
            ps.setInt(1, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Valida un usuario por su nombre de usuario y contraseña.
     *
     * @param nombreUsuario El nombre de usuario.
     * @param password      La contraseña.
     * @return El usuario validado o null si no se encuentra.
     */

    public Usuario validarUsuario(String nombreUsuario, String password) {
        Usuario salida = null;

        try (PreparedStatement ps = con.prepareStatement(SELECT_FROM_USUARIO_WHERE_NOMBRE_USUARIO_AND_CONTRASEÑA)) {
            ps.setString(1, nombreUsuario);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                salida = new Usuario();
                salida.setId(rs.getInt("id"));
                salida.setNombre_usuario(rs.getString("nombre_usuario"));
                salida.setContraseña(rs.getString("contraseña"));

                salida.setCopias(new CopiaDAO(con).findAllByUser(salida));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return salida;
    }
}

