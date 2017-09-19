package br.edu.ifma.crea_ma;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.List;

import br.edu.ifma.crea_ma.dao.ProprietarioDAO;
import br.edu.ifma.crea_ma.modelo.Notificacao;
import br.edu.ifma.crea_ma.modelo.Proprietario;


public class FormularioHelperNotificacao {

    private final Spinner spNomeProprietario;
    private final Spinner spInfracao;
    private final RadioGroup rgTipoObra;
    private final CheckBox chkAlvenaria;
    private final CheckBox chkFundacao;
    private final CheckBox chkCobertura;
    private final CheckBox chkInstalacao;
    private final ImageView campoFoto;
    private final EditText campoEnderecoNotificacao;

    private Notificacao notificacao;

    public FormularioHelperNotificacao(FormularioNotificacoesActivity activity){

        spNomeProprietario = (Spinner) activity.findViewById(R.id.spNomeProprietario);
        spInfracao = (Spinner) activity.findViewById(R.id.spInfracoes);
        rgTipoObra = (RadioGroup) activity.findViewById(R.id.rgTipoObra);
        chkAlvenaria = (CheckBox) activity.findViewById(R.id.chkAlvenaria);
        chkFundacao = (CheckBox) activity.findViewById(R.id.chkFundacao);
        chkCobertura = (CheckBox) activity.findViewById(R.id.chkCobertura);
        chkInstalacao = (CheckBox) activity.findViewById(R.id.chkInstalacoes);

        campoFoto = (ImageView) activity.findViewById(R.id.formulario_foto_infracao);;

        campoEnderecoNotificacao = (EditText) activity.findViewById(R.id.edtEnderecoObra);
        notificacao = new Notificacao();

    }


    public Notificacao pegaInfracao() {

            notificacao.setNomeNotificado(spNomeProprietario.getSelectedItem().toString());
            notificacao.setInfracaoCometida(spInfracao.getSelectedItem().toString());
            notificacao.setDadosObra(pegaRbSelecionado());
            notificacao.setAlvenaria( (chkAlvenaria.isChecked()) ? "Alvenaria" : "" );
            notificacao.setFundacao( (chkFundacao.isChecked()) ? "Fundacao" : "" );
            notificacao.setCobertura( (chkCobertura.isChecked()) ? "Cobertura" : "" );
            notificacao.setInstalacao( (chkInstalacao.isChecked()) ? "Instalacao" : "" );
            notificacao.setCaminhoFoto((String) campoFoto.getTag());
            notificacao.setEnderecoNotificacao((String) campoEnderecoNotificacao.getText().toString());

            return notificacao;
    }

    private String pegaRbSelecionado() {
        String tipo = "";
        int id = rgTipoObra.getCheckedRadioButtonId();
        switch (id){
            case (R.id.rbResidencial):
                tipo = "residencial";
                return tipo;

            case (R.id.rbComercial):
                tipo = "comercial";
                return tipo;

        }
        return null;
    }

    private boolean validaCampos() {
        if(spNomeProprietario.getSelectedItem().toString().isEmpty() || spInfracao.getSelectedItem().toString().isEmpty()){
            return false;
        }
        return true;
    }


    public void preencheFormularioInfracao(Notificacao notificacao) {

//        spNomeProprietario.setSelection(1);
//        spInfracao.setSelection(1);
        chkCobertura.setChecked( (notificacao.getCobertura().toString().equals("Cobertura") ) ? true : false );
        chkFundacao.setChecked( (notificacao.getFundacao().toString().equals("Fundacao") ) ? true : false );
        chkAlvenaria.setChecked( (notificacao.getAlvenaria().toString().equals("Alvenaria") ) ? true : false );
        chkInstalacao.setChecked( (notificacao.getInstalacao().toString().equals("Instalacao") ) ? true : false );
        RadioButton rb;
        if(notificacao.getDadosObra().equals("fisica")){
            rb = rgTipoObra.findViewById(R.id.rbResidencial);
            rb.setChecked(true);
        }else{
            rb = rgTipoObra.findViewById(R.id.rbComercial);
            rb.setChecked(true);
        }
        carregaImagem(notificacao.getCaminhoFoto());
        campoEnderecoNotificacao.setText(notificacao.getEnderecoNotificacao());

        this.notificacao = notificacao;
    }

    public void carregaImagem(String caminhoFoto) {
        if(caminhoFoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
        }

    }
}
