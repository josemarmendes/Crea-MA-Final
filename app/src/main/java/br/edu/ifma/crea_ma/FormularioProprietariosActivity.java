package br.edu.ifma.crea_ma;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import br.edu.ifma.crea_ma.dao.ProprietarioDAO;
import br.edu.ifma.crea_ma.modelo.Proprietario;

public class FormularioProprietariosActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FormularioHelperProprietario helperProprietario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_proprietarios);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Form proprietário");
        setSupportActionBar(toolbar);

        helperProprietario = new FormularioHelperProprietario(this);

        Intent intent = getIntent();
        Proprietario proprietario = (Proprietario) intent.getSerializableExtra("proprietario");
        if(proprietario != null){
            helperProprietario.preencheFormularioProprietario(proprietario);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario_proprietario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_proprietario_ok:
                salvarProprietario();
                break;
            case R.id.menu_lista_notificacao:
                startActivity( new Intent(FormularioProprietariosActivity.this, ListaNotificacoesActivity.class) );
                finish();
                break;
            case R.id.menu_cadastro_notificacao:
                startActivity( new Intent(FormularioProprietariosActivity.this, FormularioNotificacoesActivity.class) );
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

    private void salvarProprietario(){
        if(helperProprietario.pegaProprietario() == null){
            Toast.makeText(FormularioProprietariosActivity.this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
        }else {
            Proprietario proprietario = helperProprietario.pegaProprietario();
            ProprietarioDAO dao = new ProprietarioDAO(this);

            if(proprietario.getIdProprietario() != null){
                dao.altera(proprietario);
            } else {
                dao.insere(proprietario);
            }
            dao.close();

            Toast.makeText(FormularioProprietariosActivity.this, "Proprietário " + proprietario.getNome() + " salvo com sucesso", Toast.LENGTH_SHORT).show();

            finish();
        }
    }
}
