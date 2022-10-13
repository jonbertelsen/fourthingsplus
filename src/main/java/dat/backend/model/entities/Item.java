package dat.backend.model.entities;

import java.sql.Timestamp;
import java.util.Objects;

public class Item
{
    private int id;
    private String name;
    private boolean done;
    private Timestamp created;
    private String username;

    public Item(int id, String name, boolean done, Timestamp created, String username)
    {
        this.id = id;
        this.name = name;
        this.done = done;
        this.created = created;
        this.username = username;
    }

    public Item(int id, String name, boolean done, String username)
    {
        this.id = id;
        this.name = name;
        this.done = done;
        this.username = username;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public boolean isDone()
    {
        return done;
    }

    public Timestamp getCreated()
    {
        return created;
    }

    public String getUsername()
    {
        return username;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return getId() == item.getId() && isDone() == item.isDone() && Objects.equals(getName(), item.getName()) && Objects.equals(getUsername(), item.getUsername());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId(), getName(), isDone(), getUsername());
    }
}
