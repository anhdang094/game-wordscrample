package models;

import javax.persistence.*;

import com.avaje.ebean.*;
import play.data.validation.*;

import java.util.List;


/**
 * Computer entity managed by Ebean
 */
@Entity
public class Account extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    public  Long id;

    @Constraints.Required
    public  String username;

    @Constraints.Required
    public   String password;

    public  String company;

    public  Integer age;

    public  String address;


    public Account(final String username, final String password, final String company, final Integer age, final String address) {
        this.username = username;
        this.password = password;
        this.company = company;
        this.age = age;
        this.address = address;
    }

    public Account() {
    }

//    public Long getUserId() {
//        return this.id;
//    }
//
//    public  String getUserName() {
//        return this.username;
//    }
//    public  String getUserPass() {
//        return this.password;
//    }
//
//    public  Integer getAge() {
//        return this.age;
//    }
//
//    public  String getCompany() {
//        return this.company;
//    }
//
//    public  String getAddress() {
//        return this.address;
//    }






    /**
     * Generic query helper for entity Computer with id Long
     */
    public static  Finder<Long,Account> find = new Finder<Long,Account>(Long.class, Account.class);

    public static List<Account> findAll(){
        return find.all();
    }

    public static Account findByUsername(String username){
        return find.where().eq("username", username).findUnique();
    }

}

