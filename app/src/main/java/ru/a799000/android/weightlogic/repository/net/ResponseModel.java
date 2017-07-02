package ru.a799000.android.weightlogic.repository.net;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Replay Auth
 */

public class ResponseModel {
    @SerializedName("time_reseption")
    @Expose
    private String timeReseption;

    @SerializedName("command")
    @Expose
    private String command;

    @SerializedName("time_response")
    @Expose
    private String timeResponse;

    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("mess_error")
    @Expose
    private String messError;

    @SerializedName("success")
    @Expose
    private Boolean success;


    public String getTimeReseption() {
        return timeReseption;
    }

    public void setTimeReseption(String timeReseption) {
        this.timeReseption = timeReseption;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getTimeResponse() {
        return timeResponse;
    }

    public void setTimeResponse(String timeResponse) {
        this.timeResponse = timeResponse;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessError() {
        return messError;
    }

    public void setMessError(String messError) {
        this.messError = messError;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "timeReseption='" + timeReseption + '\'' +
                ", command='" + command + '\'' +
                ", timeResponse='" + timeResponse + '\'' +
                ", response='" + response + '\'' +
                ", messError='" + messError + '\'' +
                ", success=" + success +
                '}';
    }
}
