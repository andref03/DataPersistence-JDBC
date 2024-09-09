package br.ifnmg.edu.user;

import br.ifnmg.edu.repository.Dao;
import br.ifnmg.edu.repository.DbConnection;
import static br.ifnmg.edu.user.User.converterStringToLocalDateTime;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta é a classe de acesso aos dados do objeto (Data Access Object) específico
 * para o usuário (classe User). É aqui onde se declaram as devidas sentenças SQL 
 * para realizar operações no Banco de Dados (CRUD). Além disso, também possui a
 * sobrescrita do método de extrai um objeto (extractObject(ResultSet resultSet)).
 * 
 * @author andref03
 */
public class UserDao extends Dao<User> {

    public static final String TABLE = "user";

    @Override
    public String getDeleteByIdStatment() {
        return "delete from " + TABLE + 
                " where id = ?";
    }

    @Override
    public String getSaveStatment() {
        return "insert into " + TABLE
                + "(name, email, password, lastAccess, active)"
                + " values (?, ?, ?, ?, ?)";
    }

    @Override
    public String getUpdateStatment() {
        return "update " + TABLE
                + " set name = ?, email = ?,"
                + " password = ?, lastAccess = ?,"
                + " active = ? where id = ?";
    }

    @Override
    public void composeSaveOrUpdateStatement(PreparedStatement pstmt, User e) {
        try {
            pstmt.setString(1, e.getName());

            pstmt.setString(2, e.getEmail());

            pstmt.setString(3, e.getPassword());

            pstmt.setString(4, e.getLastAccess());

            pstmt.setString(5, e.getActive());

            // Just for the update
            if (e.getId() != null) {
                pstmt.setLong(6, e.getId());
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getFindByIdStatment() {
        return "select id, name, email, password, lastAccess, active"
                + " from " + TABLE
                + " where id = ?";
    }

    @Override
    public String getFindByActiveStatment() {
        return "select id, name, email, password, lastAccess, active"
                + " from " + TABLE
                + " where active = ?";
    }

    @Override
    public String getFindAllStatment() {
        return "select id, name, email, password, lastAccess, active"
                + " from " + TABLE;
    }

    @Override
    public User extractObject(ResultSet resultSet) {

        User user = null;

        try {
            user = new User();
            user.setId(resultSet.getLong("id"));
            try {
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
            } catch (Exception ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }

            // é necessário converter a String (varchar) da tabela para LocalDateTime no código
            String lastAccessExtraidoString = resultSet.getString("lastAccess");
            // converte aqui:
            LocalDateTime lastAccesExtraidoLocalDateTime = converterStringToLocalDateTime(lastAccessExtraidoString);
            user.setLastAccess(lastAccesExtraidoLocalDateTime);

            // é necessário converter a String (varchar) da tabela para Boolean no código
            String activeExtraidoString = resultSet.getString("active");
            // converte aqui:
            Boolean activeExtraidoBoolean = activeExtraidoString.equals("Sim");
            user.setActive(activeExtraidoBoolean);

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

}
