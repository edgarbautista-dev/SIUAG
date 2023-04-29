package com.example.siuag;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.siuag.entity.User;
import com.example.siuag.service.RegistrationService;

public class NewUserActivity extends AppCompatActivity {
    private EditText Usuario;
    private EditText Password;
    private EditText Nombre;
    private EditText Apellido;
    private Spinner TipoUsuario;
    private TextView LblMensaje;
    private TextView ErrorMsg;
    private Button btnRegistrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        Usuario=(EditText) findViewById(R.id.editTextUsername);
        Password=(EditText) findViewById(R.id.editTextPassword);
        Nombre=(EditText) findViewById(R.id.editTextName);
        Apellido=(EditText) findViewById(R.id.editTextLastName);
        LblMensaje=(TextView) findViewById(R.id.textView);
        btnRegistrar=(Button) findViewById(R.id.buttonRegister);
        ErrorMsg=(TextView) findViewById(R.id.error_messages);

        final String[] tiposUsuario = new String[]{"-", "Administrador", "Usuario"};
        TipoUsuario=(Spinner) findViewById(R.id.tipoUsuario);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposUsuario);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TipoUsuario.setAdapter(adapter);
        TipoUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Has seleccionado: " + tiposUsuario[position]);
                TipoUsuario.setSelection(position);
                System.out.println(TipoUsuario.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ErrorMsg.setText("");
            }
        });


        btnRegistrar.setOnClickListener(v -> {
            String usuario = Usuario.getText().toString();
            String password = Password.getText().toString();
            String nombre = Nombre.getText().toString();
            String apellido = Apellido.getText().toString();
            String tipoUsuario = TipoUsuario.getSelectedItem().toString();
            if (usuario.isEmpty() || usuario.equals("")) {
                ErrorMsg.setText("Ingresa un nombre de usuario");
            }else if (password.isEmpty() || password.equals("")) {
                ErrorMsg.setText("Ingresa una contraseña");
            }else if ( nombre.isEmpty() || nombre.equals("")) {
                ErrorMsg.setText("Ingresa un nombre");
            }else if ( apellido.isEmpty() || apellido.equals("")) {
                ErrorMsg.setText("Ingresa un apellido");
            }else if ( tipoUsuario.isEmpty() || tipoUsuario.equals("") || tipoUsuario.equals("-")) {
                ErrorMsg.setText("Selecciona un tipo de usuario");
            } else {
                User user = new User();
                user.setUsername(usuario);
                user.setPassword(password);
                user.setName(nombre);
                user.setLastname(apellido);
                user.setRole(tipoUsuario);

                long userID = RegistrationService.createUser(user, null, this);
                if(userID > 0){
                    Toast.makeText(NewUserActivity.this, "Usuario agregado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewUserActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(NewUserActivity.this, "Error al crear el usuario, intenta mas tarde", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}