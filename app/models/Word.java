package models;

import javax.persistence.*;

import com.avaje.ebean.*;
import play.data.validation.*;

import java.util.List;


/**
 * Computer entity managed by Ebean
 */
@Entity
public class Word extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    public  Long id;

    @Constraints.Required
    public  String name;




    public Word(final String name) {
        this.name = name;
    }

    public Word() {
    }




    /**
     * Generic query helper for entity Computer with id Long
     */
    public static  Finder<Long,Word> find = new Finder<Long,Word>(Long.class, Word.class);

    public static List<Word> findAll(){
        return find.all();
    }

    public static Word findById(Integer id){
        return find.where().eq("id", id).findUnique();
    }

    public static Integer findMaxId() { return findAll().size();}

}

