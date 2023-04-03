package com.example.alimentoapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class TableSlotAdapter extends RecyclerView.Adapter<TableSlotAdapter.ViewHolder> {

    private List<String> fields;
    private DocumentReference documentRef;
    ListenerRegistration registration;

    ArrayList<RestaurantReservationsData> restaurantReservationsDataArrayList;
    private int clickCount = 0;
    private final int maxClicks = 5;
    private String docId;
    FirebaseFirestore db;

    public TableSlotAdapter(DocumentReference documentReference, List<String> fields, ArrayList<RestaurantReservationsData> restaurantReservationsDataArrayList){
        this.documentRef = documentReference;
        this.fields = fields;
        this.restaurantReservationsDataArrayList = restaurantReservationsDataArrayList;
        this.docId = documentReference.getId();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_table_detail_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String field = fields.get(position);

        registration = documentRef.addSnapshotListener((documentSnapshot, e) -> {
            if (e != null) {
                return;
            }
            if (documentSnapshot != null && documentSnapshot.exists()) {
                String value = documentSnapshot.getString(field);
                holder.tbslt.setText(value);

                holder.tbsl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(clickCount < maxClicks) {
                            Intent tb2 = new Intent(v.getContext(), TableSlotReserve.class);
                            tb2.putExtra("slots", value);
                            tb2.putExtra("hotelnames",docId);
                            v.getContext().startActivity(tb2);

                            clickCount++;
                        }else{
                            holder.tbslt.setText("Reservations Full");
                            holder.tbsl.setEnabled(false);
                            holder.tbsl.setAlpha(0.5f);
                        }
                    }
                });
            }
        });



    }

    @Override
    public int getItemCount() {
        return fields.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tbslt;
        CardView tbsl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tbslt = itemView.findViewById(R.id.tv_resttbslt);
            tbsl = itemView.findViewById(R.id.cardView_tablesltdetail);
        }
    }

    public void cleanup() {
        if (registration != null) {
            registration.remove();
        }
    }

}
