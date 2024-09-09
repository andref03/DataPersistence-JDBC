package br.ifnmg.edu.repository;

import br.ifnmg.edu.entity.Entity;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Esta interface é responsável por declarar os métodos que serão implementados
 * na classe Dao.
 * 
 * @author andref03
 */
public interface IDao<T extends Entity> {

    public String getSaveStatment();

    public String getUpdateStatment();

    public void composeSaveOrUpdateStatement(PreparedStatement pstmt, T e);

    public Long saveOrUpdate(T e);

    public String getFindByIdStatment();

    public String getDeleteByIdStatment();

    public T findById(Long id);

    public String getFindAllStatment();

    public List<T> findAll();

    public String getFindByActiveStatment();

    public T extractObject(ResultSet resultSet);

    public List<T> extractObjects(ResultSet resultSet);

}
