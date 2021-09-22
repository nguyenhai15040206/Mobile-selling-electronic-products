package com.example.banhangonline.CustomAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhangonline.API.ApiService;
import com.example.banhangonline.Activity.CartActivity;
import com.example.banhangonline.Activity.LaptopDetailsActivity;
import com.example.banhangonline.Activity.PhoneDetailsActivity;
import com.example.banhangonline.Database.DataBaseHelper;
import com.example.banhangonline.Model.CartModel;
import com.example.banhangonline.Model.ConvertStringToBitmapFromAsscess;
import com.example.banhangonline.Model.Product;
import com.example.banhangonline.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private DataBaseHelper dataBaseHelper;
    private CartModel cartModel;
    int slTon = 0;
    private boolean isCheckedAll = false;
    double tongTien = 0;
    double sumMoneyAllProducts = 0.0;
    private DecimalFormat decimalFormat = new DecimalFormat("#0,000");
    private Product productByCode;

    public void selectAll(){
        isCheckedAll = true;
        notifyDataSetChanged();
    }
    public void unselectAll(){
        isCheckedAll = false;
        notifyDataSetChanged();
    }
    public CartAdapter(Context context) {
        this.context = context;
        dataBaseHelper = new DataBaseHelper(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        cartModel = CartActivity.cartModelList.get(position);
        productByCode = new Product();
        setDataToGUI(holder);
        if(!isCheckedAll){
            holder.ckbChoseProduct.setChecked(false);
        }
        else{
            holder.ckbChoseProduct.setChecked(true);
        }
        holder.edtAmount.setText(String.valueOf(cartModel.getProductAmount()));
        ckbChoseAllProducClick(holder);
        btnPlusClick(holder, position);
        btnMinusClick(holder, position);
        tvRemoveProductClick(holder,position);
        ckbChoseProductCLick(holder, position);
        btnRemoveAllProductCart();
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productByCode = new Product();
                callApiProductByCode(CartActivity.cartModelList.get(position).getProductCode());
            }
        });
    }
    
    public void btnRemoveAllProductCart() {
        CartActivity.tvRemoveAllProductsCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Thông báo!");
                dialog.setMessage("Xóa tất cả sản phẩm trong giả hàng?");
                dialog.setCancelable(false);
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(context,"Ok Bạn",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isRemoveAll = false;
                        for (int i = 0; i < CartActivity.cartModelList.size(); i++) {
                            if (dataBaseHelper.DeleteProduct(CartActivity.cartModelList.get(i).productCode)) {
                                isRemoveAll = true;
                                continue;
                            } else {
                                isRemoveAll = false;
                                return;
                            }
                        }
                        if (isRemoveAll) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            CartActivity.cartModelList = dataBaseHelper.getAllProductsIn_tblCartWithStatus_false();
                            CartAdapter.this.notifyDataSetChanged();
                            CartActivity.listProductDoApriori.clear();
                            CartActivity.aprioriAdapter.notifyDataSetChanged();
                            CartActivity.layoutByDoApriori.setVisibility(View.GONE);
                            if (CartActivity.cartModelList.isEmpty()) {
                                CartActivity.layoutChoseAllProduct.setVisibility(View.GONE);
                                CartActivity.layoutSumMoeny.setVisibility(View.GONE);
                                CartActivity.bottomNavigationView.setVisibility(View.GONE);
                                CartActivity.linearLayout_NoProducts.setVisibility(View.VISIBLE);
                            } else {
                                CartActivity.tvCountProductCart.setText("(" + CartActivity.cartModelList.size() + " sản phẩm)");
                                CartActivity.layoutChoseAllProduct.setVisibility(View.VISIBLE);
                                CartActivity.linearLayout_NoProducts.setVisibility(View.GONE);
                                CartActivity.layoutSumMoeny.setVisibility(View.VISIBLE);
                                CartActivity.bottomNavigationView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(context, "Có một vài sự cố nhỏ bạn vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                dialog.show();

            }
        });
    }

    public void setDataToGUI(ViewHolder holder) {
        DecimalFormat decimalFormat = new DecimalFormat("#0,000");
        if (cartModel == null)
            return;
        Picasso.get().load(cartModel.productImg).error(R.drawable.notfound).into(holder.imageView);
        //holder.imageView.setImageBitmap(ConvertStringToBitmapFromAsscess.convertStringToBitmap(context,cartModel.productImg));
        holder.tvProductName.setText(cartModel.productName);
        if (cartModel.getProductSale() != 0) {
            holder.tvUnitPriceCart.setText("" + decimalFormat.format(cartModel.productAmount * (cartModel.productUnitPrice - cartModel.productSale)) + "đ");
            holder.tvProductCartSale.setPaintFlags(holder.tvProductCartSale.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvProductCartSale.setText("" + decimalFormat.format(cartModel.productAmount * cartModel.productUnitPrice) + "đ");
        } else {
            holder.tvUnitPriceCart.setText("" + decimalFormat.format(cartModel.productAmount * cartModel.productUnitPrice) + "đ");
            holder.tvProductCartSale.setText("");
        }
    }

    public void ckbChoseAllProducClick(ViewHolder holder) {
        CartActivity.ckbChoseProductAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CartActivity.listProductsSelect = new ArrayList<>();
                double tongTien = 0.0;
                if (CartActivity.ckbChoseProductAll.isChecked()) {
                    CartAdapter.this.selectAll();
                    /*for (int i = 0; i < CartActivity.cartModelList.size(); i++) {
                       tongTien += CartActivity.cartModelList.get(i).productAmount * (CartActivity.cartModelList.get(i).productUnitPrice - CartActivity.cartModelList.get(i).productSale);
                        CartActivity.listProductsSelect.add(CartActivity.cartModelList.get(i));
                    }
                    CartActivity.tvSumMoney.setText(decimalFormat.format(tongTien) + "đ");*/
                }
                if (!CartActivity.ckbChoseProductAll.isChecked()) {
                    CartAdapter.this.unselectAll();
                    //CartActivity.listProductsSelect.clear();
                    tongTien = 0;
                    CartActivity.tvSumMoney.setText("0đ");
                }
            }
        });
    }

    public void ckbChoseProductCLick(ViewHolder holder, int position) {
        holder.ckbChoseProduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.ckbChoseProduct.isChecked()) {
                    tongTien += CartActivity.cartModelList.get(position).productAmount * (CartActivity.cartModelList.get(position).productUnitPrice - CartActivity.cartModelList.get(position).getProductSale());
                    CartActivity.listProductsSelect.add(CartActivity.cartModelList.get(position));
                    CartActivity.tvSumMoney.setText(decimalFormat.format(tongTien) + "đ");
                } else {
                    tongTien -= CartActivity.cartModelList.get(position).productAmount * (CartActivity.cartModelList.get(position).productUnitPrice - CartActivity.cartModelList.get(position).getProductSale());
                    if(tongTien<=0){
                            tongTien = 0;
                            CartActivity.tvSumMoney.setText("0đ");
                            CartActivity.listProductsSelect.clear();
                    }else {
                        CartActivity.tvSumMoney.setText(decimalFormat.format(tongTien) + "đ");
                        CartActivity.listProductsSelect.remove(CartActivity.cartModelList.get(position));
                    }
                }
            }
        });
    }

    public void tvRemoveProductClick(ViewHolder holder,int position) {
        holder.tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartModel = CartActivity.cartModelList.get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Thông báo!");
                dialog.setMessage("Bạn có chắc muốn xóa " + cartModel.getProductName() + " Ra khỏi giỏ hàng không?");
                dialog.setCancelable(false);
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(context,"Ok Bạn",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dataBaseHelper.DeleteProduct(cartModel.productCode)) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            CartActivity.cartModelList = dataBaseHelper.getAllProductsIn_tblCartWithStatus_false();
                            CartAdapter.this.notifyDataSetChanged();
                            CartActivity.listProductDoApriori.clear();
                            DoApriori();
                            CartActivity.aprioriAdapter.notifyDataSetChanged();
                            if (CartActivity.cartModelList.isEmpty()) {
                                CartActivity.layoutChoseAllProduct.setVisibility(View.GONE);
                                CartActivity.layoutSumMoeny.setVisibility(View.GONE);
                                CartActivity.bottomNavigationView.setVisibility(View.GONE);
                                CartActivity.linearLayout_NoProducts.setVisibility(View.VISIBLE);
                            } else {
                                CartActivity.tvCountProductCart.setText("(" + CartActivity.cartModelList.size() + " sản phẩm)");
                                CartActivity.layoutChoseAllProduct.setVisibility(View.VISIBLE);
                                CartActivity.linearLayout_NoProducts.setVisibility(View.GONE);
                                CartActivity.layoutSumMoeny.setVisibility(View.VISIBLE);
                                CartActivity.bottomNavigationView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    public void btnMinusClick(ViewHolder holder, int position) {
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ma = CartActivity.cartModelList.get(position).productCode;
                for(int i=0; i< CartActivity.listProductByCode.size();i++){
                    int maold =CartActivity.listProductByCode.get(i).getMaSanPham();
                    if(ma == maold){
                        slTon =   CartActivity.listProductByCode.get(i).soLuong;
                    }
                }
                int soLuongMinus = Integer.parseInt(holder.edtAmount.getText().toString()) - 1;
                if (soLuongMinus >= slTon) {
                    holder.btnPlus.setEnabled(false);
                    holder.btnMinus.setEnabled(true);
                    soLuongMinus = slTon;
                } else if (soLuongMinus <= 1) {
                    holder.btnPlus.setEnabled(true);
                    holder.btnMinus.setEnabled(false);
                    soLuongMinus = 1;
                } else if (soLuongMinus >= 1) {
                    holder.btnPlus.setEnabled(true);
                    holder.btnMinus.setEnabled(true);
                }
                holder.edtAmount.setText(soLuongMinus + "");
                if (dataBaseHelper.UpdateAmountProduct(CartActivity.cartModelList.get(position).productCode, soLuongMinus)) {
                    CartActivity.cartModelList = dataBaseHelper.getAllProductsIn_tblCartWithStatus_false();
                    CartAdapter.this.notifyDataSetChanged();
                } else {
                    return;
                }
            }
        });

    }

    public void btnPlusClick(ViewHolder holder, int position) {
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int ma = CartActivity.cartModelList.get(position).productCode;
                for(int i=0; i< CartActivity.listProductByCode.size();i++){
                    if(ma == CartActivity.listProductByCode.get(i).maSanPham){
                        slTon =   CartActivity.listProductByCode.get(i).soLuong;
                    }
                }
                int soLuongThem = Integer.parseInt(holder.edtAmount.getText().toString()) + 1;
                if (soLuongThem >= slTon) {
                    openDialog_InventoryNumber(Gravity.CENTER,slTon);
                    //holder.btnPlus.setEnabled(false);
                    holder.btnMinus.setEnabled(true);
                    soLuongThem=slTon;
                } else if (soLuongThem <= 1) {
                    holder.btnPlus.setEnabled(true);
                    holder.btnMinus.setEnabled(false);
                    soLuongThem = 1;
                } else if (soLuongThem >= 1) {
                    holder.btnPlus.setEnabled(true);
                    holder.btnMinus.setEnabled(true);
                }
                holder.edtAmount.setText(soLuongThem + "");
                if (dataBaseHelper.UpdateAmountProduct(CartActivity.cartModelList.get(position).productCode, soLuongThem)) {
                    CartActivity.cartModelList = dataBaseHelper.getAllProductsIn_tblCartWithStatus_false();
                    CartAdapter.this.notifyDataSetChanged();
                } else {
                    return;
                }
            }
        });
    }

    private void openDialog_InventoryNumber(int gravity, int soLuong) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_unitprice_layout);
        Window window = dialog.getWindow();
        if(window==null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribus = window.getAttributes();
        windowAttribus.gravity =gravity;
        window.setAttributes(windowAttribus);
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        AppCompatButton btnOk = dialog.findViewById(R.id.btnOKDialog);
        TextView tvSL = dialog.findViewById(R.id.tvEnventoryNumber);
        tvSL.setText("Mặt hàng này chỉ còn tối đa là "+soLuong+" sản phẩm");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void callApiProductByCode(int productCode)
    {
        ApiService.apiService.getDataProduct(productCode).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                productByCode = new Product(response.body().maSanPham, response.body().tenSanPham,
                        response.body().soLuong, response.body().donGia, response.body().donGiaNhap,
                        response.body().moTa, response.body().moTaChiTiet, response.body().khuyenMai,
                        response.body().giamGia, response.body().ngayCapNhat, response.body().xuatXu,
                        response.body().hinhMinhHoa, response.body().dsHinh, response.body().tinhTrang,
                        response.body().ghiChu,response.body().tenDanhMuc);
                if(productByCode.getGhiChu().equals("DienThoai")){
                    Intent intent = new Intent(context.getApplicationContext(), PhoneDetailsActivity.class);
                    intent.putExtra("productCode",productByCode.maSanPham);

                    context.startActivity(intent);
                    CustomIntent.customType(context,"left-to-right");
                }
                if(productByCode.getGhiChu().equals("Laptop"))
                {
                    Intent intent = new Intent(context.getApplicationContext(), LaptopDetailsActivity.class);
                    intent.putExtra("productCode",productByCode.maSanPham);
                    context.startActivity(intent);
                    CustomIntent.customType(context,"left-to-right");
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
    }
    public void DoApriori() {
        List<String> listString = new ArrayList<>();
        // duyệt danh sách cách sản phẩm có trong giỏ hàng
        if(CartActivity.cartModelList==null || CartActivity.cartModelList.size()==0)
        {
            CartActivity.layoutByDoApriori.setVisibility(View.GONE);
            CartActivity.listProductDoApriori.clear();
            return;
        }
        CartActivity.layoutByDoApriori.setVisibility(View.VISIBLE);
        for (CartModel item : CartActivity.cartModelList) {
            if (item.note.equalsIgnoreCase("DienThoai") || item.note.equalsIgnoreCase("Laptop")) {
                listString.add(item.note);
            }
            if (item.note.equalsIgnoreCase("PhuKien")) {
                listString.add(item.categoryName);
            }
        }
        // lấy các ds khong trùng nhau đê khai phá luật
        Set<String> set = new HashSet<>(listString);
        listString.clear();
        listString.addAll(set);
        // timf cacs tập hợp con của chuỗi không trùng nhau
        List<String> listSubs = new ArrayList<>();
        for (int i = 0; i < listString.size(); i++) {
            findSubset(listString.get(i).toString().trim(), listSubs);
        }
        for (String item : listSubs) {
            callApiDoApriori(item.substring(0, item.length() - 1).trim());
        }
    }

    public void findSubset(String s, List<String> listSub) {
        if (listSub.size() == 0) {
            listSub.add(s + ",");
        } else {
            int size = listSub.size();
            for (int i = 0; i < size; i++) {
                if (!listSub.contains(s + ",")) {
                    listSub.add(s + ",");
                }
                listSub.add(listSub.get(i) + s + ",");
            }
        }
    }

    public void callApiDoApriori(String input) {
        ApiService.apiService.getDataDoApriori(input).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                //listProductDoApriori.clear();
                if (response.isSuccessful() && response.body() != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        if(CartActivity.listProductDoApriori.size()==0 || CartActivity.listProductDoApriori==null)
                        {
                            CartActivity.listProductDoApriori.add(new Product(response.body().get(i).maSanPham, response.body().get(i).tenSanPham,
                                    response.body().get(i).soLuong, response.body().get(i).donGia, response.body().get(i).donGiaNhap,
                                    response.body().get(i).moTa, response.body().get(i).moTaChiTiet, response.body().get(i).khuyenMai,
                                    response.body().get(i).giamGia, response.body().get(i).ngayCapNhat, response.body().get(i).xuatXu,
                                    response.body().get(i).hinhMinhHoa, response.body().get(i).dsHinh, response.body().get(i).tinhTrang,
                                    response.body().get(i).ghiChu, response.body().get(i).tenDanhMuc));
                        }else{
                            Product product = new Product(response.body().get(i).maSanPham, response.body().get(i).tenSanPham,
                                    response.body().get(i).soLuong, response.body().get(i).donGia, response.body().get(i).donGiaNhap,
                                    response.body().get(i).moTa, response.body().get(i).moTaChiTiet, response.body().get(i).khuyenMai,
                                    response.body().get(i).giamGia, response.body().get(i).ngayCapNhat, response.body().get(i).xuatXu,
                                    response.body().get(i).hinhMinhHoa, response.body().get(i).dsHinh, response.body().get(i).tinhTrang,
                                    response.body().get(i).ghiChu, response.body().get(i).tenDanhMuc);
                            boolean check = true;
                            for (int j=0 ; j< CartActivity.listProductDoApriori.size();j++)
                            {
                                if(CartActivity.listProductDoApriori.get(j).maSanPham == product.maSanPham)
                                {
                                    check= false;
                                }
                            }
                            if(check){
                                CartActivity.listProductDoApriori.add(product);
                            }
                        }
                        CartActivity.aprioriAdapter.notifyDataSetChanged();
                    }
                    for (CartModel cartModel: CartActivity.cartModelList)
                    {
                        for(Product item: CartActivity.listProductDoApriori)
                        {
                            if(cartModel.getNote().equalsIgnoreCase("DienThoai")|| cartModel.getNote().equalsIgnoreCase("Laptop"))
                            {
                                if(cartModel.getNote().equalsIgnoreCase(item.getGhiChu()))
                                {
                                    CartActivity.listProductDoApriori.remove(item);
                                    CartActivity.aprioriAdapter.notifyDataSetChanged();
                                    break;
                                }
                            }
                            if(cartModel.getNote().equalsIgnoreCase("PhuKien"))
                            {
                                if(cartModel.getCategoryName().equalsIgnoreCase(item.getTenDanhMuc()))
                                {
                                    CartActivity.listProductDoApriori.remove(item);
                                    CartActivity.aprioriAdapter.notifyDataSetChanged();
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (CartActivity.cartModelList != null) {
            return CartActivity.cartModelList.size();
        }
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tvProductName, tvUnitPriceCart, tvProductCartSale;
        private TextView edtAmount;
        ImageButton btnPlus, btnMinus;
        private TextView tvRemove;
        public CheckBox ckbChoseProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgProductCart);
            tvProductName = itemView.findViewById(R.id.tvNameProductsCart);
            edtAmount = itemView.findViewById(R.id.edtAmount);
            tvUnitPriceCart = itemView.findViewById(R.id.tvUnitPriceCart);
            tvProductCartSale = itemView.findViewById(R.id.tvProductCartSale);
            tvRemove = itemView.findViewById(R.id.btnRemoveProduct);
            ckbChoseProduct = itemView.findViewById(R.id.ckbChoseProduct);
            btnPlus = itemView.findViewById(R.id.imageButtonAdd);
            btnMinus = itemView.findViewById(R.id.imageButtonMinus);
            //this.setIsRecyclable(false);
        }




    }

}
