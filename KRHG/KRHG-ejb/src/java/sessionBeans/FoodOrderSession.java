/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entity.FoodOrder;
import error.NoResultException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class FoodOrderSession implements FoodOrderSessionLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<FoodOrder> getAllFoodOrder() {
        Query q;
        q = em.createQuery("SELECT fo FROM FoodOrder fo ");
        return q.getResultList();
    }

    @Override
    public FoodOrder getAllFoodOrderByID(Long foodOrderID) throws NoResultException {
        FoodOrder fo = em.find(FoodOrder.class, foodOrderID);
        if (fo != null) {
            return fo;
        } else {
            throw new NoResultException("Food Order not found.");
        }
    }

    @Override
    public void updateFoodOrder(FoodOrder foodOrder) throws NoResultException {
        FoodOrder oldFoodOrder = em.find(FoodOrder.class, foodOrder.getFoodOrderID());
        if (oldFoodOrder != null) {
            oldFoodOrder.setFoodOrdered(foodOrder.getFoodOrdered());
            oldFoodOrder.setTotalPrice(foodOrder.getTotalPrice());
            oldFoodOrder.setStatus(foodOrder.getStatus());
            oldFoodOrder.setSpecialRequest(foodOrder.getSpecialRequest());
        } else {
            throw new NoResultException("Food Menu Item not found");
        }
    }

    @Override
    public void deleteFoodOrder(Long foodOrderID) throws NoResultException {
        FoodOrder foodOrder = em.find(FoodOrder.class, foodOrderID);
        if (foodOrder != null) {
            em.remove(foodOrder);
        } else {
            throw new NoResultException("Food Order not found");
        }
    }

    @Override
    public void createFoodOrder(FoodOrder foodOrder) {
        em.persist(foodOrder);
    }

    @Override
    public FoodOrder getLastFoodOrdered() {
        Query q = em.createQuery("SELECT f FROM FoodOrder f ORDER BY f.foodOrderID DESC");
        return (FoodOrder) q.getResultList().get(0);
    }
}
