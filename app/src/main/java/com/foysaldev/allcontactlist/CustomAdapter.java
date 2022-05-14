package com.foysaldev.allcontactlist;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    Activity activity;
    ArrayList<ContactModel> arrayList;
    ArrayList<ContactModel> listfull;

    public CustomAdapter(Activity activity, ArrayList<ContactModel> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
        this.listfull = arrayList;
        this.arrayList = new ArrayList<>(listfull);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ContactModel model = arrayList.get(position);

        holder.tvName.setText(model.getName());
        holder.tvNumber.setText(model.getNumber());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:"+model.getNumber()));
                activity.startActivity(intent);*/

                String s = "tel:" + model.getNumber();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(s));
                activity.startActivity(intent);

                /*AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment(model.getName(),model.getNumber())).addToBackStack(null).commit();*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public Filter getFilter() {
        return contactFilter;
    }

    private final Filter contactFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<ContactModel> contactFilterlist  = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                contactFilterlist.addAll(listfull);
            } else {
                String filterPattern = constraint.toString().trim();
                for (ContactModel contactModel : listfull) {
                    if (contactModel.getName().toLowerCase().contains(filterPattern))
                        contactFilterlist.add(contactModel);
                    if (contactModel.getName().toUpperCase().contains(filterPattern))
                        contactFilterlist.add(contactModel);
                }
            }
            FilterResults results = new FilterResults();
            results.values = contactFilterlist;
            results.count = contactFilterlist.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayList.clear();
            arrayList.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvNumber = itemView.findViewById(R.id.list_number);
        }
    }
}
