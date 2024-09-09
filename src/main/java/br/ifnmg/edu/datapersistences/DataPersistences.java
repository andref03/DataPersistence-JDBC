package br.ifnmg.edu.datapersistences;

import br.ifnmg.edu.user.User;
import br.ifnmg.edu.user.UserDao;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Esta é a classe principal do programa, onde estão os testes pedidos,
 * todos dentro do método estático main. Os testes estão colapsados, para
 * facilitar visualização e manutenção.
 * 
 * @author andref03
 */
public class DataPersistences {

    public static void main(String[] args) {
        
        User u1 = new User();
        User u2 = new User();
        User u3 = new User();
        User u4 = new User();
        User u5 = new User();
        User u6 = new User();
        
        //<editor-fold defaultstate="collapsed" desc="Testando as Inserções">
        try {
            u1.setName("Ana Zaira");
            u1.setEmail("a.zaira@mail.com");
            u1.setPassword("123");
            u1.setLastAccess(LocalDateTime.now().minusDays(1).withHour(17).withMinute(0).withSecond(0).withNano(0));
            u1.setActive(true);
            
            u2.setName("Beatriz Yana");
            u2.setEmail("b.yana@mail.com");
            u2.setPassword("456");
            u2.setLastAccess(LocalDateTime.now().withHour(3).withMinute(0).withSecond(0).withNano(0));
            u2.setActive(true);
            
            u3.setName("Cecília Xerxes");
            u3.setEmail("c.xerxes@mail.com");
            u3.setPassword("789");
            u3.setLastAccess(LocalDateTime.now().minusDays(2).withHour(12).withMinute(0).withSecond(0).withNano(0));
            u3.setActive(true);
            
            u4.setName("Débora Wendel");
            u4.setEmail("debora.w@mail.com");
            u4.setPassword("147");
            u4.setLastAccess(null);
            u4.setActive(false);
            
            u5.setName("Eugênia Vale");
            u5.setEmail("e.vale@mail.com");
            u5.setPassword("258");
            u5.setLastAccess(LocalDateTime.now().withHour(6).withMinute(0).withSecond(0).withNano(0));
            u5.setActive(true);
            
            u6.setName("Fernanda Uchoa");
            u6.setEmail("f.vale@mail.com");
            u6.setPassword("369");
            u6.setLastAccess(LocalDateTime.now().minusDays(1).withHour(23).withMinute(59).withSecond(0).withNano(0));
            u6.setActive(false);
            
        } catch(Exception e){
            System.out.println(">> " + e.getMessage());
        }
        
//        new UserDao().saveOrUpdate(u1);
//        new UserDao().saveOrUpdate(u2);
//        new UserDao().saveOrUpdate(u3);
//        new UserDao().saveOrUpdate(u4);
//        new UserDao().saveOrUpdate(u5);
//        new UserDao().saveOrUpdate(u6);
        
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Testando os updates">
        try {
            u1.setId(u1.getId());
            u1.setLastAccess(LocalDateTime.now().withSecond(0).withNano(0));
            
            u3.setId(u3.getId());
            u3.setActive(false);
            
            u4.setId(u4.getId());
            u4.setEmail("d.wendel@mail.com");
            u4.setPassword("&Yh4$Wu9");
            
            u5.setId(u5.getId());
            u5.setPassword("asdfqwerty");
            u5.setLastAccess(LocalDateTime.now().withSecond(0).withNano(0));
            
        } catch (Exception e) {
            System.out.println(">> " + e.getMessage());
        }
        
//        new UserDao().saveOrUpdate(u1);
//        new UserDao().saveOrUpdate(u3);
//        new UserDao().saveOrUpdate(u4);
//        new UserDao().saveOrUpdate(u5);
        
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Impressão do 3º objeto recuperado no Banco de Dados">
        
//        System.out.println();
//        System.out.println(">> Impressao do 3o objeto recuperado no Banco de Dados: " 
//                + new UserDao().findById(u3.getId()));
        
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Impressão de todos os objetos recuperados do Banco de Dados">
        
//        System.out.println();
//        System.out.println(">> Impressao de todos os objetos recuperados do Banco de Dados: "
//                + new UserDao().findAll());
        
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Impressão de todos os objetos com estado ativo">
        
//        System.out.println();
//        System.out.println(">> Impressao de todos os objetos com estado ativo: " 
//                + new UserDao().findByActive(true));
        
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Remoção do 4º objeto inserido no Banco de Dados">
        
//        System.out.println();
//        System.out.println(">> Remocao do 4o objeto inserido no Banco de Dados: " 
//                + (new UserDao().deleteById(u4.getId()) ? "Removido com sucesso!":"Não encontrado!"));
        
        //</editor-fold>
        
    }
}

