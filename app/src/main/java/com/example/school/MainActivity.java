package com.example.school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import static com.example.school.StudentDatabase.COLLUM_USER;
import static com.example.school.StudentDatabase.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    StudentDatabase studentDatabase;

    SQLiteDatabase sqLiteDatabase;
    EditText user,pwd;
    Button login,singup,fb_signin;
    CheckBox remeber;
    SharedPreferences sprf;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.user);
        pwd = findViewById(R.id.pwd);
        remeber = findViewById(R.id.remeber);
        login = findViewById(R.id.login);
        singup = findViewById(R.id.signup);
        fb_signin = findViewById(R.id.signin_facebook);
        sprf = getSharedPreferences("Save User Login",MODE_PRIVATE);
        editor = sprf.edit();


        studentDatabase = new StudentDatabase(getApplicationContext());

        String user_namee = sprf.getString("User","");
        String user_pass = sprf.getString("Password","");

        if(user_namee!="" && user_pass!="")
        {
           /* Intent intent = new Intent(getApplicationContext(),StudentDatabase.class);
            startActivity(intent);*/

           user.setText(sprf.getString("User",""));
           pwd.setText(sprf.getString("Password",""));
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user_name = user.getText().toString();
                String password =  pwd.getText().toString();

                if(user_name=="" || password=="")
                    Toast.makeText(getApplicationContext(), "Please enter email or password", Toast.LENGTH_SHORT).show();
                else {
                    try {
                        sqLiteDatabase = studentDatabase.getWritableDatabase();
                        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + StudentDatabase.TABLE_NAME + " where "
                                        + StudentDatabase.COLLUM_USER + " ='" + user.getText().toString() + "' "
                                        + " and " + StudentDatabase.COLLUM_PWD + " = '" + pwd.getText().toString() + "' ",
                                null);

                        if (cursor != null && cursor.moveToNext()) {

                            Toast.makeText(getApplicationContext(), cursor.getString(cursor.getColumnIndex(COLLUM_USER)), Toast.LENGTH_SHORT).show();
                            cursor.close();

                            /*To save username and password permanently*/

                            /*if(remeber.isChecked())
                            {
                                editor.putString("User",user.getText().toString());
                                editor.putString("Paswword",pwd.getText().toString());
                                Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                                editor.commit();


                            }
                            else
                            {
                                editor.clear();
                                editor.remove("User");
                                editor.remove("Password");
                                editor.commit();
                                Toast.makeText(MainActivity.this, "Remove", Toast.LENGTH_SHORT).show();
                            }
*/


                            if(remeber.isChecked()) {
                                editor.putString("User", user.getText().toString());
                                editor.putString("Paswword", pwd.getText().toString());
                                Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                                editor.commit();
                            }
                            else
                            {
                                editor.clear();
                                editor.remove("User");
                                editor.remove("Password");
                                Toast.makeText(MainActivity.this, "Removed", Toast.LENGTH_SHORT).show();
                                editor.commit();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(),"Incorrect Email or Password",Toast.LENGTH_SHORT).show();

                        }



/*

                    ContentValues values = new ContentValues();
                    values.put(StudentDatabase.COLLUM_USER,user.getText().toString());
                    values.put(StudentDatabase.COLLUM_PWD,pwd.getText().toString());
                    sqLiteDatabase.insert(StudentDatabase.TABLE_NAME,null,values);
                    sqLiteDatabase.close();
                    Toast.makeText(getApplicationContext(),"Data insert Sucessfully...",Toast.LENGTH_SHORT).show();

*/
                    } catch (CursorIndexOutOfBoundsException e) {
                        Log.e("Information", e.toString());
                        // Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();

                    } catch (SQLiteException e) {
                        Log.e("Information", e.toString());
                        //              Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();

                    }
                }
            }
        });

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),StudentForm.class);
                startActivity(intent);
            }
        });
    }
}
