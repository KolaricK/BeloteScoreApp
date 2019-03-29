package com.example.belot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.belot.adapter.GameAdapter;
import com.example.belot.dialogs.AboutDialogFragment;
import com.example.belot.dialogs.EnterScoreDialogFragment;
import com.example.belot.dialogs.RestartDialogFragment;
import com.example.belot.dialogs.WinConditionDialogFragment;
//import com.example.belot.dialogs.ExitDialogFragment;  DISABLED
import com.example.belot.model.GameItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // current score variables of the game
    private TextView tvCurrentScoreTeamA;
    private TextView tvCurrentScoreTeamB;
    private int currentScoreA = 0;
    private int currentScoreB = 0;

    // total win variables
    private TextView tvTotalWinA;
    private TextView tvTotalWinB;
    private TextView tvFirstTo;
    public int totalWinA = 0;
    public int totalWinB = 0;
    public int winCondition = 1001;

    // recyclerView variables
    private RecyclerView recyclerView;
    private GameAdapter recyclerAdapter;
    private ArrayList<GameItem> gameItemArrayList;

    // Buttons
    private ImageButton btnNewGame;
    private ImageButton btnAddScore;
    private ImageButton btnWinCondition;
    private ImageButton btnRestart;
    private ImageButton btnAbout;
    // private ImageButton btnExit; Disabled

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameItemArrayList = new ArrayList<>(); // creating game list
        setupRecyclerGames(); // setting up recyclerView
        initWidgets(); // initialize widgets
        setupButtonListeners(); // setup buttons

        displayCurrentScore(); // display current score
        displayTotalScore(); // display total score
        displayWinCondition(); // display win condition
    }

    /*
    *       Initialize widgets
    * */
    private void initWidgets() {
        // textVies current score
        tvCurrentScoreTeamA = findViewById(R.id.current_game_team_a);
        tvCurrentScoreTeamB = findViewById(R.id.current_game_team_b);
        // total wins textViews and win condition
        tvTotalWinA = findViewById(R.id.games_team_a);
        tvTotalWinB = findViewById(R.id.games_team_b);
        tvFirstTo = findViewById(R.id.tv_first_to);
        // Buttons
        btnNewGame = findViewById(R.id.btn_new_game);
        btnAddScore = findViewById(R.id.btn_add_score);
        btnWinCondition = findViewById(R.id.btn_win_condition);
        btnRestart = findViewById(R.id.btn_restart);
        btnAbout = findViewById(R.id.btn_about);
        //btnExit = findViewById(R.id.btn_exit); DISABLED
    }

    /*
     *    Setting up recycler view
     * */
    private void setupRecyclerGames() {
        recyclerView = findViewById(R.id.recycler_games);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerAdapter = new GameAdapter(gameItemArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.setOnItemClickListener(new GameAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    /*
    *       Delete game list item on click if score was wrongly entered
    * */
    public void removeItem(int position) {
        int scoreA = Integer.valueOf(gameItemArrayList.get(position).getScoreA());
        int scoreB = Integer.valueOf(gameItemArrayList.get(position).getScoreB());

        gameItemArrayList.remove(position);
        recyclerAdapter.notifyItemRemoved(position);

        //  if team A won reduce total win if scores was wrongly entered
        if (currentScoreA > 1000 && totalWinA > 0 && currentScoreA > currentScoreB) {
            totalWinA--;
        }

        //  if team A won reduce total win if scores was wrongly entered
        if (currentScoreB > 1000 && totalWinB > 0 && currentScoreB > currentScoreA) {
            totalWinB--;
        }

        currentScoreA -= scoreA;
        currentScoreB -= scoreB;

        addScoreClick(); // set button add clickable again
        displayCurrentScore();
        displayTotalScore();
    }

    // Display input from dialog to main activity
    public void calculateScores(String scoreA, String scoreB) {
        currentScoreA += Integer.valueOf(scoreA);
        currentScoreB += Integer.valueOf(scoreB);
        displayCurrentScore();

        gameItemArrayList.add(new GameItem(String.valueOf(scoreA), String.valueOf(scoreB)));
        win();
    }

    /*
    *       Check if any of teams reached wining condition
    * */
    private void win() {

        if (currentScoreA >= winCondition || currentScoreB >= winCondition) {
            if (currentScoreA > currentScoreB) {
                totalWinA++;
                btnAddScore.setClickable(false); // disables button for adding score after wining condition met
            } else {
                totalWinB++;
                btnAddScore.setClickable(false);
            }
        }

        displayTotalScore();
    }

    /*
     *       Display current score to textViews
     * */
    private void displayCurrentScore() {
        tvCurrentScoreTeamA.setText(String.valueOf(currentScoreA));
        tvCurrentScoreTeamB.setText(String.valueOf(currentScoreB));
    }

    /*
     *       Display total wins to textViews
     * */
    public void displayTotalScore() {
        tvTotalWinA.setText(String.valueOf(totalWinA));
        tvTotalWinB.setText(String.valueOf(totalWinB));
    }

    /*
    *       Display win condition to textView
    * */
    public void displayWinCondition() {
        tvFirstTo.setText(String.valueOf(winCondition));
    }

    /*
    *       Clear current score textViews, GameList and RecyclerView
    * */
    public void clear() {
        gameItemArrayList.clear();
        currentScoreA = 0;
        currentScoreB = 0;
        displayCurrentScore();
        recyclerView.removeAllViews();
    }

    /*
     *       Set button for adding score clickable again
     * */
    private void addScoreClick() {
        if (!btnAddScore.isClickable()) {
            btnAddScore.setClickable(true);
        }
    }

    /*
    *       Setup buttons
    * */
    private void setupButtonListeners() {
        // Start new game
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                addScoreClick();
            }
        });

        // Add score for current game
        btnAddScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterScoreDialogFragment es = new EnterScoreDialogFragment();
                es.show(getSupportFragmentManager(), "Add scores");
            }
        });

        // select win condition
        btnWinCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WinConditionDialogFragment wcd = new WinConditionDialogFragment();
                wcd.show(getSupportFragmentManager(), "Win condition");
            }
        });

        // Action called on button clicked restart game
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestartDialogFragment rdf = new RestartDialogFragment();
                rdf.show(getSupportFragmentManager(), "Restart");
                addScoreClick();
            }
        });

        // Action called on button clicked about us
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutDialogFragment af = new AboutDialogFragment();
                af.show(getSupportFragmentManager(), "About us");
            }
        });

        // Exit app action on button click DISABLED
        /*btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExitDialogFragment edf = new ExitDialogFragment();
                edf.show(getSupportFragmentManager(), "Exit dialog");
            }
        });*/
    }

}
