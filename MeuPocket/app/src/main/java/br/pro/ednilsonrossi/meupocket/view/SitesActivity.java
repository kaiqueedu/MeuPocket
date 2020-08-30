package br.pro.ednilsonrossi.meupocket.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.pro.ednilsonrossi.meupocket.R;
import br.pro.ednilsonrossi.meupocket.model.Site;

public class SitesActivity extends AppCompatActivity {

    private static final String SHARED_LIST = "List";
    private RecyclerView sitesRecyclerView;
    private List<Site> siteList;
    private ItemSiteAdapter siteAdapter;
    private SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sites);


        sitesRecyclerView = findViewById(R.id.recycler_lista_sites);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        sitesRecyclerView.setLayoutManager(layoutManager);

        siteList = recuperateAll();

        siteAdapter = new ItemSiteAdapter(siteList);

        sitesRecyclerView.setAdapter(siteAdapter);

        siteAdapter.setClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String url = corrigeEndereco(siteList.get(position).getEndereco());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    private ArrayList<Site> recuperateAll() {
        shared = getPreferences(MODE_PRIVATE);
        String jsonList = shared.getString(SHARED_LIST, "");
        if (jsonList.isEmpty()) {
            return new ArrayList<>();
        }
        return new Gson().fromJson(jsonList, new TypeToken<ArrayList<Site>>() {
        }.getType());

    }

    private void saveList() {
        shared.edit()
                .putString(SHARED_LIST, new Gson().toJson(siteList))
                .apply();

    }

    private String corrigeEndereco(String endereco) {
        String url = endereco.trim().replace(" ", "");
        if (!url.startsWith("http://")) {
            return "http://" + url;
        }
        return url;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_mypocket, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_adicionar:
                //chama activity
                Intent intent = new Intent(this, AddSiteActivity.class);
                startActivityForResult(intent, 0);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            String titulo = Objects.requireNonNull(data.getExtras()).getString("titulo");
            String endereco = Objects.requireNonNull(data.getExtras()).getString("endereco");
            siteList.add(new Site(titulo, endereco));
            saveList();
            siteAdapter.notifyDataSetChanged();
        }
    }
}
