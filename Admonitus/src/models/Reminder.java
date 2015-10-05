package models;

import java.io.Serializable;

import javax.persistence.*;

import models.Friend;

import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

/**
 * The persistent class for the reminders database table.
 * 
 */
@Entity
@Table(name = "reminders")
@NamedQuery(name = "Reminder.findAll", query = "SELECT r FROM Reminder r")
public class Reminder implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date")
    private Date creationDate;

    @Temporal(TemporalType.DATE)
    private Date datestart;

    private byte frequency;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

    @Lob
    private String text;

    // bi-directional many-to-one association to User
    @ManyToOne
    private User user;

    // bi-directional many-to-one association to Friend
    @OneToMany(mappedBy = "reminder")
    private List<Friend> friends;

    public Reminder()
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

    public Date getCreationDate()
    {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }

    public Date getDatestart()
    {
        return this.datestart;
    }

    public void setDatestart(Date datestart)
    {
        this.datestart = datestart;
    }

    public byte getFrequency()
    {
        return this.frequency;
    }

    public void setFrequency(byte frequency)
    {
        this.frequency = frequency;
    }

    public Timestamp getLastUpdate()
    {
        return this.lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate)
    {
        this.lastUpdate = lastUpdate;
    }

    public String getText()
    {
        return this.text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public User getUser()
    {
        return this.user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public List<Friend> getFriends()
    {
        return this.friends;
    }

    public void setFriends(List<Friend> friends)
    {
        this.friends = friends;
    }

    public Friend addFriend(Friend friend)
    {
        getFriends().add(friend);
        friend.setReminder(this);

        return friend;
    }

    public Friend removeFriend(Friend friend)
    {
        getFriends().remove(friend);

        return friend;
    }

    @Override
    public String toString()
    {
        return text;

    }

}