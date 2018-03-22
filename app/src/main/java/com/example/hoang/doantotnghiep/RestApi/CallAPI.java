package com.example.hoang.doantotnghiep.RestApi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.doantotnghiep.Model.ModelApi.ExpensesDate;
import com.example.hoang.doantotnghiep.Model.ModelApi.ListBudget;
import com.example.hoang.doantotnghiep.Model.ModelApi.ListClick;
import com.example.hoang.doantotnghiep.Model.ModelApi.ListRevenue;
import com.example.hoang.doantotnghiep.Model.ModelApi.MessageDataMonthCopy;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelExpensesDateEdit.DeleteExpensesDate;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelExpensesDateEdit.UpdateExpensesDate;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelManagerRevenue.ManagerRevenue;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelManagerRevenue.MessageRevenueGet;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelPayMent1.ModelPayment;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelPayMent1.PaymentObject;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelProfile.MessageProfile;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelProfileAvatar.ProfileAvatar;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelTargetLoans.DeleteLoans;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelTargetLoans.TargetLoanMessage;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelTargetLoans.UpdateTargetLoan;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelTargetSaving.DeleteSaving;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelTargetSaving.TargetSavingMessage;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelTargetSaving.UpdateTargetSaving;
import com.example.hoang.doantotnghiep.Model.ModelApi.TargetSaving;
import com.example.hoang.doantotnghiep.Model.ModelApi.model.MessageEvaluteProfile;
import com.example.hoang.doantotnghiep.Model.ModelApi.model.MessageRegister;
import com.example.hoang.doantotnghiep.Model.ModelApi.model.MessageRevenue;
import com.example.hoang.doantotnghiep.Model.ModelApi.model.MessageServer;
import com.example.hoang.doantotnghiep.Model.ModelApi.model.ProfileEvalute;
import com.example.hoang.doantotnghiep.Model.ModelApi.model.RevenueInfo;
import com.example.hoang.doantotnghiep.Model.ModelApi.model.UserEmail;
import com.example.hoang.doantotnghiep.Model.ModelBudget.EditBudgetRequest;
import com.example.hoang.doantotnghiep.Model.ModelExpenseDate.ExpenseTop10;
import com.example.hoang.doantotnghiep.Model.ModelExpenseDate.ModelExpenseDate;
import com.example.hoang.doantotnghiep.Model.ModelTarget.ModelEditLoan;
import com.example.hoang.doantotnghiep.Model.ModelTarget.ModelTarget;
import com.example.hoang.doantotnghiep.Model.ResponseCommon;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.View.activitys.LoginRegister;
import com.example.hoang.doantotnghiep.utils.SharedPrefsUtils;
import com.example.hoang.doantotnghiep.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




/**
 * Created by vancu on 20/11/2017.
 */

public class CallAPI {
    public interface LoginFbResult {
        void onResult(int code);
    }

    public interface OnLoginSuccess {
        void onSuccess(boolean success, Boolean isFirstLogin);
    }


    public static void insert_signup(final Context context, UserEmail userEmail,
                                     final LinearLayout layoutlogin,
                                     final LinearLayout layoutregister,
                                     final TextView txtvlogin, final TextView txtvregister,
                                     final EditText edtrgtemail, final EditText edtloginacount,
                                     final EditText edtpassword, final EditText edtrepassword) {
        Call<MessageRegister> mCall = ApiUtils.getApiService().insert_signup(userEmail.getEmail(), userEmail.getPassword());
        Utils.showProgressDialog(context, "");
        mCall.enqueue(new Callback<MessageRegister>() {
            @Override
            public void onResponse(Call<MessageRegister> call, Response<MessageRegister> response) {
                Utils.dismissCurrentDialog();
                Log.d("koi", String.valueOf(response.code()));
                if (response.isSuccessful()) {
                    Toast.makeText(context, response.body().getStatus(), Toast.LENGTH_LONG).show();
                    layoutlogin.setVisibility(View.VISIBLE);
                    layoutregister.setVisibility(View.INVISIBLE);
                    txtvlogin.setTextColor(Color.WHITE);
                    txtvregister.setTextColor(Color.GRAY);
                    edtloginacount.setText(edtrgtemail.getText());
                    edtrgtemail.setText("");
                    edtpassword.setText("");
                    edtrepassword.setText("");
                } else {
                    Toast.makeText(context, "Sai định dạng tài khoản mật khẩu!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MessageRegister> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, "Không kết nối được với server!", Toast.LENGTH_LONG).show();
            }
        });
    }


    public static void Login_Email(final Context context, UserEmail userEmail, final OnLoginSuccess loginSuccess) {

        Call<MessageServer> mCall = ApiUtils.getApiService().login_email(userEmail.getEmail(), userEmail.getPassword());
        Utils.showProgressDialog(context, "");
        mCall.enqueue(new Callback<MessageServer>() {
            @Override
            public void onResponse(Call<MessageServer> call, Response<MessageServer> response) {
                Utils.dismissCurrentDialog();
                if (response.body().getToken() != null) {
                    SharedPrefsUtils.setStringPreference(context, "token", response.body().getToken().toString());
                    String token = "Bearer " + SharedPrefsUtils.getStringPreference(context, "token");
                    RetrofitClient.MyauthHeaderContent = token;
                    Boolean isFirstLogin = response.body().getUser().getIsFirstLogin();
                    loginSuccess.onSuccess(true, isFirstLogin);
                } else {
                    Toast.makeText(context, "Sai tài khoản hoặc mật khẩu !", Toast.LENGTH_LONG).show();
                    loginSuccess.onSuccess(false, false);
                }
            }

            @Override
            public void onFailure(Call<MessageServer> call, Throwable t) {
                Utils.dismissCurrentDialog();
            }
        });
    }


    public interface OnCallManager_Revenue {
        void onResult(ManagerRevenue managerRevenue);
    }

    public static void getManager_revenue(final Context context, String fromDate, String toDate, final OnCallManager_Revenue onCallManager_revenue) {
        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().getManager_revenue(fromDate, toDate).enqueue(new Callback<ManagerRevenue>() {
            @Override
            public void onResponse(Call<ManagerRevenue> call, Response<ManagerRevenue> response) {
                Utils.dismissCurrentDialog();
                if (response != null && response.body() != null && response.body().code == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }
                ManagerRevenue managerRevenue = response.body();
                onCallManager_revenue.onResult(managerRevenue);
//                Toast.makeText(context, String.valueOf(managerRevenue.data.revenue.get(2).getValue_revenue()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ManagerRevenue> call, Throwable t) {
                Utils.dismissCurrentDialog();
            }
        });
    }

    /***
     GET_EXPENSE_DATE
     */

    public interface OnCallExpensesDate {
        void onResult(ExpensesDate expensesDate);
    }

    public static void getExpensesDate(final Context context, String from_date, String to_date, int limit, String type, final OnCallExpensesDate onCallExpensesDate) {
        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().getExpenseFromDate(from_date, to_date, limit, type).enqueue(new Callback<ExpensesDate>() {
            @Override
            public void onResponse(Call<ExpensesDate> call, Response<ExpensesDate> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().code == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }
                onCallExpensesDate.onResult(response.body());
            }

            @Override
            public void onFailure(Call<ExpensesDate> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface OnCallExpenseTop10 {
        void onResult(ExpenseTop10 expenseTop10);
    }

    public static void getExpenseTop10(final Context context, final OnCallExpenseTop10 onCallExpenseTop10) {
        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().getTop10Expense(Utils.getFirstDateOfMonth(new Date()), Utils.getLastDateOfMonth(new Date())).enqueue(new Callback<ExpenseTop10>() {
            @Override
            public void onResponse(Call<ExpenseTop10> call, Response<ExpenseTop10> response) {
                Utils.dismissCurrentDialog();
                if (response != null && response.body() != null && response.body().code == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }
                if (response.body().code == 1) {
                    onCallExpenseTop10.onResult(response.body());
                }



            }

            @Override
            public void onFailure(Call<ExpenseTop10> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * POST NEW TARGET SAVING
     */

    public interface OnCallAddTargetSavingSuccess {
        void onResult(TargetSaving reslult);
    }

    public static void addNewTargetSuccess(final Context context, File icon, String target_name, String target_value, String deadline, String saving_per_month, String static_icon, final OnCallAddTargetSavingSuccess onCallAddTargetSavingSuccess) {
        Utils.showProgressDialog(context, "");
        MultipartBody.Part body = null;
        RequestBody static_icon_ = null;
        if (icon == null || icon.length() == 0) {
            body = null;
            static_icon_ = RequestBody.create(MediaType.parse("text/plain"), static_icon);
        } else {
            RequestBody icon_file = RequestBody.create(MediaType.parse("file"), icon);
            body = MultipartBody.Part.createFormData("icon", icon.getName(), icon_file);
            static_icon = null;
        }


        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), target_name);
        RequestBody value = RequestBody.create(MediaType.parse("text/plain"), target_value);
        RequestBody time = RequestBody.create(MediaType.parse("text/plain"), deadline);
        RequestBody per_mont = RequestBody.create(MediaType.parse("text/plain"), saving_per_month);

        ApiUtils.getApiService().addNewTargetSaving(name, value, time, per_mont, static_icon_, body).enqueue(new Callback<TargetSaving>() {
            @Override
            public void onResponse(Call<TargetSaving> call, Response<TargetSaving> response) {
                Utils.dismissCurrentDialog();
                if (response != null && response.body() != null && response.body().code == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }
                if (response != null && response.body() != null) {
                    TargetSaving targetSaving = response.body();
                    onCallAddTargetSavingSuccess.onResult(targetSaving);
                }
            }

            @Override
            public void onFailure(Call<TargetSaving> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();

            }
        });

    }

    /**
     * POST NEW TARGET LOAN
     */

    public interface OnCallAddTargetLoanSuccess {
        void onResult(Boolean success);
    }

    public static void addNewTargetLoanSuccess(final Context context, File icon, String loan_name, String origin_money, String preferential_rate, String preferential_time,
                                               String rate_base, String loan_time, String pay_per_month, String total_interest_pay, String total_money_pay, String static_icon, final OnCallAddTargetLoanSuccess OnCallAddTargetLoanSuccess) {
        Utils.showProgressDialog(context, "");
        MultipartBody.Part body = null;
        RequestBody static_icon_ = null;
        if (icon == null || icon.length() == 0) {
            body = null;
            static_icon_ = RequestBody.create(MediaType.parse("text/plain"), static_icon);
        } else {
            RequestBody icon_file = RequestBody.create(MediaType.parse("file"), icon);
            body = MultipartBody.Part.createFormData("icon", icon.getName(), icon_file);
            static_icon = null;
        }

        RequestBody loan_name_bd = RequestBody.create(MediaType.parse("text/plain"), loan_name);
        RequestBody origin_money_bd = RequestBody.create(MediaType.parse("text/plain"), origin_money);
        RequestBody preferential_rate_bd = RequestBody.create(MediaType.parse("text/plain"), preferential_rate);
        RequestBody preferential_time_bd = RequestBody.create(MediaType.parse("text/plain"), preferential_time);
        RequestBody rate_base_bd = RequestBody.create(MediaType.parse("text/plain"), rate_base);
        RequestBody loan_time_bd = RequestBody.create(MediaType.parse("text/plain"), loan_time);
        RequestBody pay_per_month_bd = RequestBody.create(MediaType.parse("text/plain"), pay_per_month);
        RequestBody total_interest_pay_bd = RequestBody.create(MediaType.parse("text/plain"), total_interest_pay);
        RequestBody total_money_pay_bd = RequestBody.create(MediaType.parse("text/plain"), total_money_pay);
        ApiUtils.getApiService().addNewTargetLoan(loan_name_bd, origin_money_bd, preferential_rate_bd, preferential_time_bd,
                rate_base_bd, loan_time_bd, pay_per_month_bd, total_interest_pay_bd, total_money_pay_bd, static_icon_, body).enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().code == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }

                    ResponseCommon responseCommon = response.body();
                    if (responseCommon.code == 1)
                        OnCallAddTargetLoanSuccess.onResult(true);
                    else
                        OnCallAddTargetLoanSuccess.onResult(false);
                }


            @Override
            public void onFailure(Call<ResponseCommon> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * UPLOAD AVATAR
     *
     * @param context
     * @param avatar
     */
    public static void uploadAvatar(final Context context, File avatar) {
        Utils.showProgressDialog(context, "");

        RequestBody fileAvatar = RequestBody.create(MediaType.parse("file"), avatar);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", avatar.getName(), fileAvatar);
        ApiUtils.getApiService().uploadAvatar(body).enqueue(new Callback<ProfileAvatar>() {
            @Override
            public void onResponse(Call<ProfileAvatar> call, Response<ProfileAvatar> response) {
                Utils.dismissCurrentDialog();
                if (response != null && response.body() != null && response.body().code == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }
                if (response != null && response.body() != null) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileAvatar> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public interface OnCallMessageProfile {
        void onResult(MessageProfile messageProfile);
    }

    public static void getProfile(final Context context, final OnCallMessageProfile onCallMessageProfile) {
        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().getProfile().enqueue(new Callback<MessageProfile>() {
            @Override
            public void onResponse(Call<MessageProfile> call, Response<MessageProfile> response) {
                Utils.dismissCurrentDialog();
                if (response != null && response.body() != null && response.body().getCode() == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }

                MessageProfile messageProfile = response.body();
                onCallMessageProfile.onResult(messageProfile);
            }

            @Override
            public void onFailure(Call<MessageProfile> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, "Không thể kết nối đến server !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * ADD LIST BUDGET
     */

    public interface OnCallAddListBudgets {
        void onResult(Boolean success);
    }

    public static void addListBudgets(final Context context, String token, ListBudget listBudgets, final OnCallAddListBudgets onCallAddListBudgets) {
        Utils.showProgressDialog(context, "");

        ApiUtils.getApiService().addListBudgets(token, listBudgets).enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().code == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }

                ResponseCommon responseCommon = response.body();
                if (responseCommon != null && responseCommon.code == 1) {
                    Toast.makeText(context, context.getString(R.string.add_list_budget_success), Toast.LENGTH_SHORT).show();
                    onCallAddListBudgets.onResult(true);
                } else {
                    Toast.makeText(context, context.getString(R.string.add_list_budget_fail), Toast.LENGTH_SHORT).show();
                    onCallAddListBudgets.onResult(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseCommon> call, Throwable t) {
                Utils.dismissCurrentDialog();
                onCallAddListBudgets.onResult(false);
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * ADD LIST BUDGET
     */

    public interface OnCallAddListRevenues {
        void onResult(Boolean success);
    }

    public static void addListRevenues(final Context context, String token, ListRevenue listRevenues, final OnCallAddListRevenues onCallAddListRevenues) {
        Utils.showProgressDialog(context, "");

        ApiUtils.getApiService().addListRevenues(token, listRevenues).enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                 Utils.dismissCurrentDialog();
                if (response != null && response.body() != null && response.body().code == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }

                ResponseCommon responseCommon = response.body();
                if (responseCommon != null && responseCommon.code == 1) {
                    Toast.makeText(context, context.getString(R.string.add_list_revenue_success), Toast.LENGTH_SHORT).show();
                    onCallAddListRevenues.onResult(true);
                } else {
                    Toast.makeText(context, context.getString(R.string.add_list_revenue_fail), Toast.LENGTH_SHORT).show();
                    onCallAddListRevenues.onResult(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseCommon> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * UPDATE FIRST LOGIN
     */

    public static void updateFirstLogin(final Context context) {
        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().updateFirstLogin().enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().code == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }

                ResponseCommon responseCommon = response.body();
                if (response != null && responseCommon.code == 1) {

                } else {
                    Toast.makeText(context, context.getString(R.string.update_firstLogin), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCommon> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Add Payment
     */

    public static void insert_Payment(final Context context, PaymentObject paymentObject) {

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), paymentObject.getExpense_name());
        RequestBody value = RequestBody.create(MediaType.parse("text/plain"), paymentObject.getExpense_value());
        RequestBody datetime = RequestBody.create(MediaType.parse("text/plain"), paymentObject.getDate_time());
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), paymentObject.getType());
        RequestBody note = RequestBody.create(MediaType.parse("text/plain"), paymentObject.getNote());
        RequestBody fileImg = null;
        MultipartBody.Part body = null;
        if (paymentObject.getImgfile() != null) {
            fileImg = RequestBody.create(MediaType.parse("file"), paymentObject.getImgfile());
            body = MultipartBody.Part.createFormData("image", paymentObject.getImgfile().getName(), fileImg);
        }
        RequestBody icon = RequestBody.create(MediaType.parse("text/plain"), paymentObject.getImgName());
        RequestBody subType = RequestBody.create(MediaType.parse("text/plain"), paymentObject.getSybType());
        Call<ModelPayment> mCall = ApiUtils.getApiService().AddPayment(name, value, datetime, type, note, body,icon,subType);
        Utils.showProgressDialog(context, "");
        mCall.enqueue(new Callback<ModelPayment>() {
            @Override
            public void onResponse(Call<ModelPayment> call, Response<ModelPayment> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().getCode() == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }

                if (response.isSuccessful()) {
                    Toast.makeText(context, "Thêm chi tiêu thành công", Toast.LENGTH_LONG).show();
                    ((Activity) context).setResult(1111);
                    ((Activity) context).finish();

                } else {
                    Toast.makeText(context, "Thất bại", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ModelPayment> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }

        });
    }

    // update Target
    public static void updateTargetSaving(final Context context, String token, String change_icon, String target_saving_id, ModelTarget modelTarget, File icon, String static_icon) {
        MultipartBody.Part body = null;
        RequestBody static_icon_ = null;
        if (icon == null || icon.length() == 0) {
            body = null;
            static_icon_ = RequestBody.create(MediaType.parse("text/plain"), static_icon);
        } else if (icon != null) {
            RequestBody icon_file = RequestBody.create(MediaType.parse("file"), icon);
            body = MultipartBody.Part.createFormData("icon", icon.getName(), icon_file);
            static_icon = null;
        } else body = null;
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), modelTarget.getTargetName());
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), modelTarget.getTargetValue());
        RequestBody datetime = RequestBody.create(MediaType.parse("text/plain"), modelTarget.getDeadLine());
        RequestBody saving_per_month = RequestBody.create(MediaType.parse("text/plain"), modelTarget.getPay_per_month());


        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().editTargetSaving(token, target_saving_id, change_icon, name, price, datetime, saving_per_month, static_icon_, body).enqueue(new Callback<UpdateTargetSaving>() {
            @Override
            public void onResponse(Call<UpdateTargetSaving> call, Response<UpdateTargetSaving> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().getCode() == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }
                //Toast.makeText(context, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                ((Activity) context).setResult(2312);
                ((Activity) context).finish();
            }

            @Override
            public void onFailure(Call<UpdateTargetSaving> call, Throwable t) {
                Log.d("err", t.getMessage());
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void updateTargetLoan(final Context context, String token, String change_icon, String target_loan_id, ModelEditLoan modelEditLoan, File icon, String static_icon) {
        MultipartBody.Part body = null;
        RequestBody static_icon_ = null;
        if (icon == null || icon.length() == 0) {
            body = null;
            static_icon_ = RequestBody.create(MediaType.parse("text/plain"), static_icon);
        } else if (icon != null) {
            RequestBody icon_file = RequestBody.create(MediaType.parse("file"), icon);
            body = MultipartBody.Part.createFormData("icon", icon.getName(), icon_file);
            static_icon = null;
        } else body = null;
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), modelEditLoan.getLoan_name());
        RequestBody origin_money = RequestBody.create(MediaType.parse("text/plain"), modelEditLoan.getOrigin_money());
        RequestBody preferential_rate = RequestBody.create(MediaType.parse("text/plain"), modelEditLoan.getPreferential_rate());
        RequestBody rate_base = RequestBody.create(MediaType.parse("text/plain"), modelEditLoan.getRate_base());
        RequestBody pay_per_month = RequestBody.create(MediaType.parse("text/plain"), modelEditLoan.getPay_per_month());
        RequestBody total_interest_pay = RequestBody.create(MediaType.parse("text/plain"), modelEditLoan.getTotal_interest_pay());
        RequestBody total_money_pay = RequestBody.create(MediaType.parse("text/plain"), modelEditLoan.getTotal_money_pay());
        RequestBody loan_time = RequestBody.create(MediaType.parse("text/plain"), modelEditLoan.getLoan_time());

        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().editTargetLoan(token, target_loan_id, change_icon, name, origin_money, preferential_rate, rate_base, pay_per_month,
                total_money_pay, total_interest_pay, loan_time, static_icon_, body).enqueue(new Callback<UpdateTargetLoan>() {
            @Override
            public void onResponse(Call<UpdateTargetLoan> call, Response<UpdateTargetLoan> response) {
                Utils.dismissCurrentDialog();


                if (response != null && response.body() != null && response.body().getCode() == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }
                //Toast.makeText(context, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                ((Activity) context).setResult(2312);
                ((Activity) context).finish();
            }

            @Override
            public void onFailure(Call<UpdateTargetLoan> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //update Spend

    public interface onBackReload {
        void onReload(boolean isReload);
    }

    public static void updateExpensesDate(final Context context, String token, String expense_id, String change_icon, ModelExpenseDate modelExpenseDate, File image, final onBackReload onBackReload) {
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), modelExpenseDate.getExpense_name());
        RequestBody value = RequestBody.create(MediaType.parse("text/plain"), modelExpenseDate.getExpense_value());
        RequestBody note = RequestBody.create(MediaType.parse("text/plain"), modelExpenseDate.getNote());
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), modelExpenseDate.getType());
        RequestBody date_time = RequestBody.create(MediaType.parse("text/plain"), modelExpenseDate.getDate_time());
        RequestBody image_file = null;
        MultipartBody.Part body = null;
        if (modelExpenseDate.getImgfile() != null) {
            image_file = RequestBody.create(MediaType.parse("file"), image);
            body = MultipartBody.Part.createFormData("expense_image", image.getName(), image_file);
        }
        RequestBody icon = RequestBody.create(MediaType.parse("text/plain"), modelExpenseDate.getIcon());
        RequestBody subType = RequestBody.create(MediaType.parse("text/plain"), modelExpenseDate.getSubType());
        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().editExpensesDate(token, expense_id, change_icon, name, value, date_time, type, note, body,icon,subType).enqueue(new Callback<UpdateExpensesDate>() {
            @Override
            public void onResponse(Call<UpdateExpensesDate> call, Response<UpdateExpensesDate> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().getCode() == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }

                ((Activity) context).setResult(2023);

                if (response.body().getCode() == 1) {
                    onBackReload.onReload(true);
                } else {
                    onBackReload.onReload(false);
                }

            }

            @Override
            public void onFailure(Call<UpdateExpensesDate> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //add revenue
    public interface OnCallAddRevenue {
        void onResult(int code);
    }

    public static void addRevenue(final Context context, String token, RevenueInfo revenueInfo, final OnCallAddRevenue onCallAddRevenue) {
        Call<MessageRevenue> mCall = ApiUtils.getApiService().addRevenue(token, revenueInfo.getNameRevenue(), String.valueOf(revenueInfo.getValueRevenue()),
                revenueInfo.getStaticIcon());
        Utils.showProgressDialog(context, "");
        mCall.enqueue(new Callback<MessageRevenue>() {
            @Override
            public void onResponse(Call<MessageRevenue> call, Response<MessageRevenue> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().getCode() == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }


                SharedPrefsUtils.setStringPreference(context, "message", response.body().getStatus());
                int result = response.body().getCode();
                onCallAddRevenue.onResult(result);
            }

            @Override
            public void onFailure(Call<MessageRevenue> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Get target saving

    public interface OnCallTarrget_Saving {
        void onResult(TargetSavingMessage targetSavingMessage);
    }

    public static void getTarget_Saving(final Context context, final OnCallTarrget_Saving onCallTarrget_saving) {
        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().getTargetSaving().enqueue(new Callback<TargetSavingMessage>() {
            @Override
            public void onResponse(Call<TargetSavingMessage> call, Response<TargetSavingMessage> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().getCode() == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }

                TargetSavingMessage targetSavingMessage = response.body();
                onCallTarrget_saving.onResult(targetSavingMessage);
            }

            @Override
            public void onFailure(Call<TargetSavingMessage> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface OnCallTarrget_Loan {
        void onResult(TargetLoanMessage targetLoanMessage);
    }

    public static void getTarget_Loan(final Context context, final OnCallTarrget_Loan onCallTarrget_loan) {
        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().getTargetLoan().enqueue(new Callback<TargetLoanMessage>() {
            @Override
            public void onResponse(Call<TargetLoanMessage> call, Response<TargetLoanMessage> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().getCode() == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }

                TargetLoanMessage targetLoanMessage = response.body();
                onCallTarrget_loan.onResult(targetLoanMessage);
            }

            @Override
            public void onFailure(Call<TargetLoanMessage> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface OnCallRevenue {
        void onResult(MessageRevenueGet messageRevenueGet);
    }

    public static void get_Revenue(final Context context, String fromDate, String toDate, final OnCallRevenue onCallRevenue) {
        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().getRevenue(fromDate, toDate).enqueue(new Callback<MessageRevenueGet>() {

            @Override
            public void onResponse(Call<MessageRevenueGet> call, Response<MessageRevenueGet> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().getCode() == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }

                MessageRevenueGet messageRevenueGet = response.body();
                onCallRevenue.onResult(messageRevenueGet);
            }

            @Override
            public void onFailure(Call<MessageRevenueGet> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public interface OnDelete {
        void deleteSuccess(boolean onSuccess);
    }

    /**
     * DELETE BUDGET
     *
     * @param context
     * @param budget_id
     * @param delete
     */
    public static void deleteBudget(final Context context, String budget_id, final OnDelete delete) {
        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().deleteBudget(budget_id).enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().code == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }


                if (response != null && response.body().code == 1) {
                    delete.deleteSuccess(true);
                } else
                    delete.deleteSuccess(false);
            }

            @Override
            public void onFailure(Call<ResponseCommon> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //delete Expense
    public static void deleteExpenses(final Context context, String expense_id, final OnDelete delete) {
        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().deleteExpensesDate(expense_id).enqueue(new Callback<DeleteExpensesDate>() {
            @Override
            public void onResponse(Call<DeleteExpensesDate> call, Response<DeleteExpensesDate> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().code == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }

                ((Activity) context).setResult(2024);
                if (response != null && response.body().code == 1) {
                    delete.deleteSuccess(true);
                } else
                    delete.deleteSuccess(false);
            }

            @Override
            public void onFailure(Call<DeleteExpensesDate> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
        {

        }
    }

    public static void deleteLoans(final Context context, String target_loan_id, final OnDelete delete) {
        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().deleteTargetLoans(target_loan_id).enqueue(new Callback<DeleteLoans>() {
            @Override
            public void onResponse(Call<DeleteLoans> call, Response<DeleteLoans> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().code == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }


                if (response != null && response.body().code == 1) {
                    delete.deleteSuccess(true);
                } else
                    delete.deleteSuccess(false);
            }

            @Override
            public void onFailure(Call<DeleteLoans> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
        {

        }
    }

    public static void deleteBorrow(final Context context, String id, final OnDelete delete) {
        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().deleteBorrow(id).enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().code == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }

                if (response != null && response.body().code == 1) {
                    delete.deleteSuccess(true);
                } else
                    delete.deleteSuccess(false);
            }

            @Override
            public void onFailure(Call<ResponseCommon> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
        {

        }
    }

    public static void deleteSaving(final Context context, String target_saving_id, final OnDelete delete) {
        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().deleteTargetSaving(target_saving_id).enqueue(new Callback<DeleteSaving>() {
            @Override
            public void onResponse(Call<DeleteSaving> call, Response<DeleteSaving> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().code == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }

                if (response != null && response.body().code == 1) {
                    delete.deleteSuccess(true);
                } else
                    delete.deleteSuccess(false);
            }

            @Override
            public void onFailure(Call<DeleteSaving> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
        {

        }
    }

    public interface OnEdit {
        void editSuccess(boolean onSuccess);
    }

    /**
     * EDIT LIST BUDGET
     *
     * @param context
     * @param arr
     * @param edit
     */

    public static void editListBudget(final Context context, String token, ArrayList<EditBudgetRequest.EditItem> arr, final OnEdit edit) {
        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().editListbudgets(token, arr).enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().code == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }

                if (response != null && response.body().code == 1) {
                    edit.editSuccess(true);
                } else {
                    edit.editSuccess(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseCommon> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static void updateEvaluteProfile(final Context context, String token, ProfileEvalute profileEvalute) {
        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().updateEvaluteProfile(token, profileEvalute.getAddress(), profileEvalute.getAge(),
                profileEvalute.getGender(), profileEvalute.getIsMarrige(), profileEvalute.getCareChildAlone(),
                profileEvalute.getFinaceHealthy()).enqueue(new Callback<MessageEvaluteProfile>() {
            @Override
            public void onResponse(Call<MessageEvaluteProfile> call, Response<MessageEvaluteProfile> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().getCode() == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }

                SharedPrefsUtils.setStringPreference(context, "message", response.body().getStatus());
            }

            @Override
            public void onFailure(Call<MessageEvaluteProfile> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void addClick(final Context context, String token, ListClick listClick, final OnCallAddListBudgets onCallAddListBudgets) {
        Utils.showProgressDialog(context, "");

        ApiUtils.getApiService().addClick(token, listClick).enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().code == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }


                ResponseCommon responseCommon = response.body();
                if (responseCommon != null && responseCommon.code == 1) {
                    //Toast.makeText(context, context.getString(R.string.click_success), Toast.LENGTH_SHORT).show();

                    onCallAddListBudgets.onResult(true);
                } else {
                    //Toast.makeText(context, context.getString(R.string.click_false), Toast.LENGTH_SHORT).show();
                    onCallAddListBudgets.onResult(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseCommon> call, Throwable t) {
                Utils.dismissCurrentDialog();
                onCallAddListBudgets.onResult(false);
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void addDownload(final Context context, String token, String id, String name, String os, String version) {
        RequestBody id1 = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody name1 = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody os1 = RequestBody.create(MediaType.parse("text/plain"), os);
        RequestBody version1 = RequestBody.create(MediaType.parse("text/plain"), version);
        Call<ModelPayment> mCall = ApiUtils.getApiService().addDownload(token, id1, name1, os1, version1);
        Utils.showProgressDialog(context, "");
        mCall.enqueue(new Callback<ModelPayment>() {
            @Override
            public void onResponse(Call<ModelPayment> call, Response<ModelPayment> response) {
                Utils.dismissCurrentDialog();


                if (response != null && response.body() != null && response.body().getCode() == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }

                if (response.isSuccessful()) {
                    //Toast.makeText(context, "Thêm download thành công", Toast.LENGTH_LONG).show();

                } else {
                    //Toast.makeText(context, "Thất bại", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ModelPayment> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }

        });
    }

    public static void deleteRevenua(final Context context, String id, final OnDelete delete) {
        Call<DeleteExpensesDate> mCall = ApiUtils.getApiService().deleteRevenue(id);
        Utils.showProgressDialog(context, "");
        mCall.enqueue(new Callback<DeleteExpensesDate>() {
            @Override
            public void onResponse(Call<DeleteExpensesDate> call, Response<DeleteExpensesDate> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().code == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }

                if (response.isSuccessful()) {
                    delete.deleteSuccess(true);

                } else {
                    delete.deleteSuccess(false);
                }
            }

            @Override
            public void onFailure(Call<DeleteExpensesDate> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }

        });
    }


    public static void editRevenue(final Context context, String token, String id, String name, double value, final OnCallAddRevenue onCallAddRevenue) {
        RequestBody name1 = RequestBody.create(MediaType.parse("text/plain"), name);
        Call<MessageRevenue> mCall = ApiUtils.getApiService().editRevenue(token, id, name1, value);
        Utils.showProgressDialog(context, "");
        mCall.enqueue(new Callback<MessageRevenue>() {
            @Override
            public void onResponse(Call<MessageRevenue> call, Response<MessageRevenue> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().getCode() == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }


                SharedPrefsUtils.setStringPreference(context, "message", response.body().getStatus());
                int result = response.body().getCode();
                onCallAddRevenue.onResult(result);
            }

            @Override
            public void onFailure(Call<MessageRevenue> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //updateborrow

    public static void updateBorrow(final Context context, String token, String borrow_id, String name_borrow, double money_borrow, int time_borrow, double money_income, String office,
                                    String type_receive_money, double money_electric, double money_isurrance, String address, String email, String number_phone, double rate_base, float pay_per_month)
    {
        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().editBorrow(token,borrow_id,name_borrow, money_borrow, time_borrow, money_income, office, type_receive_money,money_electric,money_isurrance,address,email,number_phone,rate_base,pay_per_month).enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                Utils.dismissCurrentDialog();
                if (response != null && response.body() != null && response.body().getCode() == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }
                Toast.makeText(context, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                ((Activity) context).setResult(2021);
                ((Activity) context).finish();
            }

            @Override
            public void onFailure(Call<ResponseCommon> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface OnAddBorrow {
        void onResult(ResponseCommon responseCommon);
    }

    public static void addNewBorrow(final Context context, String name_borrow, double money_borrow, int time_borrow, double money_income, String office,
                                    String type_receive_money, double money_electric, double money_isurrance, String address, String email, String number_phone, double rate_base, double pay_per_month,
                                    final OnAddBorrow onAddBorrow) {
        Utils.showProgressDialog(context, "");
        ApiUtils.getApiService().addNewBorrow(name_borrow, money_borrow, time_borrow, money_income, office, type_receive_money, money_electric, money_isurrance, address, email, number_phone, rate_base, pay_per_month).enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                Utils.dismissCurrentDialog();

                if (response != null && response.body() != null && response.body().getCode() == 2) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(context, "Token hết hạn. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    return;
                }

                if (response != null)
                    onAddBorrow.onResult(response.body());
            }

            @Override
            public void onFailure(Call<ResponseCommon> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void copyMonthData1(final Context context, String token, String curentMonth, String currentYear) {

        Call<MessageDataMonthCopy> mCall = ApiUtils.getApiService().copyDataMonth(token, curentMonth, currentYear);
        Utils.showProgressDialog(context, "");
        mCall.enqueue(new Callback<MessageDataMonthCopy>() {

            @Override
            public void onResponse(Call<MessageDataMonthCopy> call, Response<MessageDataMonthCopy> response) {
            }

            @Override
            public void onFailure(Call<MessageDataMonthCopy> call, Throwable t) {
                Toast.makeText(context, context.getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
