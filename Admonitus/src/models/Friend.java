package models;

import java.io.Serializable;

import javax.persistence.*;

import flexjson.JSON;
import models.Reminder;

/**
 * The persistent class for the friends database table.
 * 
 */
@Entity
@Table(name = "friends")
@NamedQuery(name = "Friend.findAll", query = "SELECT f FROM Friend f")
public class Friend implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;

    // bi-directional many-to-one association to Reminder
    @ManyToOne
    private Reminder reminder;

    public Friend()
    {
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @JSON(include=false)
    public Reminder getReminder()
    {
        return this.reminder;
    }

    public void setReminder(Reminder reminder)
    {
        this.reminder = reminder;
    }

}