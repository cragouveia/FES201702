package br.estacio.fes201702;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.estacio.fes201702.dao.AlunoDAO;
import br.estacio.fes201702.domain.Aluno;

public class FormAluno extends AppCompatActivity {

    private EditText edtNome, edtFone, edtEmail;
    private Button btnSalvar;

    private Aluno aluno;
    private AlunoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_aluno);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtFone = (EditText) findViewById(R.id.edtFone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
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

                aluno = new Aluno();
                aluno.setNome(edtNome.getText().toString());
                aluno.setFone(edtFone.getText().toString());
                aluno.setEmail(edtEmail.getText().toString());

                dao = new AlunoDAO(FormAluno.this);
                dao.insert(aluno);
                dao.close();

                Toast.makeText(FormAluno.this,
                        String.format("Registro para o aluno %s inserido com sucesso.",
                                edtNome.getText()),
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }
}
