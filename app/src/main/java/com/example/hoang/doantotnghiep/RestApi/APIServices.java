package com.example.hoang.doantotnghiep.RestApi;

import com.example.hoang.doantotnghiep.Model.ModelApi.BorrowResponse;
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
import com.example.hoang.doantotnghiep.Model.ModelApi.modelPayment.Payment;
import com.example.hoang.doantotnghiep.Model.ModelBudget.EditBudgetRequest;
import com.example.hoang.doantotnghiep.Model.ModelExpenseDate.ExpenseTop10;
import com.example.hoang.doantotnghiep.Model.ResponseCommon;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIServices {


    @POST("/register")
    @FormUrlEncoded
    Call<MessageRegister> insert_signup(@Field("email") String email,
                                        @Field("password") String password
    );

    @POST("/login_email")
    @FormUrlEncoded
    Call<MessageServer> login_email(@Field("email") String email,
                                    @Field("password") String password
    );


    @POST("/expense")
    @FormUrlEncoded
    Call<Payment> addPayment(@Field("expense_name") String name,
                             @Field("expense_value") String value,
                             @Field("note") String note);


    @GET("/manager_revenue/{from_date}/{to_date}")
    Call<ManagerRevenue> getManager_revenue(
            @Path("from_date") String from_date,
            @Path("to_date") String to_date);

    @GET("/revenues/{from_date}/{to_date}")
    Call<MessageRevenueGet> getRevenue(
            @Path("from_date") String from_date,
            @Path("to_date") String to_date
    );

    @POST("/expense_date")
    @FormUrlEncoded
    Call<ExpensesDate> getExpenseFromDate(@Field("from_date") String from_date,
                                          @Field("to_date") String to_date,
                                          @Field("limit") int limit,
                                          @Field("type") String type);


    @GET("/getTop10Expense/{fromdate}/{todate}")
    Call<ExpenseTop10> getTop10Expense(@Path("fromdate") String fromdate, @Path("todate") String todate);


    @POST("/target_saving")
    @Multipart
    Call<TargetSaving> addNewTargetSaving(
            @Part("target_name") RequestBody target_name,
            @Part("target_value") RequestBody target_value,
            @Part("deadline") RequestBody deadline,
            @Part("saving_per_month") RequestBody saving_per_month,
            @Part("static_icon") RequestBody static_icon,
            @Part MultipartBody.Part icon


    );

    @POST("/target_loan")
    @Multipart
    Call<ResponseCommon> addNewTargetLoan(@Part("loan_name") RequestBody loan_name,
                                          @Part("origin_money") RequestBody origin_money,
                                          @Part("preferential_rate") RequestBody preferential_rate,
                                          @Part("preferential_time") RequestBody preferential_time,
                                          @Part("rate_base") RequestBody rate_base,
                                          @Part("loan_time") RequestBody loan_time,
                                          @Part("pay_per_month") RequestBody pay_per_month,
                                          @Part("total_interest_pay") RequestBody total_interest_pay,
                                          @Part("total_money_pay") RequestBody total_money_pay,
                                          @Part("static_icon") RequestBody static_icon,
                                          @Part MultipartBody.Part icon
    );

    @POST("/profile/avatar")
    @Multipart
    Call<ProfileAvatar> uploadAvatar(
            @Part MultipartBody.Part avatar
    );

    @GET("/profile")
    Call<MessageProfile> getProfile();


    @POST("/budgets")
    Call<ResponseCommon> addListBudgets(@Header("Authorization") String token, @Body ListBudget listData);

    @DELETE("/budget/{budget_id}")
    Call<ResponseCommon> deleteBudget(@Path("budget_id") String budget_id);

    @PUT("/listbudgets")
    Call<ResponseCommon> editListbudgets(@Header("Authorization") String token, @Body ArrayList<EditBudgetRequest.EditItem> listEdit);

    @POST("/revenues")
    Call<ResponseCommon> addListRevenues(@Header("Authorization") String token, @Body ListRevenue listData);

    @GET("/first_login")
    Call<ResponseCommon> updateFirstLogin();


    @POST("/expense")
    @Multipart
    Call<ModelPayment> AddPayment(
            @Part("expense_name") RequestBody expense_name,
            @Part("expense_value") RequestBody expense_value,
            @Part("date_time") RequestBody date_time,
            @Part("type") RequestBody type,
            @Part("note") RequestBody note,
            @Part MultipartBody.Part image,
            @Part("icon") RequestBody icon,
            @Part("subType") RequestBody subType
    );

    @POST("/revenue")
    @FormUrlEncoded
    Call<MessageRevenue> addRevenue(@Header("Authorization") String token,
                                    @Field("name_revenue") String name,
                                    @Field("value_revenue") String value,
                                    @Field("static_icon") String static_icon);

    @GET("/target_savings")
    Call<TargetSavingMessage> getTargetSaving();

    @GET("/target_loans")
    Call<TargetLoanMessage> getTargetLoan();


    @PUT("/target_saving/{target_saving_id}/{change_icon}")
    @Multipart
    Call<UpdateTargetSaving> editTargetSaving(@Header("Authorization") String token,
                                              @Path("target_saving_id") String saving_id,
                                              @Path("change_icon") String change_icon,
                                              @Part("target_name") RequestBody target_name,
                                              @Part("target_value") RequestBody target_value,
                                              @Part("deadline") RequestBody deadline,
                                              @Part("saving_per_month") RequestBody saving_per_month,
                                              @Part("static_icon") RequestBody static_icon,
                                              @Part MultipartBody.Part icon);

    @PUT("/target_loan/{target_loan_id}/{change_icon}")
    @Multipart
    Call<UpdateTargetLoan> editTargetLoan(@Header("Authorization") String token,
                                          @Path("target_loan_id") String loan_id,
                                          @Path("change_icon") String change_icon,
                                          @Part("loan_name") RequestBody loan_name,
                                          @Part("origin_money") RequestBody origin_money,
                                          @Part("preferential_rate") RequestBody preferential_rate,
                                          @Part("rate_base") RequestBody rate_base,
                                          @Part("pay_per_month") RequestBody pay_per_month,
                                          @Part("total_interest_pay") RequestBody total_interest_pay,
                                          @Part("total_money_pay") RequestBody total_money_pay,
                                          @Part("loan_time") RequestBody loan_time,
                                          @Part("static_icon") RequestBody static_icon,
                                          @Part MultipartBody.Part icon);

    @PUT("/borrow/{borrow_id}")
    @FormUrlEncoded
    Call<ResponseCommon> editBorrow(@Header("Authorization") String token,
                                    @Path("borrow_id") String borrow_id,
                                    @Field("name_borrow") String name_borrow,
                                    @Field("money_borrow") double money_borrow,
                                    @Field("time_borrow") int time_borrow,
                                    @Field("money_icome") double money_icome,
                                    @Field("office") String office,
                                    @Field("type_receive_money") String type_receive_money,
                                    @Field("money_electric") double money_electric,
                                    @Field("money_isurrance") double money_isurrance,
                                    @Field("address") String address,
                                    @Field("email") String email,
                                    @Field("number_phone") String number_phone,
                                    @Field("rate_base") double rate_base,
                                    @Field("pay_per_month") float pay_per_month);


    @PUT("/profile")
    @FormUrlEncoded
    Call<MessageEvaluteProfile> updateEvaluteProfile(@Header("Authorization") String token,
                                                     @Field("address") String address,
                                                     @Field("age") int age,
                                                     @Field("gender") int gender,
                                                     @Field("is_marriage") int is_marriage,
                                                     @Field("care_child_alone") int care_child_alone,
                                                     @Field("finace_healthy") float finace_healthy
    );

    @PUT("/expense/{expense_id}/{change_icon}")
    @Multipart
    Call<UpdateExpensesDate> editExpensesDate(@Header("Authorization") String token,
                                              @Path("expense_id") String spend_id,
                                              @Path("change_icon") String change_icon,
                                              @Part("expense_name") RequestBody expense_name,
                                              @Part("expense_value") RequestBody expense_value,
                                              @Part("date_time") RequestBody date_time,
                                              @Part("type") RequestBody type,
                                              @Part("note") RequestBody note,
                                              @Part MultipartBody.Part expense_image,
                                              @Part("icon") RequestBody icon,
                                              @Part("subType") RequestBody subType);


    @POST("/add_click")
    Call<ResponseCommon> addClick(@Header("Authorization") String token, @Body ListClick list);

    @POST("/add_download")
    @Multipart
    Call<ModelPayment> addDownload(@Header("Authorization") String token,
                                   @Part("device_id") RequestBody deviceId,
                                   @Part("device_name") RequestBody deviceName,
                                   @Part("device_os") RequestBody deviceOs,
                                   @Part("device_version") RequestBody deviceVersion);

    @DELETE("/revenue/{revenue_id}")
    Call<DeleteExpensesDate> deleteRevenue(@Path("revenue_id") String revenue_id);

    @DELETE("/expense/{expense_id}")
    Call<DeleteExpensesDate> deleteExpensesDate(@Path("expense_id") String expense_id);

    @DELETE("/target_loan/{target_loan_id}")
    Call<DeleteLoans> deleteTargetLoans(@Path("target_loan_id") String target_loan_id);

    @DELETE("/target_saving/{target_saving_id}")
    Call<DeleteSaving> deleteTargetSaving(@Path("target_saving_id") String target_saving_id);

    @PUT("/revenue/{revenue_id}")
    @Multipart
    Call<MessageRevenue> editRevenue(@Header("Authorization") String token,
                                     @Path("revenue_id") String revenue_id,
                                     @Part("name_revenue") RequestBody name_revenue,
                                     @Part("value_revenue") double value_revenue);


    //Borrow
    @POST("/borrow")
    @FormUrlEncoded
    Call<ResponseCommon> addNewBorrow(

            @Field("name_borrow") String name_borrow,
            @Field("money_borrow") double money_borrow,
            @Field("time_borrow") int time_borrow,
            @Field("money_icome") double money_icome,
            @Field("office") String office,
            @Field("type_receive_money") String type_receive_money,
            @Field("money_electric") double money_electric,
            @Field("money_isurrance") double money_isurrance,
            @Field("address") String address,
            @Field("email") String email,
            @Field("number_phone") String number_phone,
            @Field("rate_base") double rate_base,
            @Field("pay_per_month") double pay_per_month
    );

    @GET("/borrow")
    Call<BorrowResponse> getBorrow();

    @PUT("/borrow/{borrow_id}")
    @FormUrlEncoded
    Call<ResponseCommon> updateBorrow(
            @Path("borrow_id") String borrow_id,
            @Field("name_borrow") String name_borrow,
            @Field("time_borrow") int time_borrow,
            @Field("money_income") int money_income,
            @Field("office") String office,
            @Field("type_receive_money") String type_receive_money,
            @Field("money_electric") double money_electric,
            @Field("money_isurrance") double money_isurrance,
            @Field("address") String address,
            @Field("email") String email,
            @Field("number_phone") String number_phone,
            @Field("rate_base") double rate_base,
            @Field("pay_per_month") double pay_per_month
    );

    @DELETE("/borrow/{borrow_id}")
    Call<ResponseCommon> deleteBorrow(@Path("borrow_id") String borrow_id);

    @POST("/coppyDataMonth")
    @FormUrlEncoded
    Call<MessageDataMonthCopy> copyDataMonth(@Header("Authorization") String token,
                                             @Field("current_month") String current_month,
                                             @Field("current_year") String current_year);
}
