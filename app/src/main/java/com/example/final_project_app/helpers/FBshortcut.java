package com.example.final_project_app.helpers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FBshortcut {
    /**
     * @author		Harel Leibovich <hl9163@bs.amalnet.k12.il>
     * @version	1.0
     * @since		02/12/2022
     * Firebase shortcuts
     */

    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();

    public static DatabaseReference refClients=FBDB.getReference("Clients");
    public static  DatabaseReference refBusiness = FBDB.getReference("Business");
}
