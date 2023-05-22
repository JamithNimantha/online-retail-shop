package service;

import service.imp.ItemServiceImpl;
import service.imp.ShopperServiceImpl;

/**
 * @author Jamith Nimantha
 */
public final class ServiceManager {

    private volatile static ItemService itemService = null;
    private volatile static ShopperService shoppingService = null;

    public enum ServiceType {
        ITEM_SERVICE, SHOPPER_SERVICE, ADMIN_SERVICE
    }

    private ServiceManager() {
    }

    public static SuperService getService(ServiceType type) {
        switch (type) {
            case ITEM_SERVICE:
                if (itemService == null) {
                    synchronized (ServiceManager.class) {
                        if (itemService == null) {
                            itemService = new ItemServiceImpl();
                        }
                    }
                }
                return itemService;
            case SHOPPER_SERVICE:
                if (shoppingService == null) {
                    synchronized (ServiceManager.class) {
                        if (shoppingService == null) {
                            shoppingService = new ShopperServiceImpl((ItemService) getService(ServiceType.ITEM_SERVICE));
                        }
                    }
                }
                return shoppingService;
            default:
                throw new RuntimeException("Invalid service type");
        }
    }
}
