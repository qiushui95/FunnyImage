package me.yangcx.funnyimage.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration

class Migration1To2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `temp` (`index` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `id` TEXT NOT NULL, `thumb` TEXT NOT NULL, `small` TEXT NOT NULL, `regular` TEXT NOT NULL, `full` TEXT NOT NULL, `raw` TEXT NOT NULL);")
        database.execSQL("insert into `temp`(id,thumb,small,regular,full,raw) select * from ImageInfo;")
        database.execSQL("drop table ImageInfo;")
        database.execSQL("alter table `temp` rename to ImageInfo;")
    }
}