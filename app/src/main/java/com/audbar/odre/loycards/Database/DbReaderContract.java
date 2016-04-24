package com.audbar.odre.loycards.Database;

import android.provider.BaseColumns;

/**
 * Created by Audrius on 2016-04-04.
 */
public final class DbReaderContract{

    public DbReaderContract(){}

    public static abstract class UsersCardsDb implements BaseColumns{
        public static final String TABLE_NAME = "users_cards";
        public static final String COLUMN_NAME_DATE_REGISTERED = "date_registered";
        public static final String COLUMN_NAME_LOY_CARD_TYPE_ID = "loy_card_type_id";
        public static final String COLUMN_NAME_LOY_CARD_TYPE = "loy_card_type";
        public static final String COLUMN_NAME_CARD_NUMBER = "card_number";
        public static final String COLUMN_NAME_USER_NAME = "user_name";
        public static final String COLUMN_NAME_USER_BIRTH_DATE = "birth_date";
        public static final String COLUMN_NAME_PASSWORD = "password"; //todo: hash
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_IMAGE_URL = "image_url";
    }

    public static abstract class LoyaltyCardTypesDb implements BaseColumns{
        public static final String TABLE_NAME = "loyalty_card_types";
        public static final String COLUMN_NAME_DATE_CREATED = "date_created";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_IMAGE_URL = "image_url";

    }

    public static abstract class UsersDb implements BaseColumns{
        public static final String TABLE_NAME = "app_users";
        public static final String COLUMN_NAME_DATE_CREATED = "date_created";
        public static final String COLUMN_NAME_DEVICE_ID = "device_id";
        public static final String COLUMN_NAME_USER_NAME = "user_name";
        public static final String COLUMN_NAME_USER_LAST_NAME = "user_last_name";
        public static final String COLUMN_NAME_IMAGE_URL = "image_url";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_GOOGLE_ID = "google_id";

    }

}
