package vn.iotstar.configs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class VNPAYConfig {
	 public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
	    public static String vnp_Returnurl = "/vnpay-payment";
	    public static String vnp_TmnCode = "E4RNDLBT";
	    public static String vnp_HashSecret = "E2CD4GL1MITFHZQO10NAKIL7I7Q2F18A";
	    public static String vnp_apiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";

	    public static String md5(String message) {
	        String digest = null;
	        try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            byte[] hash = md.digest(message.getBytes("UTF-8"));
	            StringBuilder sb = new StringBuilder(2 * hash.length);
	            for (byte b : hash) {
	                sb.append(String.format("%02x", b & 0xff));
	            }
	            digest = sb.toString();
	        } catch (UnsupportedEncodingException ex) {
	            digest = "";
	        } catch (NoSuchAlgorithmException ex) {
	            digest = "";
	        }
	        return digest;
	    }

	    public static String Sha256(String message) {
	        String digest = null;
	        try {
	            MessageDigest md = MessageDigest.getInstance("SHA-256");
	            byte[] hash = md.digest(message.getBytes("UTF-8"));
	            StringBuilder sb = new StringBuilder(2 * hash.length);
	            for (byte b : hash) {
	                sb.append(String.format("%02x", b & 0xff));
	            }
	            digest = sb.toString();
	        } catch (UnsupportedEncodingException ex) {
	            digest = "";
	        } catch (NoSuchAlgorithmException ex) {
	            digest = "";
	        }
	        return digest;
	    }

	    public static String hashAllFields(Map fields) {
	        List fieldNames = new ArrayList(fields.keySet());
	        Collections.sort(fieldNames);
	        StringBuilder sb = new StringBuilder();
	        Iterator itr = fieldNames.iterator();
	        
	        // Tạo chuỗi dữ liệu trước khi tính toán chữ ký
	        System.out.println("Chuỗi dữ liệu trước khi tạo chữ ký:");
	        
	        while (itr.hasNext()) {
	            String fieldName = (String) itr.next();
	            String fieldValue = (String) fields.get(fieldName);
	            if ((fieldValue != null) && (fieldValue.length() > 0)) {
	                sb.append(fieldName);
	                sb.append("=");
	                sb.append(fieldValue);
	            }
	            if (itr.hasNext()) {
	                sb.append("&");
	            }
	        }
	        
	        // In chuỗi dữ liệu tạo chữ ký
	        String dataToSign = sb.toString();
	        System.out.println("Chuỗi dữ liệu cần tạo chữ ký: " + dataToSign);

	        // Tạo chữ ký HMAC-SHA512
	        String signature = hmacSHA512(vnp_HashSecret, dataToSign);
	        
	        // In ra chữ ký tính được
	        System.out.println("Chữ ký tạo được: " + signature);
	        
	        return signature;
	    }

	    
	    public static String hmacSHA512(final String key, final String data) {
	        try {

	            if (key == null || data == null) {
	                throw new NullPointerException();
	            }
	            final Mac hmac512 = Mac.getInstance("HmacSHA512");
	            byte[] hmacKeyBytes = key.getBytes();
	            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
	            hmac512.init(secretKey);
	            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
	            byte[] result = hmac512.doFinal(dataBytes);
	            StringBuilder sb = new StringBuilder(2 * result.length);
	            for (byte b : result) {
	                sb.append(String.format("%02x", b & 0xff));
	            }
	            return sb.toString();

	        } catch (Exception ex) {
	            return "";
	        }
	    }

	    public static String getIpAddress(HttpServletRequest request) {
	        String ipAdress;
	        try {
	            ipAdress = request.getHeader("X-FORWARDED-FOR");
	            if (ipAdress == null) {
	                ipAdress = request.getLocalAddr();
	            }
	        } catch (Exception e) {
	            ipAdress = "Invalid IP:" + e.getMessage();
	        }
	        return ipAdress;
	    }

	    public static String getRandomNumber(int len) {
	        Random rnd = new Random();
	        String chars = "0123456789";
	        StringBuilder sb = new StringBuilder(len);
	        for (int i = 0; i < len; i++) {
	            sb.append(chars.charAt(rnd.nextInt(chars.length())));
	        }
	        return sb.toString();
	    }
}