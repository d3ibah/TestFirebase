package by.testfirebase;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import by.testfirebase.dataModel.Article;


public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.AasViewHolder> {

    private List<Article> articleList;

    public ShowAdapter(List<Article> articles) {
        this.articleList = articles;
    }

    @NonNull
    @Override
    public AasViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_and_show_item_recyclerview, viewGroup, false);
        return new AasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AasViewHolder holder, int position) {
        Article article = articleList.get(position);

        holder.textMessage.setText(article.getTextMessage());
        holder.textIdUser.setText(article.getUserId());
        holder.textDate.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", article.getTimeMessage()));


    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class AasViewHolder extends RecyclerView.ViewHolder{
        TextView textDate, textIdUser, textMessage;

        public AasViewHolder(@NonNull View itemView) {
            super(itemView);

            textDate = itemView.findViewById(R.id.textViewDate);
            textIdUser = itemView.findViewById(R.id.textViewIdUser);
            textMessage = itemView.findViewById(R.id.textViewMessage);
        }
    }
}
