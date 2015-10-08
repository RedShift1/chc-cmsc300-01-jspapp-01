package models;

import java.io.Serializable;

import javax.persistence.*;

import flexjson.JSON;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQueries
(
    {
        @NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
        @NamedQuery(name = "User.findByEmailAndPassword", query = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    }
)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="activation_key")
    private String activationKey;

    private boolean active;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="creation_date")
    private Date creationDate;

    private String email;

    @Column(name="last_update")
    private Timestamp lastUpdate;

    private String password;

    @Column(name="picture_path")
    private String picturePath;
    
    @Lob @Column(name="picture")
    private byte[] picture;

    //bi-directional many-to-one association to Reminder
    @OneToMany(mappedBy="user", fetch=FetchType.LAZY)
    private List<Reminder> reminders;

    public User() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JSON(include=false)
    public String getActivationKey() {
        return this.activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    @JSON(include=false)
    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @JSON(include=false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicturePath() {
        return this.picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
    
    @JSON(include=false)
    public byte[] getPicture()
    {
        return this.picture;
    }
    
    public void setPicture(byte[] picture)
    {
        this.picture = picture;
    }

    @JSON(include=false)
    public List<Reminder> getReminders() {
        return this.reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    public Reminder addReminder(Reminder reminder) {
        getReminders().add(reminder);
        reminder.setUser(this);

        return reminder;
    }

    public Reminder removeReminder(Reminder reminder) {
        getReminders().remove(reminder);

        return reminder;
    }

}