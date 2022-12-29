package com.example.final_project_app.helpers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FBshortcut {
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();

    public static DatabaseReference refClients=FBDB.getReference("Clients");
    public static  DatabaseReference refBusiness = FBDB.getReference("Business");
}
