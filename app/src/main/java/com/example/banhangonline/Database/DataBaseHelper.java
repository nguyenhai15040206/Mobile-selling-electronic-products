package com.example.banhangonline.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.banhangonline.Model.CartModel;

import java.util.ArrayList;
import java.util.List;

import bolts.UnobservedTaskException;

public class DataBaseHelper {
    private Context context;
    private String dbName = "ProductDB.db";

    public DataBaseHelper(Context context) {
        this.context = context;
    }

    private SQLiteDatabase openDB()
    {
        return context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE,null);
    }

    public void droptable()
    {
        SQLiteDatabase db = openDB();
        String query = "Drop table tblCart";
        db.execSQL(query);
        closeDB(db);
    }
    public void createDB()
    {
        SQLiteDatabase db = openDB();
        String query = "CREATE TABLE IF NOT EXISTS tblCart (" +"Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ProductCode INTEGER, ProductName TEXT, ProductImg TEXT, ProductAmount INTEGER," +
                " ProductUnitPrice DOUBLE, ProductSale DOUBLE, TotalMoney DOUBLE,Note TEXT,CategoryName TEXT,Status BOOLEAN )";
        db.execSQL(query);
        closeDB(db);
    }

    private void closeDB(SQLiteDatabase db)
    {
        db.close();
    }

    public void InsertProduct(int productCode, String productName, String productImg, int productAmount, double productUnitPrice,
                              double productSale, double totalMoney, String note,String categoryName,boolean status)
    {
        SQLiteDatabase sqLiteDatabase = openDB();
        ContentValues cv = new ContentValues();
        cv.put("ProductCode",productCode);
        cv.put("ProductName",productName);
        cv.put("ProductImg",productImg);
        cv.put("ProductAmount",productAmount);
        cv.put("ProductUnitPrice",productUnitPrice);
        cv.put("ProductSale",productSale);
        cv.put("TotalMoney",totalMoney);
        cv.put("Note", note);
        cv.put("CategoryName",categoryName);
        cv.put("Status", status);
        sqLiteDatabase.insert("tblCart",null,cv);
        closeDB(sqLiteDatabase);
    }

    public boolean UpdateAmountProduct(int productCode, int productAmount)
    {
        try{
            SQLiteDatabase sqLiteDatabase = openDB();
            String query = "Update tblCart " + "set ProductAmount= "+productAmount + " , TotalMoney = "+ productAmount +"*+ (ProductUnitPrice - ProductSale)" + "WHERE tblCart.ProductCode = "+productCode;
            sqLiteDatabase.execSQL(query);
            return true;
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

    // lấy tất cả các các sẩn phẩm với tình trạng là chưa thanh toán
    public List<CartModel> getAllProductsIn_tblCartWithStatus_false()
    {
        SQLiteDatabase db = openDB();
        List<CartModel> cartModelList = new ArrayList<>();
        String query = "select * from tblCart where Status = 0";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null)
        {
            if(cursor.moveToNext()){
                do {
                    int productCode = cursor.getInt(1);
                    String productName = cursor.getString(2);
                    String productImg = cursor.getString(3);
                    int productAmount = cursor.getInt(4);
                    double productUnitPrice = cursor.getDouble(5);
                    double productSale = cursor.getDouble(6);
                    String note = cursor.getString(8);
                    String categoryName = cursor.getString(9);
                    cartModelList.add(new CartModel(productCode,productName,productImg,productAmount, productUnitPrice, productSale,note,categoryName));
                }while (cursor.moveToNext());
            }
        }
        closeDB(db);
        return cartModelList;
    }

    public boolean DeleteProduct(int productCode)
    {
        try
        {
            SQLiteDatabase db = openDB();
            String query = "DELETE from tblCart where ProductCode = "+productCode;
            db.execSQL(query);
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean check_record_Product(int productCode)
    {
        boolean check = false;
        SQLiteDatabase db = openDB();
        String query = "SELECT * from tblCart where Status = 0 and ProductCode = "+productCode;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToNext())
        {
            check = true;
        }
        closeDB(db);
        return check;
    }



}
