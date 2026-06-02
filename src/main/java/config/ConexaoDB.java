package main.java.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConexaoDB {

    public static void main(String[] args) {}

    private static  Connection connection = null;

    private static String dbUrl;
    private static String dbuser;
    private static String dbsenha;


    public static Connection getConections(){
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(dbUrl,dbuser,dbsenha);
                System.out.println("Sucesso ao estabelecer a conexão com o banco de dados");
            } else if (connection != null) {
                System.out.println("Já existe uma conexão com o banco de dados e será reutilizada");
            }
        } catch (SQLException sqlException) { throw new RuntimeException(sqlException);}
        return connection;

    }


    private static Properties loadproperties() {
        try (FileInputStream fileInputStream = new FileInputStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            System.out.println("Sucesso ao carregar as propriedades do banco de dados. Pronto para tentativa de conexão.");
            return properties;
        } catch (IOException e) {
            System.err.println("Erro encontrado ao carregar as configurações do banco de dados. Verifique o arquivo 'db.properties'.");
            throw new RuntimeException(e);
        }
    }

    static {
        Properties properties = loadproperties();

        String dbhost = properties.getProperty("dbhost");
        String dbporta = properties.getProperty("dbporta");
        String database = properties.getProperty("database");

        dbuser = properties.getProperty("dbuser");
        dbsenha = properties.getProperty("dbsenha");
        dbUrl = "jdbc:mysql://" + dbhost + ":" + dbporta + "/" + database;
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        }
    }
}

