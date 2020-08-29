package br.pro.ednilsonrossi.meupocket.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.pro.ednilsonrossi.meupocket.R;
import br.pro.ednilsonrossi.meupocket.model.Site;

public class AddSiteActivity extends AppCompatActivity {

    private EditText novoSite;
    private EditText endereco;
    private Button botalSalvar;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private Site site;
    private List<Site> listSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);

        novoSite = findViewById(R.id.edittext_novo_site);
        endereco = findViewById(R.id.edittext_endereco);
        botalSalvar = findViewById(R.id.button_salvar);

        //mSharedPreferences = this.getSharedPreferences(getString(R.string.file_usuario), MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        adicionarBD();
    }

    private void adicionarBD() {
    }
}
