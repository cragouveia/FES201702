package br.estacio.fes201702;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.List;

import br.estacio.fes201702.adapter.AlunoAdapter;
import br.estacio.fes201702.dao.AlunoDAO;
import br.estacio.fes201702.domain.Aluno;

public class MainActivity extends AppCompatActivity {

    private ListView listaAlunos;
    private AlunoDAO dao;
    private List<Aluno> alunos;
    private AlunoAdapter adapter;
    private Button btnNovoAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaAlunos = (ListView) findViewById(R.id.listaAlunos);
        btnNovoAluno = (Button) findViewById(R.id.btnNovoAluno);
        btnNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormAluno.class);
                startActivity(intent);
            }
        });

        dao = new AlunoDAO(MainActivity.this);
        alunos = dao.list();
        dao.close();

        adapter = new AlunoAdapter(alunos, this);

        listaAlunos.setAdapter(adapter);
    }


}
