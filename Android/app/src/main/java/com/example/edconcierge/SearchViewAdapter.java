package com.example.edconcierge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//adapter extends RecyclerView.Adapter<泛型>
// 1. constructor
// 2. 新建一个ViewHolder extends RecyclerView.ViewHolder
// 3. onCreateViewHolder创建viewholder实例，RecyclerView.ViewHolder是抽象类，不能直接实例化；
//    先layoutinflate一个view，然后实例viewholder，传入view
// 4. bindViewHolder是对ViewHolder赋值
// 5. getItemCount是告诉recycleview有多少项
public class SearchViewAdapter extends RecyclerView.Adapter<SearchViewAdapter.MyViewHolder> implements Filterable{
    LayoutInflater layoutInflater;
    List<String> QuestionTitle;
    final List<String> QuestionTitleUnChanged;
    Context context;
    public SearchViewAdapter(Context context,List<String> L){
        this.context=context;
        QuestionTitle=new ArrayList<>(L);
        QuestionTitleUnChanged=new ArrayList<>(L);
        layoutInflater=LayoutInflater.from(context);
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.search_Text);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.search_item,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.textView.setText(QuestionTitle.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(QuestionTitleUnChanged.size()<9){
                    Intent intent = new Intent(context, InformationActivity.class);
                    intent.putExtra("indexQuestion", position);
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context, NavigationActivity.class);
                    intent.putExtra("indexQuestion", position);
                    context.startActivity(intent);
                }
            }
        });
        //System.out.println(QuestionTitle.get(position));
    }

    @Override
    public int getItemCount() {
        return QuestionTitle.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(QuestionTitleUnChanged);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (String s : QuestionTitleUnChanged) {
                    if (s.toLowerCase().contains(filterPattern)) {
                        filteredList.add(s);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        //Invoked in the UI thread to publish the filtering results in the user interface.
        //QuestionTitle是赋值给recycleview的
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            QuestionTitle.clear();
            QuestionTitle.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
