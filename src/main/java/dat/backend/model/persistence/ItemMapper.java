package dat.backend.model.persistence;

import dat.backend.model.entities.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class ItemMapper
{
    static List<Item> getItems(dat.backend.model.persistence.ConnectionPool connectionPool)
    {
        List<Item> itemList = new ArrayList<>();

        String sql = "select * from item";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    int id = rs.getInt("item_id");
                    String name = rs.getString("name");
                    boolean done = rs.getBoolean("done");
                    Timestamp created = rs.getTimestamp("created");
                    String username = rs.getString("username");

                    Item newItem = new Item(id, name, done, created, username);
                    itemList.add(newItem);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return itemList;
    }

    public static void toggleItem(int item_id, ConnectionPool connectionPool)
    {
        String sql = "UPDATE item SET done = (1 - done) WHERE item_id = ?";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, item_id);
                ps.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    public static Item getItemById(int item_id, ConnectionPool connectionPool)
    {
        String sql = "select * from item where item_id = ?";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, item_id);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                {
                    int id = rs.getInt("item_id");
                    String name = rs.getString("name");
                    boolean done = rs.getBoolean("done");
                    Timestamp created = rs.getTimestamp("created");
                    String username = rs.getString("username");

                    Item newItem = new Item(id, name, done, created, username);
                    return newItem;
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateItemName(int item_id, String name, ConnectionPool connectionPool)
    {
        String sql = "UPDATE item SET name = ? WHERE item_id = ?";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, name);
                ps.setInt(2, item_id);
                ps.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static int addItem(String itemName, String username, ConnectionPool connectionPool)
    {
        String sql = "INSERT INTO item (name, username) VALUES (?,?)";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
            {
                ps.setString(1, itemName);
                ps.setString(2, username);
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

}
