package br.pro.ednilsonrossi.meupocket.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.pro.ednilsonrossi.meupocket.R;

public class AddSiteActivity extends AppCompatActivity {

    private EditText novoSite;
    private EditText endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);

        novoSite = findViewById(R.id.edittext_novo_site);
        endereco = findViewById(R.id.edittext_endereco);

        findViewById(R.id.button_salvar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !novoSite.getText().toString().isEmpty() && !endereco.getText().toString().isEmpty()){
                    adicionarSite();
                }else{
                    Toast.makeText(getApplicationContext(), "digite os valores", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void adicionarSite() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("titulo", novoSite.getText().toString());
        returnIntent.putExtra("endereco", endereco.getText().toString());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}
