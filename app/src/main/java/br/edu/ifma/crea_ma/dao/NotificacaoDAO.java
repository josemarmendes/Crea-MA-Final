package br.edu.ifma.crea_ma.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifma.crea_ma.ListaNotificacoesActivity;
import br.edu.ifma.crea_ma.modelo.Notificacao;


public class NotificacaoDAO extends SQLiteOpenHelper{

    public NotificacaoDAO(Context context) {
        super(context, "Notificacao", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Notificacoes (id INTEGER PRIMARY KEY, " +
                "nome_notificado TEXT NOT NULL, " +
                "dados_obra TEXT, etapa_alvenaria TEXT, " +
                "etapa_fundacao TEXT, etapa_cobertura TEXT, etapa_instalacao TEXT, " +
                "infracao_cometida TEXT, " +
                "caminhoFoto TEXT," +
                "enderecoNotificacao TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Notificacoes";
        db.execSQL(sql);
        onCreate(db);

    }

    public void insere(Notificacao notificacao) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosDaNotificacao(notificacao);
        db.insert("Notificacoes", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosDaNotificacao(Notificacao notificacao) {
        ContentValues dados = new ContentValues();
        dados.put("nome_notificado", notificacao.getNomeNotificado());
        dados.put("dados_obra", notificacao.getDadosObra());
        dados.put("etapa_alvenaria", notificacao.getAlvenaria());
        dados.put("etapa_fundacao", notificacao.getFundacao());
        dados.put("etapa_cobertura", notificacao.getCobertura());
        dados.put("etapa_instalacao", notificacao.getInstalacao());
        dados.put("infracao_cometida", notificacao.getInfracaoCometida());
        dados.put("caminhoFoto", notificacao.getCaminhoFoto());
        dados.put("enderecoNotificacao", notificacao.getEnderecoNotificacao());
        return dados;
    }

    public List<Notificacao> buscaNotificacoes() {

        String sql = "SELECT * FROM Notificacoes;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        List<Notificacao> notificacoes = new ArrayList<Notificacao>();

        while (c.moveToNext()){
            Notificacao notificacao = new Notificacao();
            notificacao.setId(c.getLong(c.getColumnIndex("id")));
            notificacao.setNomeNotificado(c.getString(c.getColumnIndex("nome_notificado")));
            notificacao.setDadosObra(c.getString(c.getColumnIndex("dados_obra")));
            notificacao.setAlvenaria(c.getString(c.getColumnIndex("etapa_alvenaria")));
            notificacao.setFundacao(c.getString(c.getColumnIndex("etapa_fundacao")));
            notificacao.setCobertura(c.getString(c.getColumnIndex("etapa_cobertura")));
            notificacao.setInstalacao(c.getString(c.getColumnIndex("etapa_instalacao")));
            notificacao.setInfracaoCometida(c.getString(c.getColumnIndex("infracao_cometida")));
            notificacao.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
            notificacao.setEnderecoNotificacao(c.getString(c.getColumnIndex("enderecoNotificacao")));

            notificacoes.add(notificacao);
        }

        c.close();
        Log.d(notificacoes.toString(), "Esta solicitando a lista de notificações");
        return notificacoes;
    }

    public void remove(Notificacao notificacao) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {notificacao.getId().toString()};
        db.delete("Notificacoes", "id = ?", params);

    }

    public void altera(Notificacao notificacao) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosDaNotificacao(notificacao);

        String[] params = {notificacao.getId().toString()};
        db.update("Notificacoes", dados, "id = ?", params);

    }

}
