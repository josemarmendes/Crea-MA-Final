package br.edu.ifma.crea_ma;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import br.edu.ifma.crea_ma.dao.NotificacaoDAO;
import br.edu.ifma.crea_ma.dao.ProprietarioDAO;
import br.edu.ifma.crea_ma.modelo.Notificacao;
import br.edu.ifma.crea_ma.modelo.Proprietario;

public class FormularioNotificacoesActivity extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 567;
    private Toolbar toolbar;
    private FormularioHelperNotificacao helper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_notificacoes);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Form Notificação");
        setSupportActionBar(toolbar);

        //preenchendo o spinner nome do proprietario com os rgistros do sqlite
        ProprietarioDAO proprietarioDAO = new ProprietarioDAO(this);
        List<String> listProprietario = proprietarioDAO.buscaProprietariosPorNome() ;
        proprietarioDAO.close();

        Spinner spNomeProprietario = (Spinner) findViewById(R.id.spNomeProprietario);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FormularioNotificacoesActivity.this,
                android.R.layout.simple_spinner_item, listProprietario);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNomeProprietario.setAdapter(adapter);


        helper = new FormularioHelperNotificacao(this);

        Intent intent = getIntent();
        Notificacao notificacao = (Notificacao) intent.getSerializableExtra("notificacao");
        if(notificacao != null){

            helper.preencheFormularioInfracao(notificacao);
        }

        Button botaoFoto = (Button) findViewById(R.id.formulario_botao_infracao);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() +".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, CODIGO_CAMERA);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {

            if (requestCode == CODIGO_CAMERA) {
               helper.carregaImagem(caminhoFoto);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_notificacao, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                salvarInfracao();
                break;
            case R.id.menu_lista_proprietarios:
                startActivity( new Intent(FormularioNotificacoesActivity.this, ListaProprietariosActivity.class) );
                finish();
                break;
            case R.id.menu_cadastro_proprietario:
                startActivity( new Intent(FormularioNotificacoesActivity.this, FormularioProprietariosActivity.class) );
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

    private void salvarInfracao() {
        if(helper.pegaInfracao() == null){
            Toast.makeText(FormularioNotificacoesActivity.this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
        }else {
            NotificacaoDAO dao = new NotificacaoDAO(this);
            Notificacao notificacao = helper.pegaInfracao();
            if (notificacao.getId() != null) {
                dao.altera(notificacao);
                Toast.makeText(FormularioNotificacoesActivity.this, "Notificação alterada com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                dao.insere(notificacao);
                Toast.makeText(FormularioNotificacoesActivity.this, "Notificação salva com sucesso", Toast.LENGTH_SHORT).show();
            }
            dao.close();

            finish();
        }
    }
}
