package vn.iotstar.services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import vn.iotstar.configs.VNPAYConfig;

@Service
public class VNPAYService {

	 public String createOrder(int total, String orderInfor, String urlReturn){
			System.out.println("check create oder");
	        String vnp_Version = "2.1.0";
	        String vnp_Command = "pay";
	        String vnp_TxnRef = VNPAYConfig.getRandomNumber(8);
	        String vnp_IpAddr = "vietnamnews.vn";
	        String vnp_TmnCode = VNPAYConfig.vnp_TmnCode;
	        String orderType = "order-type";
	        
	        Map<String, String> vnp_Params = new HashMap<>();
	        vnp_Params.put("vnp_Version", vnp_Version);
	        vnp_Params.put("vnp_Command", vnp_Command);
	        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
	        vnp_Params.put("vnp_Amount", String.valueOf(total*100));
	        vnp_Params.put("vnp_CurrCode", "VND");
	        
	        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
	        vnp_Params.put("vnp_OrderInfo", orderInfor);
	        vnp_Params.put("vnp_OrderType", orderType);

	        String locate = "vn";
	        vnp_Params.put("vnp_Locale", locate);

	        urlReturn += VNPAYConfig.vnp_Returnurl;
	        vnp_Params.put("vnp_ReturnUrl", urlReturn);
	        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

	        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	        String vnp_CreateDate = formatter.format(cld.getTime());
	        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

	        cld.add(Calendar.MINUTE, 15);
	        String vnp_ExpireDate = formatter.format(cld.getTime());
	        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

	        List fieldNames = new ArrayList(vnp_Params.keySet());
	        Collections.sort(fieldNames);
	        StringBuilder hashData = new StringBuilder();
	        StringBuilder query = new StringBuilder();
	        Iterator itr = fieldNames.iterator();
	        while (itr.hasNext()) {
	            String fieldName = (String) itr.next();
	            String fieldValue = (String) vnp_Params.get(fieldName);
	            if ((fieldValue != null) && (fieldValue.length() > 0)) {
	                //Build hash data
	                hashData.append(fieldName);
	                hashData.append('=');
	                try {
	                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
	                    //Build query
	                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
	                    query.append('=');
	                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
	                } catch (UnsupportedEncodingException e) {
	                    e.printStackTrace();
	                }
	                if (itr.hasNext()) {
	                    query.append('&');
	                    hashData.append('&');
	                }
	            }
	        }
	        String queryUrl = query.toString();
	        String vnp_SecureHash = VNPAYConfig.hmacSHA512(VNPAYConfig.vnp_HashSecret, hashData.toString());
	        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
	        String paymentUrl = VNPAYConfig.vnp_PayUrl + "?" + queryUrl;
	        return paymentUrl;
	    }

	    public int orderReturn(HttpServletRequest request){
	        System.out.println("orderReturn được gọi.");  // In ra khi phương thức được gọi
	        Map fields = new HashMap();
	        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
	            String fieldName = null;
	            String fieldValue = null;
	            try {
	                fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
	                fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
	            } catch (UnsupportedEncodingException e) {
	                e.printStackTrace();
	            }
	            if ((fieldValue != null) && (fieldValue.length() > 0)) {
	                fields.put(fieldName, fieldValue);
	            }
	        }

	        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
	        if (fields.containsKey("vnp_SecureHashType")) {
	            fields.remove("vnp_SecureHashType");
	        }
	        if (fields.containsKey("vnp_SecureHash")) {
	            fields.remove("vnp_SecureHash");
	        }
	        
	        String signValue = VNPAYConfig.hashAllFields(fields);  // Tạo chữ ký từ dữ liệu

	     // In ra chữ ký tính toán
	     System.out.println("Chữ ký tính toán từ hệ thống: " + signValue);

	     // In ra chữ ký VNPay trả về
	     System.out.println("Chữ ký VNPay trả về (vnp_SecureHash): " + vnp_SecureHash);

	     if (signValue.equals(vnp_SecureHash)) {  // So sánh chữ ký
	         // In ra trạng thái giao dịch để kiểm tra
	         String transactionStatus = request.getParameter("vnp_TransactionStatus");
	         System.out.println("Trạng thái giao dịch (vnp_TransactionStatus): " + transactionStatus);
	         
	         if ("00".equals(transactionStatus)) {  // Kiểm tra trạng thái giao dịch
	             return 1;  // Thành công
	         } else {
	             return 0;  // Giao dịch thất bại
	         }
	     } else {
	         // Nếu chữ ký không khớp
	         System.out.println("Lỗi: Chữ ký không khớp!");
	         return -1;  // Chữ ký không khớp
	     }
	    }

}
