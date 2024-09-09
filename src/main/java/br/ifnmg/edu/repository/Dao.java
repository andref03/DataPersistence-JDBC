package br.ifnmg.edu.repository;

import br.ifnmg.edu.entity.Entity;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta é a classe Dao (Data Access Object), que basicamente possui a implementação
 * dos códigos responsáveis por fazer ações dentro do banco de dados. Esta classe
 * implementa os métodos declarados na interface IDao, para uma determinada 
 * classe T (que herda de Entity).
 * 
 * @author andref03
 */
public abstract class Dao<T extends Entity>
        implements IDao<T> {

    public static final String DB = "alphasystem";

    @Override
    public Long saveOrUpdate(T e) {

        // Primary key
        Long id = 0L;

        if (e.getId() == null
                // Negative IDs acts as flags to insert new registers
                // on one-to-one relationships with same PKs
                || e.getId() <= 0) {

            // Insert a new register
            // try-with-resources
            try (PreparedStatement preparedStatement
                    = DbConnection.getConnection().prepareStatement(
                            getSaveStatment(),
                            Statement.RETURN_GENERATED_KEYS)) {

                // Assemble the SQL statement with the data (->?)
                composeSaveOrUpdateStatement(preparedStatement, e);

                // Show the full sentence
                System.out.println(">> SQL: " + preparedStatement);

                // Performs insertion into the database
                preparedStatement.executeUpdate();

                // Retrieve the generated primary key
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                // Moves to first retrieved data
                if (resultSet.next()) {

                    // Retrieve the returned primary key
                    id = resultSet.getLong(1);
                }

            } catch (Exception ex) {
                System.out.println(">> " + ex);
            }

        } else {
            // Update existing record
            try (PreparedStatement preparedStatement
                    = DbConnection.getConnection().prepareStatement(
                            getUpdateStatment())) {

                // Assemble the SQL statement with the data (->?)
                composeSaveOrUpdateStatement(preparedStatement, e);

                // Show the full sentence
                System.out.println(">> SQL: " + preparedStatement);

                // Performs the update on the database
                preparedStatement.executeUpdate();

                // Keep the primary key
                id = ((Entity) e).getId();

            } catch (Exception ex) {
                System.out.println("Exception: " + ex);
            }
        }

        return id;
    }

    @Override
    public T findById(Long id) {

        try (PreparedStatement preparedStatement
                = DbConnection.getConnection().prepareStatement(
                        getFindByIdStatment())) {

            // Assemble the SQL statement with the id
            preparedStatement.setLong(1, id);

            // Show the full sentence
            System.out.println(">> SQL: " + preparedStatement);

            // Performs the query on the database
            ResultSet resultSet = preparedStatement.executeQuery();

            // Returns the respective object if exists
            if (resultSet.next()) {
                return extractObject(resultSet);
            }

        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }

        return null;
    }

    public List<T> findByActive(Boolean active) {
        // converte active para String, pois está assim inserido na tabela user
        String activeString = active ? "Sim" : "Não";

        try (PreparedStatement preparedStatement
                = DbConnection.getConnection().prepareStatement(
                        getFindByActiveStatment())) {

            preparedStatement.setString(1, activeString);

            // consulta SQL na saída para termos um controle
            System.out.println(">> SQL: " + preparedStatement);

            // realizandoa consulta no banco de dados
            ResultSet resultSet = preparedStatement.executeQuery();

            // itera sobre todos os registros, resulta em false caso não encontre mais nenhum
            if (resultSet.next()) {
                return extractObjects(resultSet);
            }

        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }

        return null;
    }

    @Override
    public List<T> findAll() {

        try (PreparedStatement preparedStatement
                = DbConnection.getConnection().prepareStatement(
                        getFindAllStatment())) {

            // Show the full sentence
            System.out.println(">> SQL: " + preparedStatement);

            // Performs the query on the database
            ResultSet resultSet = preparedStatement.executeQuery();

            // Returns the respective object
            return extractObjects(resultSet);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }

        return null;
    }

    @Override
    public List<T> extractObjects(ResultSet resultSet) {
        List<T> objects = new ArrayList<>();

        try {
            while (resultSet.next()) {
                objects.add(extractObject(resultSet));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return objects.isEmpty() ? null : objects;
    }

    public boolean deleteById(Long id) {

        try (PreparedStatement preparedStatement
                = DbConnection.getConnection().prepareStatement(
                        getDeleteByIdStatment())) {

            preparedStatement.setLong(1, id);

            // consulta SQL na saída para termos um controle
            System.out.println(">> SQL: " + preparedStatement);

            // realiza a atualização no banco de dados e informa se atualizou pelo menos um registro
            int linhaAlterada = preparedStatement.executeUpdate();

            // se atualizou algum registro (nesse caso, um delete), então retorna verdadeiro (deletado com sucesso)
            return linhaAlterada > 0;

        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }

        return false;
    }

}
