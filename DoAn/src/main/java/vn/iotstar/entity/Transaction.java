package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.NamedQuery;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Data
@Table(name = "transactions")
@NamedQuery(name = "Transaction.findAll", query = "SELECT trans FROM Transaction trans")
public class Transaction implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TransId")
	private int transactionId;
	
	//Ngày giao dịch
	@Column(name = "TransDay", columnDefinition = "DATE NOT NULL")
	private Date transactionDay;
	
	//Địa chỉ giao dịch
	@Column(name = "TransAddress", columnDefinition = "NVARCHAR(200) NOT NULL")
	private String transAddress;
	
	//Trạng thái giao dịch (1 đã xong / 0 chưa xong)
	@Column(name = "TransStatus", columnDefinition = "INT")
	private int transStatus;	
	
	//Tổng giá trị giao dịch (tất cả đơn hàng)
	@Column(name = "TransTotalValue", columnDefinition = "BIGINT")
	private long transTotalValue;
	
	@ManyToOne
	@JoinColumn(name = "UserId")
	private User user;
	
	
	public Transaction() {
		
	}
	
	
	
}