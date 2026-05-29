import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/dbusuarios";
        String dbUsuario = "root";
        String dbSenha = "123456";

        System.out.println("Estabelecendo conexão com o banco de dados");

        try (Connection dbConexao = DriverManager.getConnection(dbUrl,dbUsuario,dbSenha)) {
            System.out.println("Conexão com banco de dados finalizada com sucesso");
        } catch (SQLException e) {
            System.err.println("Não foi possível conectar com o banco de dados");
            e.printStackTrace();
        }
    }
}
