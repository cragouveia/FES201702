package br.estacio.fes201702;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.estacio.fes201702.dao.AlunoDAO;
import br.estacio.fes201702.domain.Aluno;

public class FormAluno extends AppCompatActivity {

    private EditText edtNome, edtFone, edtEmail, edtEndereco;
    private Button btnSalvar;

    private Aluno aluno;
    private AlunoDAO dao;

    private Spinner spinnerEstadoCivil;
    private ArrayAdapter<String> adapterEstadoCivil;
    private String estadoCivilSelecionado;
    private List<String> listaEstadoCivil = Arrays.asList(new String[] {
            "Solteiro(a)",
            "Casado(a)",
            "Divorciado(a)",
            "Viúvo(a)",
            "União Estável"});
    private Spinner spinnerSexo;
    private ArrayAdapter<String> adapterSexo;
    private String sexoSelecionado;
    private List<String> listaSexo = Arrays.asList(new String[] {
            "Feminino",
            "Masculino"});

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_aluno);
        adapterEstadoCivil = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaEstadoCivil);
        adapterSexo = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaSexo);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtFone = (EditText) findViewById(R.id.edtFone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtEndereco = (EditText) findViewById(R.id.edtEndereco);
        spinnerEstadoCivil = (Spinner) findViewById(R.id.spinnerEstadoCivil);
        spinnerEstadoCivil.setAdapter(adapterEstadoCivil);
        spinnerEstadoCivil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estadoCivilSelecionado = adapterEstadoCivil.getItem(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerSexo = (Spinner) findViewById(R.id.spinnerSexo);
        spinnerSexo.setAdapter(adapterSexo);
        spinnerSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sexoSelecionado = adapterSexo.getItem(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtNome.getText().toString().trim().isEmpty()) {
                    edtNome.setError("O campo NOME é obrigatório!!");
                    return;
                }
                if (edtFone.getText().toString().trim().isEmpty()) {
                    edtFone.setError("O campo FONE é obrigatório!!");
                    return;
                }
                if (edtEmail.getText().toString().trim().isEmpty()) {
                    edtEmail.setError("O campo EMAIL é obrigatório!!");
                    return;
                }

                if (aluno == null) {
                    aluno = new Aluno();
                }
                aluno.setNome(edtNome.getText().toString());
                aluno.setFone(edtFone.getText().toString());
                aluno.setEmail(edtEmail.getText().toString());
                aluno.setEndereco(edtEndereco.getText().toString());
                aluno.setEstadoCivil(estadoCivilSelecionado);
                aluno.setSexo(sexoSelecionado);

                dao = new AlunoDAO(FormAluno.this);
                if (aluno.getId() == 0) {
                    dao.insert(aluno);
                }
                else {
                    dao.update(aluno);
                }
                dao.close();

                Toast.makeText(FormAluno.this,
                        String.format("Registro para o aluno %s inserido com sucesso.",
                                edtNome.getText()),
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });

        aluno = (Aluno) getIntent().getSerializableExtra("alunoSelecionado");
        if (aluno != null) {
            edtNome.setText(aluno.getNome());
            edtEmail.setText(aluno.getEmail());
            edtFone.setText(aluno.getFone());
            edtEndereco.setText(aluno.getEndereco());
            spinnerEstadoCivil.setSelection(listaEstadoCivil.indexOf(aluno.getEstadoCivil()));
            spinnerSexo.setSelection(listaSexo.indexOf(aluno.getSexo()));
        }

    }
}
