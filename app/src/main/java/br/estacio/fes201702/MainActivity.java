package br.estacio.fes201702;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.security.DomainCombiner;
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
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                view.showContextMenu();
                /*
                Aluno aluno = adapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, FormAluno.class);
                intent.putExtra("alunoSelecionado", aluno);
                startActivity(intent);
                */
            }
        });
        btnNovoAluno = (Button) findViewById(R.id.btnNovoAluno);
        btnNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormAluno.class);
                startActivity(intent);
            }
        });

        registerForContextMenu(listaAlunos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    private void carregaLista() {
        dao = new AlunoDAO(MainActivity.this);
        alunos = dao.list();
        dao.close();

        adapter = new AlunoAdapter(alunos, this);

        listaAlunos.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = adapter.getItem(info.position);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                confirmaRemocao(aluno);
                return false;
            }
        });

        MenuItem alterar = menu.add("Alterar");
        alterar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this, FormAluno.class);
                intent.putExtra("alunoSelecionado", aluno);
                startActivity(intent);
                return false;
            }
        });



    }

    private void confirmaRemocao(final Aluno aluno) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Remoção");
        dialogBuilder.setMessage(String.format("Confirma a remoção do aluno %s?", aluno.getNome()));
        dialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dao = new AlunoDAO(MainActivity.this);
                dao.delete(aluno.getId());
                dao.close();
                carregaLista();
                dialog.cancel();
            }
        });
        dialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        dialogBuilder.create().show();
    }




}
