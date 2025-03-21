package com.example.verificaorazi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //Inizializzazione variabili

    //Bottoni
    Button bt_Budget, bt_Cibo, bt_Tras, bt_Ricavo, bt_Altro, bt_Invia;

    //EditText
    EditText ed_BudIniziale, ed_Desc, ed_Importo;

    //TextView
    TextView TV_generale;

    boolean BottoneCliccato, BottoneCibo, BottoneTras, BottoneRicavo, BottoneAltro = false;

    //Variabili che servono per i calcoli dei budget e dei costi
    int budget, sottC, sottT, sottA, agg;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            ed_Desc.setVisibility(View.GONE);
            ed_Importo.setVisibility(View.GONE);
            bt_Invia.setVisibility(View.GONE);

            bt_Budget.setEnabled(true);

            //Tiene i pulsanti disabilitati
            bt_Cibo.setEnabled(false);
            bt_Tras.setEnabled(false);
            bt_Ricavo.setEnabled(false);
            bt_Altro.setEnabled(false);

            return insets;
        });

        //Collegamento dei bottoni con la grafica

        bt_Budget = findViewById(R.id.bt_Budget);
        bt_Cibo = findViewById(R.id.bt_Cibo);
        bt_Tras = findViewById(R.id.bt_Tras);
        bt_Ricavo = findViewById(R.id.bt_Ricavo);
        bt_Altro = findViewById(R.id.bt_Altro);
        bt_Invia = findViewById(R.id.bt_Invia);


        //Collegamento delle EditText con la grafica
        ed_BudIniziale = findViewById(R.id.ed_BudIniziale);
        ed_Desc = findViewById(R.id.ed_Desc);
        ed_Importo = findViewById(R.id.ed_Importo);






        bt_Budget.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                budget = ImpostaBudget();
                bt_Budget.setEnabled(false);
                bt_Cibo.setEnabled(true);
                bt_Tras.setEnabled(true);
                bt_Ricavo.setEnabled(true);
                bt_Altro.setEnabled(true);
                SettaDati(budget);
            }
        });


        // Listener per bt_Cibo
        bt_Cibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottoneCibo = true;
                mostraCampi();
                sottC = SottraiBudget(budget);
            }
        });

        // Listener per bt_Tras
        bt_Tras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottoneTras = true;
                mostraCampi();
                sottT = SottraiBudget(budget);
            }
        });

        // Listener per bt_Ricavo
        bt_Ricavo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottoneRicavo = true;
                mostraCampi();
                agg = AggiungiBudget(budget);
            }
        });

        // Listener per bt_Altro
        bt_Altro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottoneAltro = true;
                mostraCampi();
                sottA = SottraiBudget(budget);
            }
        });

        bt_Invia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InviaDati(budget, sottC, sottT, sottA, agg);
            }

        });







    }

    //metodo che imposta il budget
    public int ImpostaBudget(){
        int budget = Integer.parseInt(ed_BudIniziale.getText().toString());
        return budget;

    }


    //metodo che sottrae dal budget iniziale
    public int SottraiBudget(int budget){
        int importo = Integer.parseInt(ed_Importo.getText().toString());
        int tot = budget - importo;
        return tot;
    }

    public int AggiungiBudget(int budget){
        int importo = Integer.parseInt(ed_Importo.getText().toString());
        int tot = budget + importo;
        return tot;
    }





    // Metodo per mostrare ed_Desc, ed_Importo e bt_Invia se almeno un bottone è stato cliccato
    public void mostraCampi() {
        if (BottoneCibo || BottoneTras || BottoneRicavo || BottoneAltro) {
            ed_Desc.setVisibility(View.VISIBLE);
            ed_Importo.setVisibility(View.VISIBLE);
            bt_Invia.setVisibility(View.VISIBLE);
        }
    }


    public void InviaDati(int budget, int sottC, int sottT, int sottA, int agg){
        int totfinale = budget - sottC - sottT - sottA + agg;

        int PercentoC = (sottC * 100) / budget;
        int PercentoT = (sottT * 100) / budget;
        int PercentoA = (sottA * 100) / budget;
        int PercentoR = (agg * 100) / budget;

        if(totfinale < 0){
            Toast.makeText(this, "Il budget non può essere minore di 0!", Toast.LENGTH_SHORT).show();
        }
        else{
            String desc = "Budget Iniziale: " + budget + "\n" + "Tot Spese e Ricavi:" + "\n" + "Cibo: " + sottC  + PercentoC + "\n" + "Tras: " + sottT +  PercentoT + "\n" + "Ricavo: " + agg + PercentoR + "\n" + "Altro: " + sottA + PercentoA + "\n" + "Totale finale: " + totfinale;
            TV_generale.setText(desc);
        }


    }
    public void SettaDati(int budget){
        String desc = "Budget Iniziale: " + "budget" + "\n" + "Tot Spese e Ricavi:" + "\n" + "Cibo: " + "0" + "\n" + "Tras: " + "0" + "\n" + "Ricavo: " + "0" + "\n" + "Altro: " + "0" + "\n" + "Totale finale: " + "0";
        TV_generale.setText(desc);
    }


}