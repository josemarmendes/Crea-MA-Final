package br.edu.ifma.crea_ma;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.edu.ifma.crea_ma.adapter.NotificacoesAdapter;
import br.edu.ifma.crea_ma.dao.NotificacaoDAO;
import br.edu.ifma.crea_ma.modelo.Notificacao;

public class ListaNotificacoesActivity extends AppCompatActivity {

    private ListView listaNotificacoes;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notificacoes);

        listaNotificacoes = (ListView) findViewById(R.id.lista_infracoes);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Notificações");
        setSupportActionBar(toolbar);

        listaNotificacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int posicao, long id) {
                Notificacao notificacao = (Notificacao) listaNotificacoes.getItemAtPosition(posicao);
                Intent intentVaiProFormulario = new Intent(ListaNotificacoesActivity.this, FormularioNotificacoesActivity.class);
                intentVaiProFormulario.putExtra("notificacao", notificacao);
                startActivity(intentVaiProFormulario);
            }
        });

        Button novaNotificacao = (Button) findViewById(R.id.btnIncluirInfracao);
        novaNotificacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProFormulario = new Intent(ListaNotificacoesActivity.this, FormularioNotificacoesActivity.class);
                startActivity(intentVaiProFormulario);

            }
        });

        listaNotificacoes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> lista, View item, int posicao, long id) {
                return false;
            }
        });

        registerForContextMenu(listaNotificacoes);
     }

    private void carregaListaInfracao() {
        NotificacaoDAO dao = new NotificacaoDAO(this);
        List<Notificacao> listaDeNotificacoes = dao.buscaNotificacoes();
        dao.close();

        NotificacoesAdapter adapter = new NotificacoesAdapter(this, listaDeNotificacoes);
        if(adapter.isEmpty()){
            Toast.makeText(ListaNotificacoesActivity.this, "Adapter da lista vazio", Toast.LENGTH_SHORT).show();
        }
        listaNotificacoes.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaListaInfracao();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Notificacao notificacao = (Notificacao) listaNotificacoes.getItemAtPosition(info.position);

        MenuItem itemSite = menu.add("Visitar site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        intentSite.setData(Uri.parse("http://www.creama.org.br/new/"));
        itemSite.setIntent(intentSite);

        MenuItem itemMapa = menu.add("Visualizar Mapa da Infração");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + notificacao.getEnderecoNotificacao()));
        itemMapa.setIntent(intentMapa);

        MenuItem deletar = menu.add("Remover");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {


                NotificacaoDAO dao = new NotificacaoDAO(ListaNotificacoesActivity.this);
                dao.remove(notificacao);
                dao.close();

                carregaListaInfracao();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_notificacao, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_lista_proprietarios:
                startActivity( new Intent(ListaNotificacoesActivity.this, ListaProprietariosActivity.class) );
                break;
            case R.id.menu_cadastro_proprietario:
                startActivity( new Intent(ListaNotificacoesActivity.this, FormularioProprietariosActivity.class) );
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
