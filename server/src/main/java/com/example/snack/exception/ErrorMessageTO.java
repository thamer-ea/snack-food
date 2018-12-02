package com.example.snack.exception;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class ErrorMessageTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName(value = "message")
    private String errorMessage;

}
