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
    // users
    LOGIN, 
    GET_ALL_USERS,
    ADD_USER_GROUP, // to do
    GET_ALL_GROUPS,
    // direct messages
    SEND_MESSAGE,
    READ_CHAT_MESSAGES,
    // group messages
    SEND_GROUP_MESSAGE, // to do
    READ_GROUP_MESSAGE, // to do
    //exit
    EXIT;            
}
