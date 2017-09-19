package br.edu.ifma.crea_ma.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.edu.ifma.crea_ma.R;
import br.edu.ifma.crea_ma.modelo.Notificacao;


public class NotificacoesAdapter extends BaseAdapter{


    private final List<Notificacao> notificacoes;
    private final Context context;

    public NotificacoesAdapter(Context context, List<Notificacao> notificacoes) {
        this.context = context;
        this.notificacoes = notificacoes;
    }

    @Override
    public int getCount() {
        return notificacoes.size();
    }

    @Override
    public Object getItem(int posicao) {
        return notificacoes.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return notificacoes.get(posicao).getId();
    }

    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {
        Notificacao notificacao = notificacoes.get(posicao);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.list_item_notificacao, parent, false);
        }

        TextView campoNome = (TextView) view.findViewById(R.id.item_nome_infracao);
        campoNome.setText(notificacao.getNomeNotificado());

        TextView campoTipoInfracao = (TextView) view.findViewById(R.id.item_nome_tipoinfracao);
        campoTipoInfracao.setText(notificacao.getInfracaoCometida());

        ImageView campoFotoInfracao = (ImageView) view.findViewById(R.id.item_foto_list_infracao);
        String caminhoFoto = notificacao.getCaminhoFoto();

        if(caminhoFoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            campoFotoInfracao.setImageBitmap(bitmapReduzido);
            campoFotoInfracao.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFotoInfracao.setTag(caminhoFoto);
        }

        return view;
    }
}
