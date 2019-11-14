package e.hp.redo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import pl.droidsonroids.gif.GifImageView;

public class FindCars extends AppCompatActivity {
    RecyclerView carsList;
    DatabaseReference mref;
    FirebaseRecyclerAdapter<cars,carsViewHolder>firebaseRecyclerAdapter;
    GifImageView myGif;
    android.support.v7.widget.Toolbar findCars_toolbar;
    //TextView kmsValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_cars);

        //toolbar
        findCars_toolbar=findViewById(R.id.findCars_toolbar);
        setSupportActionBar(findCars_toolbar);
        getSupportActionBar().setTitle("Select Car");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        carsList=findViewById(R.id.carsList);
        myGif=findViewById(R.id.myGif);
        mref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://practicefirebase-9aef6.firebaseio.com/");
        mref.keepSynced(true);
        carsList.setHasFixedSize(true);
        //String kms=MainActivity.totalKms;
        //Log.e("kms value",kms);
        //kmsValue.setText(kms+" Kms");
        carsList.setLayoutManager(new LinearLayoutManager(this));
        Query carsQuery=mref.orderByChild("BookingStatus");
        FirebaseRecyclerOptions carsOptions=new FirebaseRecyclerOptions.Builder<cars>().setQuery(carsQuery.equalTo("no"),cars.class).build();
        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<cars, carsViewHolder>
                (carsOptions) {
            @Override
            protected void onBindViewHolder(@NonNull carsViewHolder holder, int position, @NonNull cars model) {
               // if(model.getBookingStatus().equals("no")) {
                    holder.setCarName(model.getCarName());
                    holder.setQuantity(model.getQuantity());
                    holder.setCarBasePrice(model.getBasePrice());
                    holder.setImage(getApplicationContext(), model.getImage());
                    holder.perHead.setText(" , "+String.valueOf(model.getPerHead())+"rs per km.");
                    holder.modelno.setText(model.getModelNumber());
                    holder.kmsValue.setText(MainActivity.totalKms+" Kms");
                    String charges=priceCalculator(MainActivity.totalKms,String.valueOf(model.getBasePrice()),String.valueOf(model.getPerHead()));
                    Log.e("charges Value:",charges);
                    holder.chargesValue.setText(charges+"rs");
                    holder.booking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent book_intent=new Intent(FindCars.this,BookingForm.class);
                            startActivity(book_intent);
                        }
                    });

                //}
            }

            @NonNull
            @Override
            public carsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                myGif.setVisibility(View.INVISIBLE);
                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cars_list,viewGroup,false);
               // GifImageView carImage=view.findViewById(R.id.carImage);
                //carImage.setImageResource(R.drawable.loading1);
                return new carsViewHolder(view);
            }
        };
        carsList.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }

    public void booking(View view) {

    }

    public  static class carsViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView perHead,modelno,kmsValue,chargesValue,booking;
        public carsViewHolder(View itemView){
            super(itemView);
            mView=itemView;
            kmsValue=itemView.findViewById(R.id.kmsValue);
            chargesValue=itemView.findViewById(R.id.chargesValue);
            perHead=itemView.findViewById(R.id.perHead);
            booking=itemView.findViewById(R.id.booking);
            modelno=itemView.findViewById(R.id.modelno);
        }
        public  void setCarName(String CarName){
            TextView carName=(TextView)mView.findViewById(R.id.carName);
            carName.setText(CarName);
        }
        public void setCarBasePrice(long BasePrice){
            TextView basePrice=(TextView)mView.findViewById(R.id.price);
            basePrice.setText(String.valueOf(BasePrice)+"rs");
        }
        public void setQuantity(long Quantity){
            TextView quanity=(TextView)mView.findViewById(R.id.quantity);
            quanity.setText(String.valueOf(Quantity)+" Seater");
        }
        public void setImage(Context ctx,String imageUrl){
            GifImageView carImage=mView.findViewById(R.id.carImage);
            //carImage.setImageResource(R.drawable.loading1);
           Picasso.with(ctx).load(imageUrl).into(carImage);
        }
    }
    String priceCalculator(String kms,String basePrice,String perHead){
        double perHEad=Double.parseDouble(perHead);
        double KMs=Double.parseDouble(kms);
        double basePRice=Double.parseDouble(basePrice);
        DecimalFormat numberFormat=new DecimalFormat("#.00");
        if(KMs<=25) {
            return numberFormat.format(basePRice);
        }
        else {

            String price=numberFormat.format(basePRice + (KMs - 25) * perHEad);
            return price;
        }
    }
}
