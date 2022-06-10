//package com.android.warehousemanager;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import androidx.annotation.Nullable;
//
//import com.android.warehousemanager.models.ChiTietPhieuNhap;
//import com.android.warehousemanager.models.Kho;
//import com.android.warehousemanager.models.PhieuNhap;
//import com.android.warehousemanager.models.VatTu;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
//public class DatabaseOpenHelper extends SQLiteOpenHelper {
//    private static final int DATABASE_VERSION = 1;
//    private static final String KHO_TABLE = "KHO";
//    private static final String DATABASE_NAME = "QUANLYNHAPKHO";
//    private static final String PHIEUNHAP_TABLE = "PHIEUNHAP";
//    private static final String COLUMN_CHITIETPHIEUNHAP_SOPHIEU = "SOPHIEU";
//    private static final String COLUMN_PHIEUNHAP_SOPHIEU = COLUMN_CHITIETPHIEUNHAP_SOPHIEU;
//    private static final String COLUMN_PHIEUNHAP_NGAYLAP = "NGAYLAP";
//    private static final String COLUMN_KHO_MAKHO = "MAKHO";
//    private static final String COLUMN_PHIEUNHAP_MAKHO = COLUMN_KHO_MAKHO;
//    private static final String COLUMN_KHO_TENKHO = "TENKHO";
//    private static final String VATTU_TABLE = "VATTU";
//    private static final String COLUMN_CHITIETPHIEUNHAP_MAVATTU = "MAVATTU";
//    private static final String COLUMN_VATTU_MAVATTU = COLUMN_CHITIETPHIEUNHAP_MAVATTU;
//    private static final String COLUMN_VATTU_TENVATTU = "TENVATTU";
//    private static final String COLUMN_VATTU_XUATXU = "XUATXU";
//    private static final String CHITIETPHIEUNHAP_TABLE = "CHITIETPHIEUNHAP";
//    private static final String COLUMN_CHITIETPHIEUNHAP_DONVITINH = "DONVITINH";
//    private static final String COLUMN_CHITIETPHIEUNHAP_SOLUONG = "SOLUONG";
//
//    public DatabaseOpenHelper(@Nullable Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        String createTableStatement;
//        createTableStatement = "CREATE TABLE " + KHO_TABLE + " (" + COLUMN_KHO_MAKHO
//                + " TEXT PRIMARY KEY, " + COLUMN_KHO_TENKHO + " TEXT NOT NULL)";
//
//        sqLiteDatabase.execSQL(createTableStatement);
//        createTableStatement = "CREATE TABLE " + VATTU_TABLE + " (" + COLUMN_VATTU_MAVATTU
//                + " TEXT PRIMARY KEY, " + COLUMN_VATTU_TENVATTU + " TEXT NOT NULL, "
//                + COLUMN_VATTU_XUATXU + " TEXT NOT NULL)";
//        sqLiteDatabase.execSQL(createTableStatement);
//
//        createTableStatement = "CREATE TABLE " + PHIEUNHAP_TABLE +
//                " (" + COLUMN_PHIEUNHAP_SOPHIEU + " INTEGER PRIMARY KEY, "
//                + COLUMN_PHIEUNHAP_NGAYLAP + " TEXT NOT NULL,"
//                + COLUMN_PHIEUNHAP_MAKHO + " TEXT NOT NULL REFERENCES " + KHO_TABLE + "(" + COLUMN_KHO_MAKHO + ")" +
//                " ON DELETE CASCADE);";
//        sqLiteDatabase.execSQL(createTableStatement);
//
//        createTableStatement = "CREATE TABLE " + CHITIETPHIEUNHAP_TABLE
//                + " (" + COLUMN_CHITIETPHIEUNHAP_SOPHIEU + " INTEGER REFERENCES "
//                + PHIEUNHAP_TABLE + " (" + COLUMN_PHIEUNHAP_SOPHIEU + "), "
//                + COLUMN_CHITIETPHIEUNHAP_MAVATTU + " TEXT REFERENCES "
//                + VATTU_TABLE + "( "+ COLUMN_VATTU_MAVATTU + " ), "
//                + COLUMN_CHITIETPHIEUNHAP_DONVITINH + " TEXT NOT NULL, "
//                + COLUMN_CHITIETPHIEUNHAP_SOLUONG + " INTEGER NOT NULL, PRIMARY KEY ("
//                + COLUMN_CHITIETPHIEUNHAP_SOPHIEU + ", " + COLUMN_CHITIETPHIEUNHAP_MAVATTU + "))";
//        sqLiteDatabase.execSQL(createTableStatement);
//    }
//
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//
//    }
//
//    //=============================================================Kho
//    public ArrayList<Kho> getAllKho() {
//        ArrayList<Kho> data = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        String sql = "SELECT * FROM " + KHO_TABLE + " ORDER BY MAKHO ASC";
//        Cursor cursor = db.rawQuery(sql, null);
//        if (cursor.moveToFirst()) {
//            do {
//                Kho kho = new Kho();
//                kho.setMaKho(cursor.getString(0));
//                kho.setTenKho(cursor.getString(1));
//                data.add(kho);
//            }
//            while (cursor.moveToNext());
//        }
//        return data;
//    }
//
//    public ArrayList<String> getListKho() {
//        ArrayList<String> data = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        String sql = "SELECT * FROM " + KHO_TABLE + " ORDER BY MAKHO ASC";
//        Cursor cursor = db.rawQuery(sql, null);
//        if (cursor.moveToFirst()) {
//            do {
//                Kho kho = new Kho();
//                kho.setMaKho(cursor.getString(0));
//                kho.setTenKho(cursor.getString(1));
//                data.add(kho.getMaKho() + " - " + kho.getTenKho());
//            }
//            while (cursor.moveToNext());
//        }
//        return data;
//    }
//
//    public void addKho(Kho kho){
//        String sql = "insert into " + KHO_TABLE +" values(?,?)";
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL(sql, new String[]{kho.getMaKho(), kho.getTenKho()});
//        db.close();
//    }
//
//    public void suaKho(Kho kho)
//    {
//        String sql=" UPDATE " + KHO_TABLE + " SET TENKHO =? WHERE MAKHO =? ";
//        SQLiteDatabase database = this.getWritableDatabase();
//        database.execSQL(sql, new String[]{ kho.getTenKho(),kho.getMaKho()});
//        database.close();
////        SQLiteDatabase db = this.getWritableDatabase();
////        ContentValues contentValues = new ContentValues();
////        contentValues.put(COL_NAME,kho.getName());
////        contentValues.put(COL_ADDRESS,kho.getAddress());
////        db.update(TABLE_KHO,contentValues,COL_ID +" = ? ",new String[]{kho.getId()});
////        db.close();
//    }
//
//    public void xoaKho(Kho kho)
//    {
//        String sql="delete from "+ KHO_TABLE +"  where MAKHO = ?";
//        SQLiteDatabase database = this.getWritableDatabase();
//        database.execSQL(sql, new String[]{ kho.getMaKho() });
//        database.close();
//    }
//
//    public Kho getKhoByID(String id){
//        Kho kho = new Kho();
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "SELECT * FROM " + KHO_TABLE + " WHERE MAKHO = ? ";
//        Cursor cursor = db.rawQuery(sql, new String[]{id});
//        cursor.moveToFirst();
//        kho.setMaKho(cursor.getString(0));
//        kho.setTenKho(cursor.getString(1));
//        return kho;
//    }
//
//    public boolean checkKhoByID(String id){
//        Kho kho = new Kho();
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "SELECT * FROM " + KHO_TABLE + " WHERE MAKHO = ? ";
//        Cursor cursor = db.rawQuery(sql, new String[]{id});
//        if (cursor.moveToFirst()){
//            return true;
//        } else{
//            return false;
//        }
//
//    }
//
//    public ArrayList<Kho> searchByName(String name) {
//        ArrayList<Kho> data = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        String sql = "SELECT * FROM " + KHO_TABLE + "  WHERE TENKHO LIKE '"+ name +"%' ";
//        Cursor cursor = db.rawQuery(sql, null);
//        if (cursor.moveToFirst()) {
//            do {
//                Kho kho = new Kho();
//                kho.setMaKho(cursor.getString(0));
//                kho.setTenKho(cursor.getString(1));
//                data.add(kho);
//            }
//            while (cursor.moveToNext());
//        }
//        return data;
//    }
//
//
//    //==============================================================================================================Phieu nhap and chi tiet phieu nhap
//
//    public List<PhieuNhap> getDataPhieuNhap(String findMaKho){
//        List<PhieuNhap> data = new LinkedList<>();
//        String query = "SELECT * FROM " + PHIEUNHAP_TABLE + " WHERE " + COLUMN_PHIEUNHAP_MAKHO + " LIKE '" + findMaKho + "%' ";
//        SQLiteDatabase database = this.getReadableDatabase();
//        Cursor cursor = database.rawQuery(query,null);
//        if(cursor.moveToFirst()){
//            do{
//                int soPhieu = cursor.getInt(0);
//                String maKho = cursor.getString(2);
//                String ngayLap = cursor.getString(1);
//                PhieuNhap value = new PhieuNhap(soPhieu,ngayLap,"N",maKho);
//                data.add(value);
//            }while (cursor.moveToNext());
//        }else {
//
//        }
//        cursor.close();
//        database.close();
//        return data;
//    }
//
//    public boolean addPhieuNhap(PhieuNhap value, List<ChiTietPhieuNhap> data){
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_PHIEUNHAP_SOPHIEU,value.getSoPhieu());
//        values.put(COLUMN_PHIEUNHAP_MAKHO,value.getMaKho());
//        values.put(COLUMN_PHIEUNHAP_NGAYLAP,value.getNgayLap());
//        long insert = database.insert(PHIEUNHAP_TABLE,null,values);
//        for (ChiTietPhieuNhap item: data) {
//            addChiTietPhieuNhapFromPhieuNhap(item);
//        }
//        if(insert == -1){
//            return false;
//        }else{
//            return true;
//        }
//    }
//
//    private void addChiTietPhieuNhapFromPhieuNhap(ChiTietPhieuNhap item) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_CHITIETPHIEUNHAP_SOPHIEU,item.getSoPhieu());
//        values.put(COLUMN_CHITIETPHIEUNHAP_MAVATTU,item.getMaVatTu());
//        values.put(COLUMN_CHITIETPHIEUNHAP_SOLUONG,item.getSoLuong());
//        database.insert(CHITIETPHIEUNHAP_TABLE,null,values);
//        database.close();
//    }
//
//    public boolean addChiTietPhieuNhap(ChiTietPhieuNhap item) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_CHITIETPHIEUNHAP_SOPHIEU,item.getSoPhieu());
//        values.put(COLUMN_CHITIETPHIEUNHAP_MAVATTU,item.getMaVatTu());
//        values.put(COLUMN_CHITIETPHIEUNHAP_SOLUONG,item.getSoLuong());
//        long insert = database.insert(CHITIETPHIEUNHAP_TABLE,null,values);
//        if (insert == -1){
//            return false;
//        }else{
//            return true;
//        }
//    }
//
//    public List<PhieuNhap> getDataPhieuNhap(){
//        List<PhieuNhap> data = new LinkedList<>();
//        String query = "SELECT * FROM " + PHIEUNHAP_TABLE;
//        SQLiteDatabase database = this.getReadableDatabase();
//        Cursor cursor = database.rawQuery(query,null);
//        if(cursor.moveToFirst()){
//            do{
//                int soPhieu = cursor.getInt(0);
//                String maKho = cursor.getString(2);
//                String ngayLap = cursor.getString(1);
//                PhieuNhap value = new PhieuNhap(soPhieu,maKho,ngayLap);
//                data.add(value);
//            }while (cursor.moveToNext());
//        }else {
//
//        }
//        cursor.close();
//        database.close();
//        return data;
//    }
//
//    public List<ChiTietPhieuNhap> getDataChiTietPhieuNhap(int soPhieu_input){
//        List<ChiTietPhieuNhap> data = new LinkedList<>();
//        String query = "SELECT * FROM " + CHITIETPHIEUNHAP_TABLE;
//        SQLiteDatabase database = this.getReadableDatabase();
//        Cursor cursor = database.rawQuery(query,null);
//        if(cursor.moveToFirst()){
//            do{
//                int soPhieu = cursor.getInt(0);
//                String maVatTu = cursor.getString(1);
//                int soLuong = cursor.getInt(3);
//                String donViTinh = cursor.getString(2);
//                ChiTietPhieuNhap value = new ChiTietPhieuNhap(soPhieu,maVatTu,soLuong,donViTinh);
//                if(value.getSoPhieu() == soPhieu_input){
//                    data.add(value);
//                }
//            }while (cursor.moveToNext());
//        }else {
//
//        }
//        cursor.close();
//        database.close();
//        return data;
//    }
//
//    public int nextSoPhieu(){
//        SQLiteDatabase database = this.getReadableDatabase();
//        String query = "SELECT MAX ( " + COLUMN_PHIEUNHAP_SOPHIEU +") FROM " + PHIEUNHAP_TABLE;
//        Cursor cursor = database.rawQuery(query,null);
//        if(cursor.moveToFirst()){
//            return  cursor.getInt(0) + 1;
//        }else{
//            return -1;
//        }
//    }
//
//    public boolean deletePhieuNhap(PhieuNhap value){
//        SQLiteDatabase database = this.getWritableDatabase();
//        long delete = database.delete(PHIEUNHAP_TABLE,COLUMN_PHIEUNHAP_SOPHIEU + " = ?",
//                new String[]{String.valueOf(value.getSoPhieu())});
//        if (delete == -1){
//            return false;
//        }else{
//            return true;
//        }
//    }
//
//    public boolean deleteAllChiTietPhieuNhap(int soPhieu){
//        SQLiteDatabase database = this.getWritableDatabase();
//        long delete = database.delete(CHITIETPHIEUNHAP_TABLE,COLUMN_CHITIETPHIEUNHAP_SOPHIEU + " = ?",
//                new String[]{String.valueOf(soPhieu)});
//        if (delete == -1){
//            return false;
//        }else{
//            return true;
//        }
//    }
//
//    public boolean deleteChiTietPhieuNhap(ChiTietPhieuNhap value){
//        SQLiteDatabase database = this.getWritableDatabase();
//        long delete = database.delete(CHITIETPHIEUNHAP_TABLE,COLUMN_CHITIETPHIEUNHAP_SOPHIEU
//                        + " = ? AND " + COLUMN_CHITIETPHIEUNHAP_MAVATTU + " = ?",
//                new String[]{String.valueOf(value.getSoPhieu()), value.getMaVatTu()});
//        if (delete == -1){
//            return false;
//        }else{
//            return true;
//        }
//    }
//
//    public boolean updatePhieuNhap(PhieuNhap value) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_PHIEUNHAP_MAKHO,value.getMaKho());
//        values.put(COLUMN_PHIEUNHAP_NGAYLAP,value.getNgayLap());
//        long update = database.update(PHIEUNHAP_TABLE,values,
//                COLUMN_PHIEUNHAP_SOPHIEU + " = ?",new String[]{String.valueOf(value.getSoPhieu())});
//        if (update == -1){
//            return false;
//        }else{
//            return true;
//        }
//    }
//
//    public boolean updateChiTietPhieuNhap(ChiTietPhieuNhap value) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_CHITIETPHIEUNHAP_SOLUONG,value.getSoLuong());
//        long update = database.update(CHITIETPHIEUNHAP_TABLE,values,
//                COLUMN_CHITIETPHIEUNHAP_SOPHIEU + " = ? AND "
//                + COLUMN_CHITIETPHIEUNHAP_MAVATTU + " = ? ",
//                new String[]{String.valueOf(value.getSoPhieu()), value.getMaVatTu()});
//        if (update == -1){
//            return false;
//        }else{
//            return true;
//        }
//    }
//
//    public boolean checkMaVatTu(String maVatTu) {
//        SQLiteDatabase database = this.getReadableDatabase();
//        String query = "SELECT * FROM " + VATTU_TABLE + " WHERE " + COLUMN_VATTU_MAVATTU + " = '" + maVatTu + "'";
//        Cursor cursor = database.rawQuery(query, null);
//        if(cursor.moveToFirst()){
//            return true;
//        }else {
//            return false;
//        }
//    }
//
//    public boolean checkMaKho(String maKho) {
//        SQLiteDatabase database = this.getReadableDatabase();
//        String query = "SELECT * FROM " + KHO_TABLE + " WHERE " + COLUMN_KHO_MAKHO + " = '" + maKho + "'";
//        Cursor cursor = database.rawQuery(query, null);
//        if(cursor.moveToFirst()){
//            return true;
//        }else {
//            return false;
//        }
//    }
//
//
//    //======================================================================================================================================Vat tu
//    public void themVatTu(VatTu vatTu) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_VATTU_MAVATTU, vatTu.getMaVT());
//        values.put(COLUMN_VATTU_TENVATTU, vatTu.getTenVT());
//        values.put(COLUMN_VATTU_XUATXU, vatTu.getXuatXu());
//        db.insert(VATTU_TABLE, null, values);
//        db.close();
//    }
//
//    public int xoaVatTu(String mavt){
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(VATTU_TABLE,COLUMN_VATTU_MAVATTU+"=?",new String[] {String.valueOf(mavt)});
//    }
//
//    public void suaVatTu(String mavt, String tenvt, String xuatxu){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_VATTU_MAVATTU,mavt);
//        contentValues.put(COLUMN_VATTU_TENVATTU,tenvt);
//        contentValues.put(COLUMN_VATTU_XUATXU,xuatxu);
//        db.update(VATTU_TABLE,contentValues,COLUMN_VATTU_MAVATTU+"=?",new String[]{mavt});
//        db.close();
//    }
//
//    public List<VatTu> getAllVatTu() {
//        List<VatTu> listVatTu = new ArrayList<>();
//
//        String selectQuery = "SELECT * FROM " + VATTU_TABLE;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery,null);
//        if (cursor.moveToFirst()) {
//            do {
//                VatTu vatTu = new VatTu();
//                vatTu.setMaVT(cursor.getString(0));
//                vatTu.setTenVT(cursor.getString(1)+"");
//                vatTu.setXuatXu(cursor.getString(2));
//                listVatTu.add(vatTu);
//
//            } while (cursor.moveToNext());
//        }
//        db.close();
//        return listVatTu;
//    }
//
//    public List<VatTu> getAllVatTu(String find) {
//        List<VatTu> listVatTu = new ArrayList<>();
//
//        String selectQuery = "SELECT * FROM " + VATTU_TABLE  + " WHERE " + COLUMN_VATTU_TENVATTU + " LIKE '" + find + "%' ";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery,null);
//        if (cursor.moveToFirst()) {
//            do {
//                VatTu vatTu = new VatTu();
//                vatTu.setMaVT(cursor.getString(0));
//                vatTu.setTenVT(cursor.getString(1)+"");
//                vatTu.setXuatXu(cursor.getString(2));
//                listVatTu.add(vatTu);
//
//            } while (cursor.moveToNext());
//        }
//        db.close();
//        return listVatTu;
//    }
//
//    public List<String> getListVatTu() {
//        List<String> returnList = new ArrayList<>();
//
//        String selectQuery = "SELECT * FROM " + VATTU_TABLE;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery,null);
//        if (cursor.moveToFirst()) {
//            do {
//                VatTu vatTu = new VatTu();
//                vatTu.setMaVT(cursor.getString(0));
//                vatTu.setTenVT(cursor.getString(1));
//                vatTu.setXuatXu(cursor.getString(2));
//                String value = vatTu.getMaVT() + " - " + vatTu.getTenVT();
//                returnList.add(value);
//
//            } while (cursor.moveToNext());
//        }
//        db.close();
//        return returnList;
//    }
//}
