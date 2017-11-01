package br.estacio.fes201702.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.estacio.fes201702.R;
import br.estacio.fes201702.domain.Aluno;

public class AlunoAdapter extends BaseAdapter {

    private List<Aluno> listaAlunos;
    private Activity activity;
    private TextView txtNome, txtFone, txtMail;

    public AlunoAdapter(List<Aluno> listaAlunos, Activity activity) {
        this.listaAlunos =listaAlunos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listaAlunos.size();
    }

    @Override
    public Aluno getItem(int position) {
        return listaAlunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaAlunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.aluno, parent, false);

        Aluno aluno = listaAlunos.get(position);

        txtNome = (TextView) view.findViewById(R.id.txtNome);
        txtNome.setText(aluno.getNome());

        txtFone = (TextView) view.findViewById(R.id.txtFone);
        txtFone.setText(aluno.getFone());

        txtMail = (TextView) view.findViewById(R.id.txtMail);
        txtMail.setText(aluno.getEmail());

        return view;
    }
}
