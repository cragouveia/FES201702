package br.estacio.fes201702;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.List;

import br.estacio.fes201702.dao.AlunoDAO;
import br.estacio.fes201702.domain.Aluno;

public class MainActivity extends AppCompatActivity {

    private ListView listaAlunos;
    private AlunoDAO dao;
    private List<Aluno> alunos;
    private ArrayAdapter adapter;
    private String[] nomeAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaAlunos = (ListView) findViewById(R.id.listaAlunos);
        dao = new AlunoDAO(MainActivity.this);
        alunos = dao.list();
        dao.close();
        nomeAlunos = new String[alunos.size()];

        for (int i = 0; i < alunos.size(); i++) {
            nomeAlunos[i] = alunos.get(i).getNome();
        }

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nomeAlunos);

        listaAlunos.setAdapter(adapter);
    }


}
