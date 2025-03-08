/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requestResponse;

import java.io.Serializable;
import java.util.List;
import model.Model;

/**
 *
 * @author Korisnik
 */
public class Response implements Serializable  {
    private static final long serialVersionUID = 7396069349441505251L;
    
    private String message;
    private boolean success;
    private Object result;
    private List<Model> resultList;

    public Response() {
    }

    public Response(String message, boolean success, Object result, List<Model> resultList) {
        this.message = message;
        this.success = success;
        this.result = result;
        this.resultList = resultList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public List<Model> getResultList() {
        return resultList;
    }

    public void setResultList(List<Model> resultList) {
        this.resultList = resultList;
    }

}
