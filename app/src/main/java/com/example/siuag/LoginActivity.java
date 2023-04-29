package com.example.siuag;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.siuag.service.AuthenticationService;

public class LoginActivity extends AppCompatActivity {
    private TextView LblMensaje;
    private EditText username;
    private EditText editTextPassword;
    private TextView OlvidoContrasena;
    private TextView Registrarse;

    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LblMensaje=(TextView) findViewById(R.id.mensajeBienvenida);
        username = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        OlvidoContrasena = findViewById(R.id.mensajeOlvidoContrasena);
        Registrarse = findViewById(R.id.registrateAqui);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes agregar la lógica de autenticación
                String user = username.getText().toString();
                String password = editTextPassword.getText().toString();
                if (AuthenticationService.isValidUser(LoginActivity.this, user, password)) {
                    // Autenticación exitosa
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // Autenticación fallida
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }

            }
        });
        OlvidoContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });
        Registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, NewUserActivity.class);
                startActivity(intent);

            }
        });
    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean isValidPassword(String password) {
        // Aquí puedes agregar tus propios criterios de validación para la contraseña
        return password.length() > 4;
    }

}