package net.senior.hr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class EmpAdapter extends RecyclerView.Adapter<EmpAdapter.ViewHolder>{
List<CVModel> emps;
OnEmpClickedListener onEmpClickedListener;

    public void setOnEmpClickedListener(OnEmpClickedListener onEmpClickedListener) {
        this.onEmpClickedListener = onEmpClickedListener;
    }

    public EmpAdapter(List<CVModel> emps) {
        this.emps = emps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.applied_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (emps.size() > 0 &&  emps.get(position)!=null) {
            holder.empName.setText(emps.get(position).getFullName());
            Picasso.get().load(emps.get(position).getPhoto()).into(holder.img);
        }else {
            holder.empName.setText("No One Has Applied");
        }

    }

    @Override
    public int getItemCount() {
        return emps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView empName;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           empName= itemView.findViewById(R.id.empName);
           img= itemView.findViewById(R.id.empPic);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   onEmpClickedListener.onEmpClicked(getAdapterPosition());
               }
           });
        }
    }
    public interface OnEmpClickedListener{
        public void onEmpClicked(int pos);
    }
}
