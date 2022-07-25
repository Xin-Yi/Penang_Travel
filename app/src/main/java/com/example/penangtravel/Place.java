package com.example.penangtravel;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Place {
    public static ArrayList<Place> place = new ArrayList<>();
    private static DatabaseReference ref;
    private String id, imageURL;
    private String name, address, contact, description, area;
    private String festival, food1, food2, hotel1, hotel2, medical1, transport1, transport2;
    private String favorite;

    public Place(String id, String imageURL, String name, String address, String contact, String description, String area, String festival, String food1,
                 String food2, String hotel1, String hotel2, String medical1, String transport1, String transport2, String favorite) {
        this.id = id;
        this.imageURL = imageURL;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.description = description;
        this.area = area;
        this.festival = festival;
        this.food1 = food1;
        this.food2 = food2;
        this.hotel1 = hotel1;
        this.hotel2 = hotel2;
        this.medical1 = medical1;
        this.transport1 = transport1;
        this.transport2 = transport2;
        this.favorite = favorite;
    }

    public Place(){
    }

    public static ArrayList<Place> getPlace() {
        return place;
    }

    public static void setPlace(ArrayList<Place> place) {
        Place.place = place;
    }

    //Search the selected place by ID
    public static Place searchPlaceById(String id) {
        for (Place p : place) {
            if (p.getId().equals(id))
                return p;
        }
        return null;
    }

    //Search the selected place by Name
    public static Place searchPlaceByName(String name) {
        for (Place p : place) {
            if (p.getName().equals(name))
                return p;
        }
        return null;
    }

    //Load the data for admin recycler view (Island)
    public static void loadAdminIslandPlace(final AdminPlaceAdapter pa, final ArrayList<Place> plc) {
        place.clear();
        ref = FirebaseDatabase.getInstance().getReference("Place");

        ref.orderByChild("area").equalTo("Island").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Get all places, and set the unique key as ID
                place.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Place p = dsp.getValue(Place.class);
                    p.setId(dsp.getKey());

                    place.add(p);
                }

                for (Place places : place) {
                    plc.add(places);
                }

                if (pa != null)
                    pa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Load the data for admin recycler view (Mainland)
    public static void loadAdminMainlandPlace(final AdminPlaceAdapter pa, final ArrayList<Place> plc) {
        place.clear();
        ref = FirebaseDatabase.getInstance().getReference("Place");

        ref.orderByChild("area").equalTo("Mainland").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Get all places, and set the unique key as ID
                place.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Place p = dsp.getValue(Place.class);
                    p.setId(dsp.getKey());

                    place.add(p);
                }

                for (Place places : place) {
                    plc.add(places);
                }

                if (pa != null)
                    pa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Load the data for user recycler view (Island)
    public static void loadUserIslandPlace(final UserPlaceAdapter pa, final ArrayList<Place> plc) {
        place.clear();
        ref = FirebaseDatabase.getInstance().getReference("Place");

        ref.orderByChild("area").equalTo("Island").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Get all places, and set the unique key as ID
                place.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Place p = dsp.getValue(Place.class);
                    p.setId(dsp.getKey());

                    place.add(p);
                }

                for (Place places : place) {
                    plc.add(places);
                }

                if (pa != null)
                    pa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Load the data for user recycler view (Mainland)
    public static void loadUserMainlandPlace(final UserPlaceAdapter pa, final ArrayList<Place> plc) {
        place.clear();
        ref = FirebaseDatabase.getInstance().getReference("Place");

        ref.orderByChild("area").equalTo("Mainland").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Get all places, and set the unique key as ID
                place.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Place p = dsp.getValue(Place.class);
                    p.setId(dsp.getKey());

                    place.add(p);
                }

                for (Place places : place) {
                    plc.add(places);
                }

                if (pa != null)
                    pa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Load the data for admin recycler view (Admin search keyword)
    public static void loadAdminSearchPlace(final AdminPlaceAdapter pa, final ArrayList<Place> plc, final String name) {
        place.clear();
        ref = FirebaseDatabase.getInstance().getReference("Place");
        Query query = ref.orderByChild("name").startAt(name).endAt(name + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Get all places, and set the unique key as ID
                place.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Place p = dsp.getValue(Place.class);
                    p.setId(dsp.getKey());

                    place.add(p);
                }

                for (Place places : place) {
                    plc.add(places);
                }

                if (pa != null)
                    pa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //Load the data for user recycler view (User search keyword)
    public static void loadUserSearchPlace(final UserPlaceAdapter pa, final ArrayList<Place> plc, final String name) {
        place.clear();
        ref = FirebaseDatabase.getInstance().getReference("Place");
        Query query = ref.orderByChild("name").startAt(name).endAt(name + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Get all places, and set the unique key as ID
                place.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Place p = dsp.getValue(Place.class);
                    p.setId(dsp.getKey());

                    place.add(p);
                }

                for (Place places : place) {
                    plc.add(places);
                }

                if (pa != null)
                    pa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Load the data for favorite list recycler view
    public static void loadFavoriteList(final FavoriteListAdapter pa, final ArrayList<Place> plc) {
        place.clear();
        ref = FirebaseDatabase.getInstance().getReference("Place");

        //Get only the data if the "favorite" variable is equal to yes
        ref.orderByChild("favorite").equalTo("yes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Get all places, and set the unique key as ID
                place.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Place p = dsp.getValue(Place.class);
                    p.setId(dsp.getKey());

                    place.add(p);
                }


                for (Place places : place) {
                        plc.add(places);
                }

                if (pa != null)
                    pa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFestival() {
        return festival;
    }

    public void setFestival(String festival) {
        this.festival = festival;
    }

    public String getFood1() {
        return food1;
    }

    public void setFood1(String food1) {
        this.food1 = food1;
    }

    public String getFood2() {
        return food2;
    }

    public void setFood2(String food2) {
        this.food2 = food2;
    }

    public String getHotel1() {
        return hotel1;
    }

    public void setHotel1(String hotel1) {
        this.hotel1 = hotel1;
    }

    public String getHotel2() {
        return hotel2;
    }

    public void setHotel2(String hotel2) {
        this.hotel2 = hotel2;
    }

    public String getMedical1() {
        return medical1;
    }

    public void setMedical1(String medical1) {
        this.medical1 = medical1;
    }

    public String getTransport1() {
        return transport1;
    }

    public void setTransport1(String transport1) {
        this.transport1 = transport1;
    }

    public String getTransport2() {
        return transport2;
    }

    public void setTransport2(String transport2) {
        this.transport2 = transport2;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }
}
