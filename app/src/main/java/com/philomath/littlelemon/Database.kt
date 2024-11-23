package com.philomath.littlelemonimport android.content.Contextimport androidx.room.Daoimport androidx.room.Databaseimport androidx.room.Entityimport androidx.room.Insertimport androidx.room.PrimaryKeyimport androidx.room.Queryimport androidx.room.Roomimport androidx.room.RoomDatabase@Entity(tableName = "menu_items")data class MenuItem(      @PrimaryKey(autoGenerate = true)      val id: Int = 0,      val name: String,      val description: String,      val price: Double,      val image: String? = null,      val category : String,)@Daointerface MenuItemDao {      @Insert      fun insertMenuItems(menuItems: List<MenuItemNetwork>)      @Query("SELECT * FROM menu_items")      suspend fun getAllMenuItems(): List<MenuItemNetwork>}@Database(entities = [MenuItem::class], version = 1, exportSchema = false)abstract class AppDatabase : RoomDatabase() {      abstract fun menuItemDao(): MenuItemDao      companion object {            @Volatile            private var INSTANCE: AppDatabase? = null            private val LOCK = Any()            operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {                  INSTANCE ?: createDatabase(context).also {                        INSTANCE = it                  }            }            private fun createDatabase(context: Context) = Room.databaseBuilder(                  context.applicationContext,                  AppDatabase::class.java,                  "menu_database"            ).build()//            fun getDatabase(context: Context): AppDatabase {//                  return INSTANCE ?: synchronized(this) {//                        val instance = Room.databaseBuilder(//                              context.applicationContext,//                              AppDatabase::class.java,//                              "menu_database"//                        ).build()//                        INSTANCE = instance//                        instance//                  }//            }      }}