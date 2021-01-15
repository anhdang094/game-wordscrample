package controllers;


import org.mindrot.jbcrypt.BCrypt;
import play.api.libs.concurrent.Promise;
import play.api.libs.ws.WSRequestFilter;
import play.libs.F;
import play.mvc.*;
import play.data.*;

import views.html.*;

import models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import play.libs.ws.WS;

import play.*;

import javax.xml.ws.Response;


/**
 * Manage a database of computers
 */
public class Application extends Controller {
    
    /**
     * This result directly redirect to application home.
     */



    public Result GO_HOME = redirect(
        routes.Application.list(0, "name", "asc", "")
    );

    
    /**
     * Handle default path requests, redirect to computers list
     */
    public Result index() {
        return ok(game.render());

    }

    public Result start() {
        int number_random = 1 + (int)(Math.random() * (Word.findMaxId()));
        return ok(
                start.render(Word.findById(number_random))
        );
    }
    public Result score() {
        return login();
    }
    public Result moregame() {
        return login();
    }



    public Result login() {
        Form<Account> userForm = Form.form(Account.class);
        return ok(
                login.render(userForm)
        );
    }

    public Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.Application.login()
        );
    }
    @Security.Authenticated(Secured.class)
    public Result infor() {

        return ok(
                Infor.render(
                        Account.findByUsername(request().username())
                )
        );
    }

    public Result authenticate(){
        Form<Account> userForm = Form.form(Account.class).bindFromRequest();
        if(userForm.hasErrors()) {
            return badRequest(login.render(userForm));
        }
        else {
            session().clear();
            session("username", userForm.get().username);

            if((Account.findByUsername(userForm.get().username) == null)) {
                userForm.reject("username", "error");
                flash("error", "Please check error");
            }
            else if (userForm.get().username.equals(Account.findByUsername(userForm.get().username).username)){
                if (BCrypt.checkpw(userForm.get().password, Account.findByUsername(userForm.get().username).password))
                    return GO_HOME;
                else {
                    userForm.reject("password", "error");
                    flash("error", "Please check error");
                }
            }

            else {
                userForm.reject("username", "error");
                flash("error", "Please check error");
            }
            return badRequest(login.render(userForm));

        }

    }

    public Result signup(){
        Form<Account> accountForm = Form.form(Account.class);
        return ok(
                SignUp.render(accountForm)
        );
    }



    public Result register() {
        Form<Account> accountForm = Form.form(Account.class).bindFromRequest();
        if(accountForm.hasErrors()) {
            return badRequest(SignUp.render(accountForm));
        }
        else if ((accountForm.field("confirmPassword").valueOr("").isEmpty()) || (!(accountForm.field("password").value().equals(accountForm.field("confirmPassword").value())))){
            accountForm.reject("confirmPassword","error");
            flash("error", "Please check error");
            return badRequest(SignUp.render(accountForm));

            }
        else {
                accountForm.get().password = BCrypt.hashpw(accountForm.get().password, BCrypt.gensalt());
                accountForm.get().save();
                flash("success", "Register success");
                return redirect(routes.Application.login());
        }

        }




    /**
     * Display the paginated list of computers.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filter Filter applied on computer names
     */

    @Security.Authenticated(Secured.class)
    public Result list(int page, String sortBy, String order, String filter) {
        return ok(
            list.render(
                Computer.page(page, 10, sortBy, order, filter),
                sortBy, order, filter,
                Account.findByUsername(request().username())
            )
        );
    }
    
    /**
     * Display the 'edit form' of a existing Computer.
     *
     * @param id Id of the computer to edit
     */
    @Security.Authenticated(Secured.class)
    public Result edit(Long id) {
        Form<Computer> computerForm = Form.form(Computer.class).fill(
            Computer.find.byId(id)
        );
        return ok(
            editForm.render(id, computerForm)
        );
    }
    
    /**
     * Handle the 'edit form' submission 
     *
     * @param id Id of the computer to edit
     */
    @Security.Authenticated(Secured.class)
    public Result update(Long id) {
        Form<Computer> computerForm = Form.form(Computer.class).bindFromRequest();
        if(computerForm.hasErrors()) {
            return badRequest(editForm.render(id, computerForm));
        }
        computerForm.get().update();
        flash("success", "Computer " + computerForm.get().name + " has been updated");
        return GO_HOME;
    }
    
    /**
     * Display the 'new computer form'.
     */
    @Security.Authenticated(Secured.class)
    public Result create() {
        Form<Computer> computerForm = Form.form(Computer.class);
        return ok(
            createForm.render(computerForm)
        );
    }
    
    /**
     * Handle the 'new computer form' submission 
     */
    @Security.Authenticated(Secured.class)
    public Result save() {
        Form<Computer> computerForm = Form.form(Computer.class).bindFromRequest();
        if(computerForm.hasErrors()) {
            return badRequest(createForm.render(computerForm));
        }
        computerForm.get().save();
        //flash("success", "Computer " + computerForm.get().name + " has been created");
        return GO_HOME;
    }
    
    /**
     * Handle computer deletion
     */
    @Security.Authenticated(Secured.class)
    public Result delete(Long id) {
        Computer.find.ref(id).delete();
        flash("success", "Computer has been deleted");
        return GO_HOME;
    }







}
            
