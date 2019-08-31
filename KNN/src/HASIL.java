/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andes herviana
 */
public class HASIL {
    private double hasil;
    int hoax;

    
    public HASIL(double hasil, int hoax) {
        this.hasil = hasil;
        this.hoax = hoax;
    }
    public void setHasil(double hasil) {
        this.hasil = hasil;
    }

    public void setHoax(int hoax) {
        this.hoax = hoax;
    }

    public double getHasil() {
        return hasil;
    }

    public int getHoax() {
        return hoax;
    }
    
    
}
