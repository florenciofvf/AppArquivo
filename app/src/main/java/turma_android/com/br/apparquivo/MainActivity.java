package turma_android.com.br.apparquivo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private EditText txtTexto;
    private TextView txtConteudo;

    private final String NOME_ARQUIVO = "arquivo_teste.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        txtTexto = (EditText) findViewById(R.id.txtTexto);
        txtConteudo = (TextView) findViewById(R.id.txtConteudo);

        lerArquivo();

        SharedPreferences pref = getPreferences(MODE_APPEND);
        String s = pref.getString("ULTIMO_TEXTO", "");
        txtTexto.setText(s);
    }

    public void salvarConteudo(View view) {
        try {
            FileOutputStream fos = openFileOutput(NOME_ARQUIVO, MODE_APPEND);

            fos.write(txtTexto.getText().toString().getBytes());
            fos.write("\n".getBytes());
            fos.close();

            lerArquivo();

            SharedPreferences pref = getPreferences(MODE_APPEND);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("ULTIMO_TEXTO", txtTexto.getText().toString());
            editor.commit();
        } catch (Exception e) {
            Log.i("ERRO", e.getMessage());
        }
    }

    private void lerArquivo() {
        StringBuilder builder = new StringBuilder();

        try {
            FileInputStream fis = openFileInput(NOME_ARQUIVO);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String linha = br.readLine();

            while(linha != null) {
                builder.append(linha + "\n");
                linha = br.readLine();
            }

            fis.close();
            txtConteudo.setText(builder.toString());
        } catch(Exception e) {
            Log.i("ERRO", e.getMessage());
        }
    }
}