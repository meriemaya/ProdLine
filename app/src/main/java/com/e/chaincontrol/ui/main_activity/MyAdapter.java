package com.e.chaincontrol.ui.main_activity;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.chaincontrol.R;
import com.e.chaincontrol.models.MachineModel;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ElementView>{

    private List<MachineModel> machines;

    private OnMachineClicked listener ;


    public MyAdapter(List<MachineModel> machines, OnMachineClicked listener){

        this.machines=machines;
        this.listener=listener;

    }

 public void setMachines(List<MachineModel> machines) {
     this.machines = machines;
 }

    @NonNull
    @Override
    public ElementView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.machine_item, parent, false);

        return new ElementView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ElementView holder, int position) {
        holder.textView.setText(machines.get(position).getName());
        holder.imageView.setImageResource(R.drawable.machine);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMachineClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (machines == null ) return 0;
        return machines.size();
    }


    public class ElementView extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imageView ;

        public ElementView(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView = (TextView) view.findViewById(R.id.item_name_machine);
            imageView=(ImageView) view.findViewById(R.id.item_image_logo);


        }

        public TextView getTextView() {
            return textView;
        }
    }


}
