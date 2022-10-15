package dat.backend.model.persistence;

import dat.backend.model.entities.Item;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;

class ItemMapperTest
{
    // TODO: Change mysql login credentials if needed below

    private final static String USER = "fourthingsplus";
    private final static String PASSWORD = "1234";
    private final static String URL = "jdbc:mysql://localhost:3306/fourthingsplus_test?serverTimezone=CET&allowPublicKeyRetrieval=true&useSSL=false";

    private static ConnectionPool connectionPool;

    private static Item i1, i2, i3;

    @BeforeAll
    public static void setUpClass()
    {
        connectionPool = new ConnectionPool(USER, PASSWORD, URL);

        try (Connection testConnection = connectionPool.getConnection())
        {
            try (Statement stmt = testConnection.createStatement())
            {
                // Create test database - if not exist
                stmt.execute("CREATE DATABASE  IF NOT EXISTS fourthingsplus_test;");

                // TODO: Create user table. Add your own tables here
                stmt.execute("CREATE TABLE IF NOT EXISTS fourthingsplus_test.user LIKE fourthingsplus.user;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fourthingsplus_test.item LIKE fourthingsplus.item;");
            }
        }
        catch (SQLException throwables)
        {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
    }

    @BeforeEach
    void setUp()
    {
        i1 = new Item(1, "Køb 2 liter mælk", false, "user");
        i2 = new Item(2, "Se tv", false, "user");
        i3 = new Item(3, "Hent bil", true, "user");

        try (Connection testConnection = connectionPool.getConnection())
        {
            try (Statement stmt = testConnection.createStatement())
            {
                // TODO: Remove all rows from all tables - add your own tables here
                stmt.execute("truncate table user");
                stmt.execute("truncate table item");

                // TODO: Insert a few users - insert rows into your own tables here
                stmt.execute("insert into user (username, password, role) " +
                        "values ('user','1234','user'),('admin','1234','admin'), ('ben','1234','user')");

                // TODO: Insert a few items
                stmt.execute("ALTER TABLE `item` DISABLE KEYS");
                stmt.execute("INSERT INTO item (item_id, done, name, username) " +
                        " VALUES (1, false, 'Køb 2 liter mælk', 'user'), (2, false, 'Se tv','user'), (3, true, 'Hent bil','user');");
                stmt.execute("ALTER TABLE `item` ENABLE KEYS");
            }
        }
        catch (SQLException throwables)
        {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
    }

    @Test
    void testConnection() throws SQLException
    {
        Connection connection = connectionPool.getConnection();
        assertNotNull(connection);
        if (connection != null)
        {
            connection.close();
        }
    }

    @Test
    void getItems()
    {
        List<Item> actualItems = ItemFacade.getItems(connectionPool);
        assertEquals(3, actualItems.size());
        assertThat(actualItems, hasItems(i1, i2, i3));
    }

    @Test
    void toggleItem()
    {
        boolean expected = !i1.isDone();
        ItemFacade.toggleItem(i1.getId(), connectionPool);
        boolean actual = ItemFacade.getItemById(i1.getId(), connectionPool).isDone();
        assertEquals(expected, actual);
    }

    @Test
    void getItemById()
    {
        Item expected = i1;
        Item actual = ItemFacade.getItemById(1, connectionPool);
        assertEquals(expected, actual);
    }

    @Test
    void updateItemName()
    {
        String newName = "Købe 10 æg";
        Item expected = new Item(i1.getId(), newName, i1.isDone(), i1.getUsername());
        ItemFacade.updateItemName(i1.getId(), newName, connectionPool);
        Item actual = ItemFacade.getItemById(i1.getId(), connectionPool);
        assertEquals(expected, actual);
    }


    @Test
    void addItem() throws DatabaseException
    {
        String newName = "Købe 10 æg";
        Item expected = new Item(4, newName, false, "user");
        int newItemId = ItemFacade.addItem(newName, "user", connectionPool);
        Item actual = ItemFacade.getItemById(newItemId, connectionPool);
        assertEquals(expected, actual);
    }

}