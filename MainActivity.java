package com.example.moree.mytvapp1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.example.moree.mytvapp1.MyFavorite.Favorite;
import com.example.moree.mytvapp1.MyFavorite.FavoriteData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import weborb.client.Fault;

import static android.R.attr.password;

//our main activity have the login and register methods
public class MainActivity extends AppCompatActivity {
    private Context context;
    //edit text to input Email and password to login
    private EditText userId, usePass;
    //button login when press to check if login is okay
    //button register to open new alert dailog to input your Email and pass and register
    private Button Login, register;
    //we need to call Fragmentcontainer to use it later
    private Fragmentcontainer fragmentcontainer;
    //layout are apart of our edittext
    private TextInputLayout layout_Email, layout_Pass;
    //we need to call Favorite to use it later on
     private Favorite favorite;
     //utlshared is shared preferences that we use to our log in
    private utlShared ut;
    //logged is a boolean that we use it to save true or false to shared preferences
    private boolean logged;
    private MainActivity activity;

     public void  setLogged(Boolean log)
     {
         this.logged=log;
     }
     public Boolean loged()
     {
         return logged;
     }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //our keys for backendless
        Backendless.initApp(this, "B69DDA46-E458-378B-FF0E-F5F182F4A800", "B506595D-64F7-320B-FF90-227125992900", "v1");
        //we call our main method that contains our called variables
        setPointer();
    }



    private void setPointer() {
        this.context = this;
        //we call new favorites to use its variables
        favorite = new Favorite();
        //we call new utlshared to use it
        ut = new utlShared(context);
        //we set logged as false since logged is boolean and utlshared has a method called getbol(Boolean logged)
        logged = ut.getBol(false);
        //we set a pointer our EditTexts and Bottons and TextInputLayout from our Xml in activity_main
        userId = (EditText) findViewById(R.id.userName);
        usePass = (EditText) findViewById(R.id.usePass);
        Login = (Button) findViewById(R.id.btnLogin);
        register = (Button) findViewById(R.id.InRegister);
        layout_Email = (TextInputLayout) findViewById(R.id.Input_Email);
        layout_Pass = (TextInputLayout) findViewById(R.id.Input_pass);
        //we call new fragmentcointer so we can use its data
        fragmentcontainer = new Fragmentcontainer();
        //set on click for  login which reads from Login();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        //set on click for register which goes to Register();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
        //method that checks if logged is true or false for out login in
       // logi();
    }



    public void logi() {
        //if logged is true it shows on the screen log is true also goes to staylogged();
        if (logged == true) {
            StayLogged();
            Toast.makeText(context, "log is true ", Toast.LENGTH_SHORT).show();
        } else {
            //if logged is false then it stays on the login screen and it wont go o the next screen till you login
            Toast.makeText(context, "log is false ", Toast.LENGTH_SHORT).show();
        }
    }


    //our method that checks if the last user did logged in and logged is true and to goes to the next screen
    protected void StayLogged() {
//checks if last user was online
        AsyncCallback<Boolean> checkuser = new AsyncCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean response) {
                Toast.makeText(context, "User Logged", Toast.LENGTH_SHORT).show();
                Intent First = new Intent(context, Fragmentcontainer.class);
                startActivity(First);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(context, "Faild to log in ", Toast.LENGTH_SHORT).show();
            }
        };
        //this get the result from checkuser if true or false
        Backendless.UserService.isValidLogin(checkuser);
    }

    //method that check login
    private void Login() {
        //String Email to get from Edit Text
        final String Email = userId.getText().toString();
        //String Password to get text to string from edit text use pass
        final String Pass = usePass.getText().toString();
///check if if not input to our Edit text Email and Pass
        if (Email.isEmpty()) {
            //layout that show error its its empty
            layout_Email.setErrorTextAppearance(R.style.error_appearance);
            layout_Email.setError("Enter you Email");

        } else {
            //if is not empty error goes away
            layout_Email.setError(null);
        }
        //check if pass its empty and shows error with layout pass
        if (Pass.isEmpty()) {
            layout_Pass.setErrorTextAppearance(R.style.error_appearance);
            //set text for our error with it happens
            layout_Pass.setError("Enter Passord");
            return;
        }
        if (usePass.getText().toString().length() < 6) {
            layout_Pass.setErrorTextAppearance(R.style.error_appearance);
            layout_Pass.setError("Error password");
            return;
        } else {
            layout_Pass.setError(null);
        }
        try {
//try to login  with Email and pass
            Backendless.UserService.login(Email, Pass, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser response) {
                    //get owner id  for the email that has logged
                    String a = Backendless.UserService.CurrentUser().getUserId();
                    //save key with shared preferences incase user logged in automaticaly
                    ut.putId(a);

                    Toast.makeText(context, "myId: " + a, Toast.LENGTH_SHORT).show();
                    //if Email and pass are ok then it goies to our next screen
                    //our distenation to Fragmentcontainer
                    Intent First = new Intent(context, Fragmentcontainer.class);
                    //with startactivity goes to the next screen
                    startActivity(First);
                    //looged is set true so next time he opens our app it know he did logged in and didnt log out
                    logged = ut.putBol(true);
                    Toast.makeText(context, "logged: " + ut.putBol(true), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void handleFault(BackendlessFault fault) {
//if email pass is wrong it wont log in and if wifi is not turned on it will say network error
                    if (fault.getCode().equals("3003")) {
                        Toast.makeText(context, "Error E-mail or Password", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Network Error ", Toast.LENGTH_SHORT).show();
                    }
                }
                //when it logged in it set that the Email has logged is true means it will log in auto
            }, logged = ut.getBol(true));
        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    ///alert dialog to register our email and pasword
    private void Register() {

        final AlertDialog myRegister = new AlertDialog.Builder(context).create();
        //final AlertDialog optionAlert =myRegister.create();
        //inflate view from xml =layout to view our alert dialog
        View inflateRegister = LayoutInflater.from(context).inflate(R.layout.register, null, false);
        //dialog wont dissmiss even if you press outside it
        //alret dialog wont go away if you press anywhere only if you press cancel
        myRegister.setCanceledOnTouchOutside(false);
        //layout for Email pass1 and pass2
        final TextInputLayout Email = (TextInputLayout) inflateRegister.findViewById(R.id.Email);
        final TextInputLayout pass1 = (TextInputLayout) inflateRegister.findViewById(R.id.layout_Pass1);
        final TextInputLayout pass2 = (TextInputLayout) inflateRegister.findViewById(R.id.layout_Pass2);
        //Edit text to input Email and pass and to enter pass again to check
        final EditText username = (EditText) inflateRegister.findViewById(R.id.RegisterName);
        final EditText userpass1 = (EditText) inflateRegister.findViewById(R.id.userPass1);
        final EditText userpass2 = (EditText) inflateRegister.findViewById(R.id.userPass2);
        //userpass1.setTransformationMethod(new PasswordTransformationMethod());
        //button to register and button to cancel incase you dont want to register
        Button btnRegister = (Button) inflateRegister.findViewById(R.id.btnRegister);
        Button btnCancel = (Button) inflateRegister.findViewById(R.id.btnCancel);
        //set onclick lister for our button cancel and register
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRegister.dismiss();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if input Email is empty it will show error Enter Email
                if (username.getText().toString().isEmpty()) {
                    Email.setErrorTextAppearance(R.style.error_appearance);
                    Email.setError("Enter Email");

                } else {
                    //if not error will go away
                    Email.setError(null);
                }
//if input pass is empty it will show error  with pass1 layout please Enter Password
                if (userpass1.getText().toString().isEmpty()) {
                    pass1.setError("please Enter Password");
                    pass1.setErrorTextAppearance(R.style.error_appearance);
                    return;
                }else
                {//if pass is not empty error will go away
                    pass1.setError(null);
                }
                //if pass text is lower than 6 it will show error
                if (userpass1.getText().toString().length() < 6) {
                    pass1.setError("password 1-6");
                    pass1.setErrorTextAppearance(R.style.error_appearance);
                    return;
                } else {
                    pass1.setError(null);
                }
//if pass1 and pass2 dont match it will show an error password dont match
                if (!userpass1.getText().toString().equals(userpass2.getText().toString())) {
                    pass2.setError("Password dont match");
                    pass2.setErrorTextAppearance(R.style.error_appearance);
                    return;

                }
//if evverying is okay it will register Email and password
                //we call Backendlessuser that is apart with backendless methods
                BackendlessUser RegisterUser = new BackendlessUser();
 // set property is to get name of the colum that we want to input in our  users table
                RegisterUser.setProperty("email", username.getText().toString());
                RegisterUser.setProperty("password", userpass1.getText().toString());
                try {

//then we register
                    Backendless.UserService.register(RegisterUser, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
//if Email is ok and pass
                            Toast.makeText(context, "User Registed", Toast.LENGTH_SHORT).show();
                            myRegister.dismiss();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                         //if email exists
                            if (fault.getCode().equals("3033")) {
                                Email.setErrorTextAppearance(R.style.error_appearance);
                                Email.setError("Email Already Exists");
                            }
//if email format is wrong
                            if (fault.getCode().equals("3040")) {
                                Email.setErrorTextAppearance(R.style.error_appearance);
                                Email.setError("Not Valid Email");
                            }


                        }
                    });
//if there was an error it will show the error
                } catch (Exception e) {
                    Toast.makeText(context, "Please Enter the Following!", Toast.LENGTH_SHORT).show();
                }
            }


        });
//add view  to our alert dialog with the inflate that we made from xml
        myRegister.setView(inflateRegister);
        //we show our alert dialog .show
        myRegister.show();

    }


}
