/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity {

    public void redirectUser(){
        if(ParseUser.getCurrentUser() != null){
            Intent intent= new Intent(getApplicationContext(),UsersActivity.class);
            startActivity(intent);
        }
    }

  public void signupLogin(View view){
      final EditText usernameEditText= findViewById(R.id.usernameEditText);
      final EditText passwordEdittext=findViewById(R.id.passwordEditText);

      ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEdittext.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {
              if(e==null){
                  Log.i("Login","Success!");
                  redirectUser();
              }else{
                  ParseUser newUser=new ParseUser();
                  newUser.setUsername(usernameEditText.getText().toString());
                  newUser.setPassword(passwordEdittext.getText().toString());
                  newUser.signUpInBackground(new SignUpCallback() {
                      @Override
                      public void done(ParseException e) {
                          if(e==null){
                              Log.i("Sign Up","Success!");
                              redirectUser();
                          }else{
                              Toast.makeText(MainActivity.this,e.getMessage().substring(e.getMessage().indexOf(" ")),Toast.LENGTH_SHORT).show();
                          }
                      }
                  });
              }
          }
      });
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setTitle("Miniblogger: Login");
      redirectUser();
    
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}