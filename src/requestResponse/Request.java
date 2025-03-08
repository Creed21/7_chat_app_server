/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requestResponse;

import java.io.Serializable;
import requestResponse.Operation;

/**
 *
 * @author Korisnik
 */
public class Request implements Serializable {
    private static final long serialVersionUID = 73960693494415052L;
    
    private Operation operation;
    private Object parametar; // na ovaj nacin mogu da prosledim
    // i studenta i profeseroa preko klijentskog zahteva

    public Request() {
    }

    public Request(Operation operacija, Object parametar ) {
        this.operation = operacija;
        this.parametar = parametar;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Object getParametar() {
        return parametar;
    }

    public void setParametar(Object parametar) {
        this.parametar = parametar;
    }

    @Override
    public String toString() {
        return "KlijentskiZahtev{" + "operacija=" + operation + ", parametar=" + parametar + '}';
    }

}
