package com.example.belot.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.belot.model.GameItem;
import com.example.belot.R;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private ArrayList<GameItem> gameList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener= listener;
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {

        public TextView scoreA;
        public TextView scoreB;


        public GameViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            scoreA = itemView.findViewById(R.id.scoreA);
            scoreB = itemView.findViewById(R.id.scoreB);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public GameAdapter(ArrayList<GameItem> gameList) {
        this.gameList = gameList;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_item, viewGroup, false);
        GameViewHolder gvh = new GameViewHolder(view, listener);
        return gvh;
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder gameViewHolder, int i) {
        GameItem currentGameItem = gameList.get(i);

        gameViewHolder.scoreA.setText(currentGameItem.getScoreA());
        gameViewHolder.scoreB.setText(currentGameItem.getScoreB());
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }
}
