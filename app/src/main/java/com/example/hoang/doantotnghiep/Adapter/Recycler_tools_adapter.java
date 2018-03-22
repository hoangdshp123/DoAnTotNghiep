package com.example.hoang.doantotnghiep.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.doantotnghiep.Model.Item_Tools;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.View.activitys.AddNewTargetActivity;
import com.example.hoang.doantotnghiep.View.dialog.DialogAddItem;
import com.example.hoang.doantotnghiep.utils.Const;

import java.util.ArrayList;

import static com.example.hoang.doantotnghiep.utils.Const.RESULT_CODE_EDIT_BUDGET_NSCB;
import static com.example.hoang.doantotnghiep.utils.Const.RESULT_CODE_EDIT_BUDGET_NSTT;
import static com.example.hoang.doantotnghiep.utils.Const.RESULT_CODE_EDIT_BUDGET_PCS;
import static com.example.hoang.doantotnghiep.utils.Const.RESULT_CODE_MANAGE_REVENUE;
import static com.example.hoang.doantotnghiep.utils.Const.RESULT_CODE_NSCB;
import static com.example.hoang.doantotnghiep.utils.Const.RESULT_CODE_NSTT;
import static com.example.hoang.doantotnghiep.utils.Const.RESULT_CODE_PCS;
import static com.example.hoang.doantotnghiep.utils.Const.RESULT_CODE_REVENUE;

/**
 * Created by kien.lovan on 1/3/2018.
 */

public class Recycler_tools_adapter extends RecyclerView.Adapter<Recycler_tools_adapter.ToolsHolder> {


    ArrayList<Item_Tools> arrItem;
    Context context;
    String type;
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;

    public Recycler_tools_adapter(ArrayList<Item_Tools> arrItem, Context context, String type) {
        this.arrItem = arrItem;
        this.context = context;
        this.type = type;

    }

    @Override
    public ToolsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tools, parent, false);
        return new ToolsHolder(view);
    }

    @Override
    public void onBindViewHolder(ToolsHolder holder, int position) {
        if (arrItem != null && arrItem.size() > 0) {
            switch (type) {

                case Const.REVENUE:
                    Item_Tools itemSetUp1Add = arrItem.get(position);
                    holder.img_icon.setImageDrawable(itemSetUp1Add.getIcon());
                    holder.txt_title.setText(itemSetUp1Add.getTitle());
                    holder.txt_title.setTextColor(context.getResources().getColor(R.color.color_white));
                    clickItemTools(holder, position, Const.REVENUE);
                    break;

                case Const.NSTT:
                    Item_Tools itemSetUp2AddNSTT = arrItem.get(position);
                    holder.img_icon.setImageDrawable(itemSetUp2AddNSTT.getIcon());
                    holder.txt_title.setText(itemSetUp2AddNSTT.getTitle());
                    holder.txt_title.setTextColor(context.getResources().getColor(R.color.color_white));
                    clickItemTools(holder, position, Const.NSTT);
                    break;

                case Const.NSCB:
                    Item_Tools itemSetUp2AddNSCB = arrItem.get(position);
                    holder.img_icon.setImageDrawable(itemSetUp2AddNSCB.getIcon());
                    holder.txt_title.setText(itemSetUp2AddNSCB.getTitle());
                    holder.txt_title.setTextColor(context.getResources().getColor(R.color.color_white));
                    clickItemTools(holder, position, Const.NSCB);
                    break;

                case Const.PCS:
                    Item_Tools itemSetUp2AddPCS = arrItem.get(position);
                    holder.img_icon.setImageDrawable(itemSetUp2AddPCS.getIcon());
                    holder.txt_title.setText(itemSetUp2AddPCS.getTitle());
                    holder.txt_title.setTextColor(context.getResources().getColor(R.color.color_white));
                    clickItemTools(holder, position, Const.PCS);
                    break;

                case Const.TAG_SETUP3:
                    Item_Tools itemSetUp3 = arrItem.get(position);
                    holder.img_icon.setImageDrawable(itemSetUp3.getIcon());
                    holder.txt_title.setText(itemSetUp3.getTitle());
                    holder.txt_title.setTextColor(context.getResources().getColor(R.color.color_white));
                    clickItemTools(holder, position, Const.TAG_SETUP3);
                    break;

                case Const.MANAGE_REVENUE:
                    Item_Tools itemManageRevenue = arrItem.get(position);
                    holder.img_icon.setImageDrawable(itemManageRevenue.getIcon());
                    holder.txt_title.setText(itemManageRevenue.getTitle());
                    holder.txt_title.setTextColor(context.getResources().getColor(R.color.text_default));
                    clickItemTools(holder, position, Const.MANAGE_REVENUE);
                    break;
                case Const.EDIT_BUDGET_NSTT:
                    Item_Tools itemEditNSTT = arrItem.get(position);
                    holder.img_icon.setImageDrawable(itemEditNSTT.getIcon());
                    holder.txt_title.setText(itemEditNSTT.getTitle());
                    holder.txt_title.setTextColor(context.getResources().getColor(R.color.text_default));
                    clickItemTools(holder, position, Const.EDIT_BUDGET_NSTT);
                    break;
                case Const.EDIT_BUDGET_NSCB:
                    Item_Tools itemEditNSCB = arrItem.get(position);
                    holder.img_icon.setImageDrawable(itemEditNSCB.getIcon());
                    holder.txt_title.setText(itemEditNSCB.getTitle());
                    holder.txt_title.setTextColor(context.getResources().getColor(R.color.text_default));
                    clickItemTools(holder, position, Const.EDIT_BUDGET_NSCB);
                    break;
                case Const.EDIT_BUDGET_PCS:
                    Item_Tools itemEditPCS = arrItem.get(position);
                    holder.img_icon.setImageDrawable(itemEditPCS.getIcon());
                    holder.txt_title.setText(itemEditPCS.getTitle());
                    holder.txt_title.setTextColor(context.getResources().getColor(R.color.text_default));
                    clickItemTools(holder, position, Const.EDIT_BUDGET_PCS);
                    break;

                case Const.TARGET_TYPE:
                    Item_Tools itemTargetSaving = arrItem.get(position);
                    holder.img_icon.setImageDrawable(itemTargetSaving.getIcon());
                    holder.txt_title.setText(itemTargetSaving.getTitle());
                    holder.txt_title.setTextColor(context.getResources().getColor(R.color.text_default));
                    clickItemTools(holder, position, Const.TARGET_TYPE);
                    break;
                default:
                    break;
            }
        }


    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    public class ToolsHolder extends RecyclerView.ViewHolder {

        private ImageView img_icon;
        private TextView txt_title;

        public ToolsHolder(View itemView) {
            super(itemView);
            img_icon = (ImageView) itemView.findViewById(R.id.img_icon);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
        }
    }

    public void clickItemTools(ToolsHolder holder, final int position, String type) {
        holder.itemView.setOnClickListener(view -> {
            switch (type) {

                case Const.REVENUE:
                case Const.NSTT:
                case Const.NSCB:
                case Const.PCS:
                case Const.EDIT_BUDGET_NSTT:
                case Const.EDIT_BUDGET_NSCB:
                case Const.EDIT_BUDGET_PCS:

                    if (position < 8) {
                        //send data to fragment
                        Bundle bundle = new Bundle();
                        bundle.putString("title", arrItem.get(position).getTitle());
                        bundle.putInt("value", 0);
                        bundle.putString("staticIconRevenue",setStaticIcon(position));
                        if (type == Const.REVENUE) {
                            bundle.putString("static_icon", setStaticIcon(position));
                            sendDataback(bundle, "DATA", RESULT_CODE_REVENUE);
                        } else if (type == Const.NSTT) {
                            sendDataback(bundle, "DATA", RESULT_CODE_NSTT);
                        } else if (type == Const.NSCB) {
                            sendDataback(bundle, "DATA", RESULT_CODE_NSCB);
                        } else if (type == Const.PCS) {
                            sendDataback(bundle, "DATA", RESULT_CODE_PCS);
                        } else if (type == Const.EDIT_BUDGET_NSTT) {
                            sendDataback(bundle, "DATA", RESULT_CODE_EDIT_BUDGET_NSTT);
                        } else if (type == Const.EDIT_BUDGET_NSCB) {
                            sendDataback(bundle, "DATA", RESULT_CODE_EDIT_BUDGET_NSCB);
                        } else if (type == Const.EDIT_BUDGET_PCS) {
                            sendDataback(bundle, "DATA", RESULT_CODE_EDIT_BUDGET_PCS);
                        }

                    } else if (position == 8) {
                        DialogAddItem dialogAddItem = new DialogAddItem((Activity) context, type, new DialogAddItem.AddNewItemSuccess() {
                            @Override
                            public void onSuccess(String ID, String name, double value) {
                                Bundle bundle = new Bundle();
                                bundle.putString("title", name);
                                bundle.putDouble("value", value);
                                bundle.putString("staticIconRevenue",setStaticIcon(position));
                                if (type == Const.REVENUE)
                                    sendDataback(bundle, "DATA", RESULT_CODE_REVENUE);
                                else if (type == Const.NSTT) {
                                    sendDataback(bundle, "DATA", RESULT_CODE_NSTT);
                                } else if (type == Const.NSCB) {
                                    sendDataback(bundle, "DATA", RESULT_CODE_NSCB);
                                } else if (type == Const.PCS) {
                                    sendDataback(bundle, "DATA", RESULT_CODE_PCS);
                                } else if (type == Const.EDIT_BUDGET_NSTT) {
                                    sendDataback(bundle, "DATA", RESULT_CODE_EDIT_BUDGET_NSTT);
                                } else if (type == Const.EDIT_BUDGET_NSCB) {
                                    sendDataback(bundle, "DATA", RESULT_CODE_EDIT_BUDGET_NSCB);
                                } else if (type == Const.EDIT_BUDGET_PCS) {
                                    sendDataback(bundle, "DATA", RESULT_CODE_EDIT_BUDGET_PCS);
                                }
                            }
                        });
                        dialogAddItem.setTextForEdittext(arrItem.get(position).getTitle());
                        dialogAddItem.setStaticIcon(arrItem.get(position).getStaticIcon());
                        dialogAddItem.showDiaglog();
                    }
                    break;

                case Const.MANAGE_REVENUE:
                    Bundle bundle = new Bundle();
                    DialogAddItem dialogAddItem = new DialogAddItem((Activity) context, type, new DialogAddItem.AddNewItemSuccess() {
                        @Override
                        public void onSuccess(String ID, String name, double value) {
                            bundle.putString("title", name);
                            bundle.putDouble("value", value);
                            if (type == Const.MANAGE_REVENUE) {
                                sendDataback(bundle, "DATA", RESULT_CODE_MANAGE_REVENUE);
                            }
                        }
                    });
                    dialogAddItem.setTextForEdittext(arrItem.get(position).getTitle());
                    dialogAddItem.setStaticIcon(arrItem.get(position).getStaticIcon());
                    dialogAddItem.showDiaglog();
                    break;

                case Const.TARGET_TYPE:
                    showOptionAddNewTarget(position, Const.TARGET_TYPE);
                    break;
                case Const.TAG_SETUP3:
                    showOptionAddNewTarget(position, Const.TAG_SETUP3);
                    break;
                default:
                    break;
            }

        });
    }

    private void sendDataback(Bundle bundle, String tag_bundle, int result_code) {
        Intent intent = ((Activity) context).getIntent();
        intent.putExtra(tag_bundle, bundle);
        ((Activity) context).setResult(result_code, intent);
        ((Activity) context).onBackPressed();
    }

    public void showOptionAddNewTarget(int position, String tag) {
        String title1 = null;
        String title2 = null;
        if (position == 0) {
            title1 = "Tiết kiệm mua nhà";
            title2 = "Vay mua nhà";
        } else if (position == 1) {
            title1 = "Tiết kiệm mua ô tô";
            title2 = "Vay mua ô tô";
        } else if (position == 2) {
            title1 = "Tiết kiệm mua sắm";
            title2 = "Vay tiêu dùng trả góp (Iphone,Laptop,Xe máy...)";
        }

        if (title1 == null && title2 == null) {
            Intent iSaving = new Intent(context,
                    AddNewTargetActivity.class);
            iSaving.putExtra("name_saving", arrItem.get(position).getTitle());
            iSaving.putExtra("position_saving", position);
            iSaving.putExtra("type", "target_saving");
            iSaving.putExtra("type1", "add");

            iSaving.putExtra("tag", tag);

            ((Activity) context).startActivityForResult(iSaving, 2345);

        } else {
            AlertDialog.Builder pictureDialog = new AlertDialog.Builder(context);
            pictureDialog.setTitle("Select Action");
            String[] pictureDialogItems = {
                    title1,
                    title2};
            pictureDialog.setItems(pictureDialogItems,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            switch (which) {
                                case 0:
                                    //send data to target saving
                                    Intent iSaving = new Intent(context,
                                            AddNewTargetActivity.class);
                                    iSaving.putExtra("name_saving", arrItem.get(position).getTitle());
                                    iSaving.putExtra("position_saving", position);
                                    iSaving.putExtra("type", "target_saving");
                                    iSaving.putExtra("type1", "add");
                                    iSaving.putExtra("tag", tag);

                                    ((Activity) context).startActivityForResult(iSaving, 2345);
                                    dialog.dismiss();
                                    break;
                                case 1:
                                    //send data to target loan
                                    Intent iLoan = new Intent(context, AddNewTargetActivity.class);
                                    iLoan.putExtra("name_loan", arrItem.get(position).getTitle());
                                    iLoan.putExtra("position_loan", position);
                                    iLoan.putExtra("type", "target_loan");
                                    iLoan.putExtra("type1", "add");
                                    iLoan.putExtra("tag", tag);
                                    ((Activity) context).startActivityForResult(iLoan, 3456);
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    });
            pictureDialog.show();
        }
    }

    public String setStaticIcon(int position) {
        String staticIcon = "";
        switch (position) {
            case 0:
                staticIcon = context.getResources().getResourceEntryName(R.drawable.icon_wallet_bright);
                break;
            case 1:
                staticIcon = context.getResources().getResourceEntryName(R.drawable.icon_bonus_bright);
                break;
            case 2:
                staticIcon = context.getResources().getResourceEntryName(R.drawable.icon_sale_bright);
                break;
            case 3:
                staticIcon = context.getResources().getResourceEntryName(R.drawable.icon_present_bright);
                break;
            case 4:
                staticIcon = context.getResources().getResourceEntryName(R.drawable.icon_interest_bright);
                break;
            case 5:
                staticIcon = context.getResources().getResourceEntryName(R.drawable.icon_loan_bright);
                break;
            case 6:
                staticIcon = context.getResources().getResourceEntryName(R.drawable.icon_overtime_bright);
                break;
            case 7:
                staticIcon = context.getResources().getResourceEntryName(R.drawable.icon_tips_bright);
                break;
            case 8:
                staticIcon = context.getResources().getResourceEntryName(R.drawable.icon_plus_bright);
                break;
        }
        return staticIcon;
    }

}
