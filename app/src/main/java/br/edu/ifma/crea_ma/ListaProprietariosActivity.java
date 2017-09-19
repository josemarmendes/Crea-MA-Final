package br.edu.ifma.crea_ma;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.edu.ifma.crea_ma.adapter.ProprietariosAdapter;
import br.edu.ifma.crea_ma.dao.ProprietarioDAO;
import br.edu.ifma.crea_ma.modelo.Proprietario;

public class ListaProprietariosActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listaProprietarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_proprietarios);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Proprietários");
        setSupportActionBar(toolbar);

        listaProprietarios = (ListView) findViewById(R.id.lista_proprietarios);

        listaProprietarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int posicao, long id) {
                Proprietario proprietario = (Proprietario) listaProprietarios.getItemAtPosition(posicao);
                Intent intentVaiProFormulario = new Intent(ListaProprietariosActivity.this, FormularioProprietariosActivity.class);
                intentVaiProFormulario.putExtra("proprietario", proprietario);
                startActivity(intentVaiProFormulario);
            }
        });

        Button novoProprietario = (Button) findViewById(R.id.btnIncluirProprietario);
        novoProprietario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProFormulario = new Intent(ListaProprietariosActivity.this, FormularioProprietariosActivity.class);
                startActivity(intentVaiProFormulario);

            }
        });

        listaProprietarios.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> lista, View item, int posicao, long id) {
                return false;
            }
        });

        registerForContextMenu(listaProprietarios);
    }

    private void carregaListaProprietario() {
        ProprietarioDAO dao = new ProprietarioDAO(this);
        List<Proprietario> listaDeProprietarios = dao.buscaProprietarios();
        dao.close();

        ProprietariosAdapter adapter = new ProprietariosAdapter(this, listaDeProprietarios);
        listaProprietarios.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaListaProprietario();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Proprietario proprietario = (Proprietario) listaProprietarios.getItemAtPosition(info.position);

        MenuItem itemLigar = menu.add("Ligar");
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(ActivityCompat.checkSelfPermission(ListaProprietariosActivity.this, android.Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ListaProprietariosActivity.this,
                            new String[]{android.Manifest.permission.CALL_PHONE}, 123);
                }else{
                    Intent intentLigar = new Intent(Intent.ACTION_VIEW);
                    intentLigar.setData(Uri.parse("tel:" + proprietario.getTelefone()));
                    startActivity(intentLigar);
                }

                return false;
            }
        });

        MenuItem itemMapa = menu.add("Visualizar Mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + proprietario.getEndereco()));
        itemMapa.setIntent(intentMapa);

        MenuItem deletar = menu.add("Remover");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {


                ProprietarioDAO dao = new ProprietarioDAO(ListaProprietariosActivity.this);
                dao.remove(proprietario);
                dao.close();

                carregaListaProprietario();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_proprietario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_lista_notificacao:
                startActivity( new Intent(ListaProprietariosActivity.this, ListaNotificacoesActivity.class) );
                finish();
                break;
            case R.id.menu_cadastro_notificacao:
                startActivity( new Intent(ListaProprietariosActivity.this, FormularioNotificacoesActivity.class) );
                finish();
                break;
            case R.id.menu_site_crea:
                Intent intentSite = new Intent(Intent.ACTION_VIEW);
                intentSite.setData(Uri.parse("http://www.creama.org.br/new/"));
                startActivity(intentSite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
