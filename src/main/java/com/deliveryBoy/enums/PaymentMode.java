//package com.deliveryBoy.enums;
//
//import java.util.Arrays;
//
//public enum PaymentMode {
//	ONLINE_PAYMENT("Online Payment"),
//    CASH_ON_DELIVERY("Cash on Delivery");
//
//    private final String mode;
//
//    PaymentMode(String mode) {
//        this.mode = mode;
//    }
//
//    public String getMode() {
//        return mode;
//    }
//
//    
//    public static boolean isValidMode(String mode) {
//        return Arrays.stream(PaymentMode.values())
//                     .anyMatch(paymentMode -> paymentMode.getMode().equalsIgnoreCase(mode));
//    }
//}
