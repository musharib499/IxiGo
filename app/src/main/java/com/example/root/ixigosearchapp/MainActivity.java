package com.example.root.ixigosearchapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.root.ixigosearchapp.ViewHolder.ViewHolderIxiGo;
import com.example.root.ixigosearchapp.model.Airlines;
import com.example.root.ixigosearchapp.model.Airports;
import com.example.root.ixigosearchapp.model.Appendix;
import com.example.root.ixigosearchapp.model.FairInfo;
import com.example.root.ixigosearchapp.model.Fare;
import com.example.root.ixigosearchapp.model.Flight;
import com.example.root.ixigosearchapp.model.Ixigo;
import com.example.root.ixigosearchapp.model.Providers;
import com.example.root.ixigosearchapp.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements IxiGoAdapter.BindAdapterListener<ViewHolderIxiGo> {
    private Ixigo ixiGoModel = null;
    private IxiGoAdapter<Flight ,ViewHolderIxiGo> adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= (RecyclerView) findViewById(R.id.recycle_list);
        loadData();


    }

    public void loadData() {

        try {
            JSONObject jasonObject = Util.loadJSONFromAsset(getApplicationContext(), "sample_flight_api_response");
            JSONObject jsonAppend = jasonObject.getJSONObject("appendix");
            Log.i("Main",jsonAppend.toString());
            ixiGoModel = new Ixigo();
            Appendix appendix = new Appendix();
          /*  for (int i = 0; i < jsonAppend.length(); i++) {
                JSONObject object = jsonAppend.getJSONObject(i);*/
          try {
                JSONObject jsonObject1 = jsonAppend.getJSONObject("airlines");
               /* for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);*/
                    Airlines airlines = new Airlines();
                    airlines.setSG(jsonObject1.getString("SG"));
                    airlines.setAI(jsonObject1.getString("AI"));
                    airlines.setG8(jsonObject1.getString("G8"));
                    airlines.set9W(jsonObject1.getString("9W"));
                    airlines.set6E(jsonObject1.getString("6E"));
                    appendix.setAirlines(airlines);

                }
          catch (JSONException e) {
              e.printStackTrace();
          }

          try {


                JSONObject jsonArray1 = jsonAppend.getJSONObject("airports");
              /*  for (int j = 0; j < jsonArray1.length(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);*/
                    Airports airlines = new Airports();
                    airlines.setBOM(jsonArray1.getString("BOM"));
                    airlines.setDEL(jsonArray1.getString("DEL"));
                    appendix.setAirports(airlines);


                }
              catch (JSONException e) {
                  e.printStackTrace();
              }
              try {
                  JSONObject jsonArray2 = jsonAppend.getJSONObject("providers");
                /*  for (int j = 0; j < jsonArray2.length(); j++) {
                      JSONObject jsonObject = jsonArray.getJSONObject(j);*/
                      Providers providers = new Providers();
                      providers.set1(jsonArray2.getString("1"));
                      providers.set2(jsonArray2.getString("2"));
                      providers.set3(jsonArray2.getString("3"));
                      providers.set4(jsonArray2.getString("4"));
                      appendix.setProviders(providers);



              }   catch (JSONException e) {
                  e.printStackTrace();
              }




        JSONArray jsonFlight = jasonObject.getJSONArray("flights");

            ArrayList<Flight> flights = new ArrayList<>();
            Flight flight = new Flight();
            for (int i = 0; i < jsonFlight.length(); i++) {
                JSONObject object = jsonFlight.getJSONObject(i);
                JSONArray jsonArray = object.getJSONArray("fares");
                ArrayList<Fare> fares = new ArrayList<>();
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);
                    Fare fare = new Fare();
                    fare.setFare(jsonObject.getInt("fare"));
                    fare.setProviderId(jsonObject.getInt("providerId"));

                    fares.add(fare);

                }
                flight.setFares(fares);
                flight.setOriginCode(object.getString("originCode"));
                flight.setDestinationCode(object.getString("destinationCode"));
                flight.setDepartureTime(object.getInt("departureTime"));
                flight.setArrivalTime(object.getInt("arrivalTime"));
                flight.setAirlineCode(object.getString("airlineCode"));
                flight.setClass_(object.getString("class"));
                flights.add(flight);
            }

            ixiGoModel.setAppendix(appendix);
            ixiGoModel.setFlights(flights);


        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        if (ixiGoModel!=null)
           setData();

    }
    public void setData() {
         adapter= new IxiGoAdapter<>(this, ixiGoModel.getFlights(), this, ViewHolderIxiGo.class, R.layout.item_layout);
         Util.recyclerView(recyclerView, this, true).setAdapter(adapter);
    }

    public static String parseTime(long milliseconds) {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliseconds),
                TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(milliseconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
    }

    @Override
    public void onBind(ViewHolderIxiGo holder, int position) {

   StringBuilder distanc = null;
        
    if (ixiGoModel.getFlights().get(position).getOriginCode().equalsIgnoreCase(ixiGoModel.getAppendix().getAirports().getBOM()))
        {
           distanc.append("From "+ixiGoModel.getAppendix().getAirports().getBOM().toString());
        }else {
        // distanc.append("From "+ixiGoModel.getAppendix().getAirports().getDEL().toString());
       }

        if (ixiGoModel.getFlights().get(position).getDestinationCode().equalsIgnoreCase(ixiGoModel.getAppendix().getAirports().getBOM()))
        {
            distanc.append(" To "+ixiGoModel.getAppendix().getAirports().getBOM());
        }else {
          //  distanc.append(" To "+ixiGoModel.getAppendix().getAirports().getDEL());
        }
        holder.tvxDistance.setText(distanc);
        holder.tvxArivTime.setText("Ari "+parseTime(ixiGoModel.getFlights().get(position).getArrivalTime())+" Dep"+parseTime(ixiGoModel.getFlights().get(position).getDepartureTime()));

        StringBuilder airLine = null;

        if (ixiGoModel.getFlights().get(position).getAirlineCode().equalsIgnoreCase(ixiGoModel.getAppendix().getAirlines().get6E()))
               holder.tvxAirLine.setText(ixiGoModel.getAppendix().getAirlines().get6E());

        if (ixiGoModel.getFlights().get(position).getAirlineCode().equalsIgnoreCase(ixiGoModel.getAppendix().getAirlines().get9W()))
            holder.tvxAirLine.setText(ixiGoModel.getAppendix().getAirlines().get9W());

        if (ixiGoModel.getFlights().get(position).getAirlineCode().equalsIgnoreCase(ixiGoModel.getAppendix().getAirlines().getAI()))
            holder.tvxAirLine.setText(ixiGoModel.getAppendix().getAirlines().getAI());

        if (ixiGoModel.getFlights().get(position).getAirlineCode().equalsIgnoreCase(ixiGoModel.getAppendix().getAirlines().getG8()))
            holder.tvxAirLine.setText(ixiGoModel.getAppendix().getAirlines().getG8());

        if (ixiGoModel.getFlights().get(position).getAirlineCode().equalsIgnoreCase(ixiGoModel.getAppendix().getAirlines().getSG()))
            holder.tvxAirLine.setText(ixiGoModel.getAppendix().getAirlines().getSG());

        holder.tvxProvider.setText(getProvider(position).airline);

        holder.tvxPrice.setText(getProvider(position).price);

    }

    public FairInfo getProvider(int position) {
       FairInfo fairInfo=new FairInfo();
        for (int i = 0; i < ixiGoModel.getFlights().get(position).getFares().size(); i++) {


            if (ixiGoModel.getFlights().get(position).getFares().get(i).toString().equalsIgnoreCase(ixiGoModel.getAppendix().getProviders().get1())) {
                fairInfo.airline += ixiGoModel.getAppendix().getProviders().get1();
                fairInfo.price+=ixiGoModel.getFlights().get(position).getFares().get(i).getFare().toString();
            }

            if (ixiGoModel.getFlights().get(position).getFares().get(i).toString().equalsIgnoreCase(ixiGoModel.getAppendix().getProviders().get2()))
            {
                fairInfo.airline += ixiGoModel.getAppendix().getProviders().get2();
                fairInfo.price=ixiGoModel.getFlights().get(position).getFares().get(i).getFare().toString();
            }

            if (ixiGoModel.getFlights().get(position).getFares().get(i).toString().equalsIgnoreCase(ixiGoModel.getAppendix().getProviders().get3()))
            {
                fairInfo.airline += ixiGoModel.getAppendix().getProviders().get3();
                fairInfo.price+=ixiGoModel.getFlights().get(position).getFares().get(i).getFare().toString();
            }

            if (ixiGoModel.getFlights().get(position).getFares().get(i).toString().equalsIgnoreCase(ixiGoModel.getAppendix().getProviders().get4()))
            {
                fairInfo.airline += ixiGoModel.getAppendix().getProviders().get4();
                fairInfo.price+=ixiGoModel.getFlights().get(position).getFares().get(i).getFare().toString();
            }

        }
        return fairInfo;
    }

}


