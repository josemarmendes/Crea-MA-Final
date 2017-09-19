package br.edu.ifma.crea_ma.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifma.crea_ma.modelo.Proprietario;

public class ProprietarioDAO extends SQLiteOpenHelper{

    public ProprietarioDAO(Context context) {
        super(context, "Proprietario", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Proprietarios (id_proprietario INTEGER PRIMARY KEY, nome TEXT NOT NULL, cpf TEXT, email TEXT, telefone TEXT, endereco TEXT, tipoPessoa TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS Proprietarios";
        db.execSQL(sql);
        onCreate(db);

    }

    public void insere(Proprietario proprietario) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosDoProprietario(proprietario);

        db.insert("Proprietarios", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosDoProprietario(Proprietario proprietario) {
        ContentValues dados = new ContentValues();
        dados.put("nome", proprietario.getNome());
        dados.put("cpf", proprietario.getCpf());
        dados.put("email", proprietario.getEmail());
        dados.put("telefone", proprietario.getTelefone());
        dados.put("endereco", proprietario.getEndereco());
        dados.put("tipoPessoa", proprietario.getTipoPessoa());
        return dados;
    }

    public List<Proprietario> buscaProprietarios() {

        String sql = "SELECT * FROM Proprietarios;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        List<Proprietario> proprietarios = new ArrayList<Proprietario>();

        while (c.moveToNext()){
            Proprietario proprietario = new Proprietario();
            proprietario.setIdProprietario(c.getLong(c.getColumnIndex("id_proprietario")));
            proprietario.setNome(c.getString(c.getColumnIndex("nome")));
            proprietario.setCpf(c.getString(c.getColumnIndex("cpf")));
            proprietario.setEmail(c.getString(c.getColumnIndex("email")));
            proprietario.setTelefone(c.getString(c.getColumnIndex("telefone")));
            proprietario.setEndereco(c.getString(c.getColumnIndex("endereco")));
            proprietario.setTipoPessoa(c.getString(c.getColumnIndex("tipoPessoa")));

            proprietarios.add(proprietario);
        }

        c.close();

        return proprietarios;
    }

    public void remove(Proprietario proprietario) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {proprietario.getIdProprietario().toString()};
        db.delete("Proprietarios", "id_proprietario = ?", params);

    }

    public void altera(Proprietario proprietario) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosDoProprietario(proprietario);

        String[] params = {proprietario.getIdProprietario().toString()};
        db.update("Proprietarios", dados, "id_proprietario = ?", params);

    }

    public List<String> buscaProprietariosPorNome() {
        String sql = "SELECT * FROM Proprietarios;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        List<String> nomeProprietarios = new ArrayList<String>();

        while (c.moveToNext()){
            String nome = c.getString(c.getColumnIndex("nome"));
            nomeProprietarios.add(nome);
        }

        c.close();

        return nomeProprietarios;
    }
}
