/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author fengl
 */
@Entity
public class MinibarItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long minibarItemID;
    private String itemName;
    private int qty;
    private double price;
    public boolean status;

    public MinibarItem(){
        status = true;
    }
    
    public MinibarItem(String itemName, int qty, double price) {
        this();
        this.itemName = itemName;
        this.qty = qty;
        this.price = price;
    }
    

    public Long getMinibarItemID() {
        return minibarItemID;
    }

    public void setMiniBarItemID(Long minibarItemID) {
        this.minibarItemID = minibarItemID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (minibarItemID != null ? minibarItemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MinibarItem)) {
            return false;
        }
        MinibarItem other = (MinibarItem) object;
        if ((this.minibarItemID == null && other.minibarItemID != null) || (this.minibarItemID != null && !this.minibarItemID.equals(other.minibarItemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MinibarItem[ minibarItemID=" + minibarItemID + " ]";
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
