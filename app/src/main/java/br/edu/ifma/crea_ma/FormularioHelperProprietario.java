package br.edu.ifma.crea_ma;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import br.edu.ifma.crea_ma.modelo.Proprietario;



public class FormularioHelperProprietario {

    private final EditText campoNomeProprietario;
    private final EditText campoCpf;
    private final EditText campoEmail;
    private final EditText campoTelefone;
    private final EditText campoEndereco;
    private final RadioGroup rgTipoPessoa;

    private Proprietario proprietario;

    public FormularioHelperProprietario(FormularioProprietariosActivity activity){

        campoNomeProprietario = (EditText) activity.findViewById(R.id.edtNomeProprietario);
        campoCpf = (EditText) activity.findViewById(R.id.edtCpf);
        campoEmail = (EditText) activity.findViewById(R.id.edtEmail);
        campoTelefone = (EditText) activity.findViewById(R.id.edtTelefone);
        campoEndereco = (EditText) activity.findViewById(R.id.edtEndereco);
        rgTipoPessoa = (RadioGroup) activity.findViewById(R.id.rgTipoPessoa);
        proprietario = new Proprietario();
    }


    public Proprietario pegaProprietario() {
        if(validaCampos()) {
            proprietario.setNome(campoNomeProprietario.getText().toString());
            proprietario.setCpf(campoCpf.getText().toString());
            proprietario.setEmail(campoEmail.getText().toString());
            proprietario.setTelefone(campoTelefone.getText().toString());
            proprietario.setEndereco(campoEndereco.getText().toString());
            proprietario.setTipoPessoa(pegaRbSelecionado());
            return proprietario;
        }
        return null;
    }

    private String pegaRbSelecionado() {
        String tipo = "";
        int id = rgTipoPessoa.getCheckedRadioButtonId();
        switch (id){
            case (R.id.rbFisica):
                tipo = "fisica";
                return tipo;

            case (R.id.rbJuridica):
                tipo = "juridica";
                return tipo;

        }
        return null;

    }

    private boolean validaCampos() {
        if (campoNomeProprietario.getText().toString().isEmpty() || campoCpf.getText().toString().isEmpty()
                || campoEmail.getText().toString().isEmpty()
                || campoTelefone.getText().toString().isEmpty()
                || campoEndereco.getText().toString().isEmpty() ){
            return false;
        }
        return true;
    }

    public void preencheFormularioProprietario(Proprietario proprietario) {
        campoNomeProprietario.setText(proprietario.getNome());
        campoCpf.setText(proprietario.getCpf());
        campoEmail.setText(proprietario.getEmail());
        campoTelefone.setText(proprietario.getTelefone());
        campoEndereco.setText(proprietario.getEndereco());
        RadioButton rb;
        if(proprietario.getTipoPessoa().equals("fisica")){
            rb = rgTipoPessoa.findViewById(R.id.rbFisica);
            rb.setChecked(true);
        }else{
            rb = rgTipoPessoa.findViewById(R.id.rbJuridica);
            rb.setChecked(true);
        }
        this.proprietario = proprietario;

    }
}
