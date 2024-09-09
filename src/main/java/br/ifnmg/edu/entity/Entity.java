package br.ifnmg.edu.entity;

/**
 * Esta classe é responsável por ser a super classe das classes que possuirão 
 * o atributo id (identificador).
 * 
 * @author andref03
 */
public abstract class Entity {

    private Long id;

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    //</editor-fold>
    
}
