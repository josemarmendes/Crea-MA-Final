package br.edu.ifma.crea_ma.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ifma.crea_ma.R;
import br.edu.ifma.crea_ma.modelo.Proprietario;

public class ProprietariosAdapter extends BaseAdapter{

    private final List<Proprietario> proprietarios;
    private final Context context;

    public ProprietariosAdapter(Context context, List<Proprietario> proprietarios){
        this.context = context;
        this.proprietarios = proprietarios;

    }

    @Override
    public int getCount() {
        return proprietarios.size();
    }

    @Override
    public Object getItem(int posicao) {
        return proprietarios.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return proprietarios.get(posicao).getIdProprietario();
    }

    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {
        Proprietario proprietario = proprietarios.get(posicao);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.list_item_proprietario, parent, false);
        }

        TextView campoId = (TextView) view.findViewById(R.id.item_id_proprietario);
        campoId.setText(proprietario.getIdProprietario().toString());

        TextView campoNome = (TextView) view.findViewById(R.id.item_nome_proprietario);
        campoNome.setText(proprietario.getNome());

        TextView campoCpf = (TextView) view.findViewById(R.id.item_cpf_proprietario);
        campoCpf.setText(proprietario.getCpf());

        return view;
    }
}
