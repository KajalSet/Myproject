//package com.deliveryBoy.enums;
//
//public enum CancellationReason {
//	CUSTOMER_NOT_AVAILABLE("Customer not available"),
//    INCORRECT_ADDRESS("Incorrect address"),
//    DELIVERY_DELAYED("Delivery delayed"),
//    OTHER("Other");
//
//    private final String reason;
//
//    CancellationReason(String reason) {
//        this.reason = reason;
//    }
//
//    public String getReason() {
//        return reason;
//    }
//
//    public static boolean isValidReason(String reason) {
//        for (CancellationReason cancellationReason : values()) {
//            if (cancellationReason.getReason().equalsIgnoreCase(reason)) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
