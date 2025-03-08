/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requestResponse;

import java.io.Serializable;

/**
 *
 * @author Korisnik
 */
public enum Operation implements Serializable{
    LOGIN, 
    SEND_MESSAGE,
    GET_ALL_USERS,
    READ_CHAT_MESSAGES,
    EXIT;
    
//    private static final Long serialVersionUID = 494415052L;
}
