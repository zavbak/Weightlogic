package ru.a799000.android.weightlogic.repository.realm;

import io.realm.DynamicRealm;
import io.realm.RealmSchema;


public class RealmMigration implements io.realm.RealmMigration  {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            schema.create("Product")
                    .addField("name",String.class);

            oldVersion++;
        }
    }
}
