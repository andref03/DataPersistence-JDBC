package br.ifnmg.edu.user;

import br.ifnmg.edu.entity.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Esta é a classe da qual o Banco de Dados irá retirar informações para a sua
 * construção, e representa a entidade de usuário no sistema. É aqui onde se
 * declaram os atributos que serão colocados lá, além de que esta classe possui
 * métodos de getters/setters e outros métodos complementares para realizar as 
 * devidas conversões na relação entre código-fonte e Banco de Dados. Por fim,
 * também possui a sobrescrita do toString(), para permitir a visualização do
 * objeto.
 * 
 * @author andref03
 */
public class User extends Entity {

    private String name;
    private String email;
    private String password;
    private LocalDateTime lastAccess;
    private Boolean active;

    public User() {
        setActive(true);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (name.length() > 150) {
            throw new Exception("O nome deve ter até 150 caracteres.");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        if (email.length() > 255) {
            throw new Exception("O email deve ter até 255 caracteres.");
        }
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
        if (password.length() > 64) {
            throw new Exception("A senha deve ter até 64 caracteres.");
        }
        this.password = password;
    }

    public String getLastAccess() {

        if (lastAccess == null) {
            return "Nunca acessou";
        }

        LocalDateTime agora = LocalDateTime.now().withSecond(0).withNano(0);
        LocalDate acesso = lastAccess.toLocalDate();
        LocalDate hoje = LocalDate.now();
        LocalDate ontem = hoje.minusDays(1);
        LocalDate anteontem = hoje.minusDays(2);

        if (lastAccess.equals(agora)) {
            // a variável agora possui a data atual e a hora e minuto atuais (ignorei segundos)
            return "Agora";
        }
        if (acesso.equals(hoje)) {
            return "Hoje as " + lastAccess.toLocalTime();
        }
        if (acesso.equals(ontem)) {
            return "Ontem as " + lastAccess.toLocalTime();
        }
        if (acesso.equals(anteontem)) {
            return "Anteontem as " + lastAccess.toLocalTime();
        }
        if (acesso.isBefore(anteontem)) {
            return "Acesso antigo";
        }

        return null;
    }

    public void setLastAccess(LocalDateTime lastAccess) {
        this.lastAccess = lastAccess;
    }

    public String getActive() {
        return active ? "Sim" : "Não";
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    //</editor-fold>

    public static LocalDateTime converterStringToLocalDateTime(String input) {
        // tira os espaços em branco nas extremidades
        input = input.trim();

        if (input.equals("Agora")) {
            // se aparecer 'Agora' na tabela, é pq é data atual e hora:minuto atual (ignorei segundos)
            return LocalDateTime.now().withSecond(0).withNano(0);
        } else if (input.equals("Nunca acessou")) {
            // se aparecer 'Nunca acessou' na tabela é pq o lastAccess era nulo, aí retornamos null
            return null;
        }

        try {
            // verifica se a String contém " as "
            int index = input.indexOf(" as ");
            if (index == -1) {
                throw new IllegalArgumentException("Formato inválido");
            }

            // extrai as partes da string
            String dia = input.substring(0, index).trim();
            String hora = input.substring(index + 4).trim();

            // verifica as possibilidades para cada variação de dia
            int diferencaDias;

            switch (dia) {
                case "Hoje":
                    diferencaDias = 0;
                    break;
                case "Ontem":
                    diferencaDias = -1;
                    break;
                case "Anteontem":
                    diferencaDias = -2;
                    break;
                default:
                    throw new IllegalArgumentException("Dia não reconhecido");
            }

            // reconstrói o LocalDate de LocalDateTime
            LocalDate date = LocalDate.now().plusDays(diferencaDias);
            // reconstrói o LocalTime de LocalDateTime
            LocalTime time = LocalTime.parse(hora, DateTimeFormatter.ofPattern("HH:mm"));

            // definindo portanto o LocalDateTime a partir das reconstruções acima
            return LocalDateTime.of(date, time);

        } catch (DateTimeParseException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Formato de entrada inválido", e);
        }
    }

    @Override
    public String toString() {
        return "\n(id = " + getId()
                + ", name = " + name
                + ", email = " + email
                + ", password = " + password
                + ", lastAccess = " + getLastAccess()
                + ", active = " + getActive()
                + ")";
    }

}
