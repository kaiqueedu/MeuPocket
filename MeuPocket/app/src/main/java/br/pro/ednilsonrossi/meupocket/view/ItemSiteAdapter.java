package br.pro.ednilsonrossi.meupocket.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.pro.ednilsonrossi.meupocket.R;
import br.pro.ednilsonrossi.meupocket.model.Site;

/**
 * Agora nosso Adapter é uma extensão do Adapter fornecido pelo pacote
 * RecyclerView, ele necessita de alguns ajustes adicionais se comparado
 * ao ArrayAdapter que implementamos na versão anterior.
 */
public class ItemSiteAdapter extends RecyclerView.Adapter<ItemSiteAdapter.SitesViewHolder> {

    private static RecyclerItemClickListener clickListener;
    private List<Site> siteList;

    //Construtor
    public ItemSiteAdapter(List<Site> siteList) {
        this.siteList = siteList;
    }

    public void setClickListener(RecyclerItemClickListener clickListener) {
        ItemSiteAdapter.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SitesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_pocket, parent, false);
        SitesViewHolder viewHolder = new SitesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SitesViewHolder holder, final int position) {
        holder.tituloTextView.setText(siteList.get(position).getTitulo());
        holder.enderecoTextView.setText(siteList.get(position).getEndereco());
        if (siteList.get(position).isFavorito())
            holder.favoritoImageView.setImageResource(R.drawable.ic_favorito);
        else
            holder.favoritoImageView.setImageResource(R.drawable.ic_nao_favorito);

        holder.favoritoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEstrelaClique(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return siteList.size();
    }

    private void onEstrelaClique(int position) {
        if (siteList.get(position).isFavorito())
            siteList.get(position).undoFavorite();
        else
            siteList.get(position).doFavotite();
        notifyDataSetChanged();
    }

    public static class SitesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tituloTextView;
        public TextView enderecoTextView;
        public ImageView favoritoImageView;

        public SitesViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.text_titulo);
            enderecoTextView = itemView.findViewById(R.id.text_endereco);
            favoritoImageView = itemView.findViewById(R.id.image_favorito);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onItemClick(getAdapterPosition());
        }
    }

}
